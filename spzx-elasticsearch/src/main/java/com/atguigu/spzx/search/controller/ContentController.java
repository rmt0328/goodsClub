package com.atguigu.spzx.search.controller;

import com.atguigu.spzx.common.es.SkuEsModel;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.search.service.EsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sun.tools.javac.util.List;

import java.io.IOException;

@RequestMapping("/search/save")
@RestController
public class ContentController {

    @Autowired
    EsProductService EsproductService;

    //上架商品
    @RequestMapping("/product")

    public Result productStatusUp(@RequestBody List<SkuEsModel> skuEsModels) throws IOException {

        EsproductService.productStatusUp(skuEsModels);
        return Result.build(null, ResultCodeEnum.SUCCESS);

    }
}
