package com.atguigu.spzx.search.service;

import com.atguigu.spzx.common.es.SkuEsModel;
import com.sun.tools.javac.util.List;

import java.io.IOException;

public interface EsProductService {
    void productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
