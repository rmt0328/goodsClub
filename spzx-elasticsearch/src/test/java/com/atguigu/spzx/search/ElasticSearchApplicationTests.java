package com.atguigu.spzx.search;

import com.alibaba.fastjson.JSON;
import com.atguigu.spzx.search.config.ElasticConfig;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.routing.allocation.allocator.BalancedShardsAllocator;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ElasticSearchApplicationTests {
    @Autowired
    private RestHighLevelClient client;

    @Test
    void contextLoads(){
        System.out.println(client);

    }

    //给es存储数据
    @Test
    public void indexData() throws IOException {
        //构造存储请求
        IndexRequest request=new IndexRequest("users");
        request.id("1");
    //    request.source("userName","zangsan","age",18);
         User user=new User();
        String jsonString = JSON.toJSONString(user);
        request.source(jsonString, XContentType.JSON);
        //执行操作

        IndexResponse index = client.index(request, ElasticConfig.COMMON_OPTIONS);

        System.out.println(index);
    }

    //检索数据
    @Test
    public void searchData() throws IOException {
        //1、创建检索请求
        SearchRequest searchRequest = new SearchRequest();
        //从哪个索引进行检索
        searchRequest.indices("users");
        //指定检索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        sourceBuilder.query(QueryBuilders.matchQuery("address","mill"));
        //按照年龄的值分布进行聚合
        sourceBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age").size(10));

        //计算平均薪资
        sourceBuilder.aggregation(AggregationBuilders.avg("balanceAvg").field("balance"));

        System.out.println("检索" + sourceBuilder.toString());
        searchRequest.source(sourceBuilder);


        //执行
        SearchResponse searchResponse = client.search(searchRequest, ElasticConfig.COMMON_OPTIONS);


        //分析结果



    }

    class User{
        private String userName;
        private String gender;
        private Integer age;
    }
}
