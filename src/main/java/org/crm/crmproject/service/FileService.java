package org.crm.crmproject.service;

import org.crm.crmproject.dto.UploadResultDTO;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    List<UploadResultDTO> uploadFile(@RequestPart MultipartFile[] multipartFile);
}
