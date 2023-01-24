package com.infobip.secops.repository;

import com.infobip.secops.model.FileItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileItemRepository extends CrudRepository<FileItem, Long> {
}
