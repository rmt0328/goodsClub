package com.atguigu.spzx.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.common.es.SkuEsModel;
import com.atguigu.spzx.search.config.ElasticConfig;
import com.atguigu.spzx.search.constant.EsConstant;
import com.atguigu.spzx.search.service.EsProductService;
import com.sun.tools.javac.util.List;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class EsProductServiceImpl implements EsProductService {

    @Autowired
    RestHighLevelClient client;
    @Override
    public void productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        //保存到es中
        BulkRequest bulkRequest =new BulkRequest();
        for(SkuEsModel model:skuEsModels){
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(model.getSkuId().toString());
            String s= JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        BulkResponse bulk = client.bulk(bulkRequest, ElasticConfig.COMMON_OPTIONS);
    }
}
