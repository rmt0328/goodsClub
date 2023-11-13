package com.atguigu.spzx.manager.service;

import com.atguigu.spzx.model.entity.product.Category;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-27-1:28
 */
public interface CategoryService {
    List<Category> findByParentId(Long parentId);

    void exportData(HttpServletResponse response);

    /**
     * @Description: 导入
     * @param file
     */
    void importData(MultipartFile file);
}
