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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.validation.ConstraintViolationException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = "/excel")
public class ExcelController {

    private FileItemRepository fileItemRepository;

    @Autowired
    public ExcelController(FileItemRepository fileItemRepository) {
        this.fileItemRepository = fileItemRepository;
    }

    @GetMapping
    public String uploadList(Model model) {
        model.addAttribute("fileItems", fileItemRepository.findAll());
        return "excel";
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<String> fetch(@PathVariable Long id) throws ParserConfigurationException {

        Optional<FileItem> optional = fileItemRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileItem item = optional.get();
        String printout = "";
        FileSystemResource fsr = new FileSystemResource(item.getFilepath());
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dbf.newDocumentBuilder();
            File file = fsr.getFile();

            Document doc = dbuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("product");
            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;
            printout = eElement.getElementsByTagName("description").item(0).getTextContent();

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(printout, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/safe")
    @ResponseBody
    public ResponseEntity<String> fetchSafe(@PathVariable Long id) throws ParserConfigurationException{
        Optional<FileItem> optional = fileItemRepository.findById(id);
        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        FileItem item = optional.get();
        String printout = "";
        FileSystemResource fsr = new FileSystemResource(item.getFilepath());
        String feature = null;
        try {

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            DocumentBuilder dbuilder = dbf.newDocumentBuilder();
            File file = fsr.getFile();
            Document doc = dbuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("product");
            Node nNode = nList.item(0);
            Element eElement = (Element) nNode;
            printout = eElement.getElementsByTagName("tags").item(0).getTextContent();
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>(printout, HttpStatus.OK);
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        Boolean success = Boolean.FALSE;
        String filename = "tmp_" + file.getOriginalFilename();

        int index = filename.lastIndexOf(".");

        String filepath = System.getProperty("java.io.tmpdir") + File.separator + filename;
        FileItem fileItem = new FileItem(filename, filepath);
        success = handleUpload(file, fileItem);

        model.addAttribute("success", success);
        return "file";
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
