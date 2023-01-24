package com.infobip.secops.controller;

import com.infobip.secops.model.FileItem;
import com.infobip.secops.repository.FileItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    private FileItemRepository fileItemRepository;

    @Autowired
    public FileController(FileItemRepository fileItemRepository) {
        this.fileItemRepository = fileItemRepository;
    }

    @GetMapping
    public String uploadList(Model model) {
        model.addAttribute("fileItems", fileItemRepository.findAll());
        return "file";
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Resource> fetch(@PathVariable Long id) {
        Optional<FileItem> optional = fileItemRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileItem item = optional.get();
        FileSystemResource fsr = new FileSystemResource(item.getFilepath());
        return new ResponseEntity<>(fsr, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/safe")
    @ResponseBody
    public ResponseEntity<Resource> fetchSafe(@PathVariable Long id) {
        Optional<FileItem> optional = fileItemRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileItem item = optional.get();
        FileSystemResource fsr = new FileSystemResource(item.getFilepath());

        // add headers
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"%s\"", fsr.getFilename()));

        return new ResponseEntity<>(fsr, headers, HttpStatus.OK);
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("checks") Long checks, Model model) {
        Boolean success = Boolean.FALSE;
        String filename = "tmp_" + file.getOriginalFilename();

        int index = filename.lastIndexOf(".");
        if (checks > 0 && !filename.substring(index).startsWith(".doc")) {
            model.addAttribute("success", success);
            return "file";
        }
        if (checks > 1 && !validateContentType(file)) {
            model.addAttribute("success", success);
            return "file";
        }
        if (checks > 2 && !validateMagicBytes(file)) {
            model.addAttribute("success", success);
            return "file";
        }

        String filepath = System.getProperty("java.io.tmpdir") + File.separator + filename;
        FileItem fileItem = new FileItem(filename, filepath);
        success = handleUpload(file, fileItem);

        model.addAttribute("success", success);
        return "file";
    }

    private static final byte[] COMPOUND_FILE_BINARY_FORMAT
            = new byte[] {(byte)0xd0, (byte)0xcf, (byte)0x11, (byte)0xe0, (byte)0xa1, (byte)0xb1, (byte)0x1a, (byte)0xe1};
    private static final byte[] ZIP_DOCX_FILE_FORMAT = new byte[] {(byte)0x50, (byte)0x4b, (byte)0x03, (byte)0x04};

    private boolean validateMagicBytes(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            return bytesStartWith(bytes, COMPOUND_FILE_BINARY_FORMAT) || bytesStartWith(bytes, ZIP_DOCX_FILE_FORMAT);
        } catch (IOException e) {
            return false;
        }
    }

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
            "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "application/vnd.ms-word.document.macroEnabled.12", "application/vnd.openxmlformats-officedocument.wordprocessingml.template");

    private boolean validateContentType(MultipartFile file) {
        return ALLOWED_CONTENT_TYPES.contains(file.getContentType());
    }

    private boolean bytesStartWith(byte[] bytes, byte[] startswith) {
        if (bytes.length < startswith.length) {
            return false;
        }
        for (int i = 0; i < startswith.length; i++) {
            if (startswith[i] != bytes[i]) {
                return false;
            }
        }
        return true;
    }
      
    private Boolean handleUpload(MultipartFile file, FileItem fileItem) {
        Boolean success;
        try {
            fileItemRepository.save(fileItem);
            FileOutputStream fos = new FileOutputStream(new File(fileItem.getFilepath()));
            fos.write(file.getBytes());
            success = Boolean.TRUE;
        } catch (IOException e) {
            success = Boolean.FALSE;
        } catch (ConstraintViolationException e) {
            String currentFilename = fileItem.getFilename();
            fileItem.setFilename(currentFilename + "_n");
            success = handleUpload(file, fileItem);
        }
        return success;
    }
}
