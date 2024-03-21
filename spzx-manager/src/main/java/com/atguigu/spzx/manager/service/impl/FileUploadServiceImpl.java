package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.manager.properties.MinioProperties;
import com.atguigu.spzx.manager.service.FileUploadService;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hpsf.Thumbnail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

/**
 * @author ljl
 * @create 2023-10-26-14:36
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private MinioProperties minioProperties ;

    @Override
    public String fileUpload(MultipartFile multipartFile) {

        try {
            // 创建一个Minio的客户端对象
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(minioProperties.getEndpointUrl())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecreKey())
                    .build();

            // 判断桶是否存在
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if (!found) { // 如果不存在，那么此时就创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else { // 如果存在打印信息
                System.out.println("Bucket 'spzx-bucket' already exists.");
            }

            // 设置存储对象名称
            String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String suffix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = dateDir + "/" + uuid + multipartFile.getOriginalFilename();
            System.out.println(fileName);

            // 判断是否是图片并且大小超过100k
            if (isPicture(suffix) && multipartFile.getSize() > (1024 * 1024 * 0.1)) {
                // 缩放并调整图片质量
                //创建一个字节数组输出流，用来存储处理后的图片数据
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                double quality = 1.0;
                //根据上传图片的大小设置不同的压缩质量
                if (multipartFile.getSize() <= (1024 * 1024)) {
                    quality = 0.3;
                } else if (multipartFile.getSize() <= (1024 * 1024 * 2)) {
                    quality = 0.2;
                } else {
                    quality = 0.1;
                }
                Thumbnails.of(multipartFile.getInputStream())
                        .scale(1f)//1f表示不改变图片的尺寸，保持原始大小
                        .outputQuality(quality)//根据设置的质量参数进行压缩处理
                        .toOutputStream(outputStream);//将处理之后的图片数据输出到字节数组输出流中

                InputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

                // 上传文件到MinIO
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .stream(inputStream, outputStream.size(), -1)
                        .object(fileName)
                        .build();
                minioClient.putObject(putObjectArgs);
            }
            else{
                PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .stream(multipartFile.getInputStream(), multipartFile.getSize(), -1)
                        .object(fileName)
                        .build();
                minioClient.putObject(putObjectArgs) ;
            }

            return minioProperties.getEndpointUrl() + "/" + minioProperties.getBucketName() + "/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 判断文件是否为图片
     */
    public boolean isPicture(String imgName) {
        boolean flag = false;
        if (StringUtils.isBlank(imgName)) {
            return false;
        }
        String[] arr = {"bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"};
        for (String item : arr) {
            if (item.equals(imgName)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
