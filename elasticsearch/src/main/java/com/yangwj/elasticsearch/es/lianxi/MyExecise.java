package com.yangwj.elasticsearch.es.lianxi;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * @author yangwj
 * @version 1.0
 * @date 2020/8/17 20:30
 */
public class MyExecise {

    private TransportClient client = null;

    @Before
    public void init() throws UnknownHostException {
        //配置
        Settings settings = Settings.builder()
                .put("cluster.name", "bigdata")
                //.put("client.transport.sniff", true)
                .build();
        //集群地址
        client = new PreBuiltTransportClient(settings).addTransportAddresses(
                new InetSocketTransportAddress(InetAddress.getByName("192.168.33.100"), 9300),
                new InetSocketTransportAddress(InetAddress.getByName("192.168.33.101"), 9300),
                new InetSocketTransportAddress(InetAddress.getByName("192.168.33.102"), 9300)
        );
    }

    @Test
    public void testCreate() throws IOException {
        //创建索引并入库
        client.prepareIndex("news","yangwj","11").setSource(
                jsonBuilder()
                        .startObject()
                        .field("name","杨杰11")
                        .field("age",11)
                        .field("desc","2020年8月18日外交部发言人赵立坚主持例行记者会")
                .endObject()
        ).get();
    }


    @Test
    public void testGetOne(){
        //获取
        GetResponse response = client.prepareGet("news", "yangwj", "1").get();
        //获取数据
        System.out.println(response.getSource());
        //获取库表信息和数据
        System.out.println(response.getSourceAsString());
    }

    @Test
    public void testUpdateOne() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("news");
        updateRequest.type("yangwj");
        updateRequest.id("1");
        updateRequest.doc(
                jsonBuilder().startObject()
                .field("age","888")
                .endObject()
        );
        client.update(updateRequest);
    }

    @Test
    public void testDeleteOne() throws IOException {
        DeleteResponse response = client.prepareDelete("news", "yangwj", "2").get();
        System.out.println(response);
    }

    @Test
    public void testQueryMany() throws IOException {
        // matchPhraseQuery,matchQuery 查询会进行分词查询
        SearchRequestBuilder srb=client.prepareSearch("news").setTypes("yangwj");
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery("name", "杰");
        QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("desc", "一个");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .must(queryBuilder2)
                )
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }


    @Test
    public void testQueryManyPage(){
        //分页查询, matchPhraseQuery
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder(); //构造一个默认配置的对象
        sourceBuilder.query(QueryBuilders.matchPhraseQuery("name", "杨杰")); //设置查询
        sourceBuilder.from(0); //设置从哪里开始
        sourceBuilder.size(5); //每页5条
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); //设置超时时间
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest).actionGet();
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }


    @Test
    public void testTermQuery() throws IOException {
        // matchPhraseQuery,matchQuery 查询会进行分词查询
        SearchRequestBuilder srb=client.prepareSearch("news").setTypes("yangwj");
        QueryBuilder queryBuilder=QueryBuilders.termQuery("name", "杨杰");
        //QueryBuilder queryBuilder2=QueryBuilders.matchPhraseQuery("desc", "一个");
        SearchResponse sr=srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                //.must(queryBuilder2)
        )
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    /***
     * 结论：相关度查询使用match，精确字段查询使用matchPhrase即可。
     * match：全文搜索, 通常用于对text类型字段的查询,会对进行查询的文本先进行分词操作
     * term：精确查询,通常用于对keyword和有精确值的字段进行查询,不会对进行查询的文本进行分词操作
     */

    @Test
    public void testTermGeo() throws IOException {

    }

}
