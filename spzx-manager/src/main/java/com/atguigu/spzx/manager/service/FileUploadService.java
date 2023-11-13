package com.atguigu.spzx.manager.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ljl
 * @create 2023-10-26-14:35
 */
public interface FileUploadService {
    String fileUpload(MultipartFile multipartFile);
}
