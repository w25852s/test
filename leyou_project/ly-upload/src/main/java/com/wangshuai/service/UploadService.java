package com.wangshuai.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.wangshuai.config.UploadProperties;
import com.wangshuai.enum1.ExceptionEnum;
import com.wangshuai.exception.LyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {

    //private static final List<String> ALLOW_FILE = Arrays.asList("image/jpeg", "image/png", "image/bmp");
    @Autowired
    private UploadProperties prop;


    @Autowired
    private FastFileStorageClient storageClient;

    public String uploadImage(MultipartFile file, HttpServletRequest request) {
      /*  String path = request.getRealPath("upload");
        log.info(path);
        File file1 = new File(path);
        if (!file1.exists()) {
            file1.mkdir();
        }*/
        String contentType = file.getContentType();
        if (!prop.getAllowTypes().contains(contentType)) {
            throw new LyException(ExceptionEnum.INVALID_FILE);
        }
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            if (image == null) {
                log.info("内容不是图片");
                throw new LyException(ExceptionEnum.INVALID_FILE);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new LyException(ExceptionEnum.INVALID_FILE);
        }
//        File uploadFile = new File(file1, file.getOriginalFilename());

        try {
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
           log.info(extension);
            StorePath path = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            log.info(path.getFullPath());
            return prop.getBaseUrl() + path.getFullPath();
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new LyException(ExceptionEnum.UPLOAD_IMAGE_FAIL);
        }

    }


}
