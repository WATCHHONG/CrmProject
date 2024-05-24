package org.crm.crmproject.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.crm.crmproject.dto.UploadResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class FileServiceImpl implements FileService {

    @Value("${org.crm.crmproject.upload.path}")
    private String uploadPath;

    @Override
    @Operation(summary = "upload 파일", description = "POST 방식으로 파일 등록")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDTO> uploadFile(@RequestPart MultipartFile[] multipartFile) {

        final List<UploadResultDTO> list = new ArrayList<>();

        for (MultipartFile uploadImage : multipartFile) {
            String uploadImageName = uploadImage.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            Path savePath = Paths.get(uploadPath, uuid + "_" + uploadImageName);

            boolean image = false;

            try {
                uploadImage.transferTo(savePath);

                // 이미지 파일 형식만 걸러내기
                if (Files.probeContentType(savePath).startsWith("image")) {

                    image = true;

                    File thumbFile = new File(uploadPath, "s_" + uuid + "_" + uploadImageName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 200, 200);
                }

                log.info("Image Saved : " + savePath);
            } catch (Exception e) {
                e.printStackTrace();
            }

            list.add(UploadResultDTO.builder()
                    .uuid(uuid)
                    .fileName(uploadImageName)
                    .img(image)
                    .build());


        }
        return list;
    }

}
