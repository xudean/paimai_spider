package com.xudean.spider.impl;

import com.xudean.spider.ISpider;
import com.xudean.util.JSONUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PingAnSpider implements ISpider {
    public static Map<String, String> header = new HashMap<>();

    private int size;
    private int page;

    public static void main(String[] args) throws IOException {
        new PingAnSpider(50, 1, "F7A3781EC81F941493C52413A8802B01").startSpider();
    }

    public PingAnSpider(int size, int page, String token) {
        this.size = size;
        this.page = page;
        header.put("isaps-token", token);
    }


    static {
        header.put("Accept", "application/json, text/plain, */*");
        header.put("Accept-Encoding", "gzip, deflate, br");
        header.put("Accept-Language", "zh-CN,zh;q=0.9");
        header.put("Connection", "keep-alive");
        header.put("Content-Type", "application/json;charset=UTF-8");
        header.put("Host", "bfiles.pingan.com.cn");
        header.put("isaps-token", "77D52A1733DC292878B5D7FDB95EEE47");
        header.put("Origin", "https://ebank.pingan.com.cn");
        header.put("Referer", "https://ebank.pingan.com.cn/");
        header.put("Sec-Fetch-Dest", "empty");
        header.put("Sec-Fetch-Mode", "cors");
        header.put("Sec-Fetch-Site", "same-site");
        header.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
    }

    @Override
    public void startSpider() throws IOException {
        Map<String, Object> pageContent = getPageContentList(1);
        //获取每一页的列表
        Map<String, Object> data = (Map<String, Object>) pageContent.get("data");
        List<Map<String, Object>> dataList = (List) data.get("list");
        for (int i = 0; i < dataList.size(); i++) {
            Map<String, Object> contentMap = dataList.get(i);
            String serialno = (String) contentMap.get("serialno");
            Map<String,Object> detailMap = getPageContentDetail(1,serialno);
            Map<String, Object> detailData = (Map<String, Object>) detailMap.get("data");
            Map<String, Object> caseData = (Map<String, Object>) detailData.get("data");

        }
        System.out.println(pageContent);
//        for(int i=1;i<=page;i++){
//            Map<String, Object> pageContent = getPageContent(i);
//
//        }
    }


    public Map<String, Object> getPageContentList(int page) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://bfiles.pingan.com.cn/brcp/stp/openapi/servgw/servicegateway/casedeal/selectCaseList");

        String json = String.format("{\"size\":50,\"page\":\"%d\"}", page);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            httpPost.setHeader(next.getKey(), next.getValue());
        }
        CloseableHttpResponse response = client.execute(httpPost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String str = "";
        StringBuilder sb = new StringBuilder();
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        return JSONUtil.toMap(sb.toString());
    }

    public Map<String, Object> getPageContentDetail(int page, String serialNo) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://bfiles.pingan.com.cn/brcp/stp/openapi/servgw/servicegateway/casedeal/caseDetailsList");
        String json = String.format("{\"page\": %d, \"serialNo\": \"%s\"}", page, serialNo);
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            httpPost.setHeader(next.getKey(), next.getValue());
        }
        CloseableHttpResponse response = client.execute(httpPost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        String str = "";
        StringBuilder sb = new StringBuilder();
        while ((str = reader.readLine()) != null) {
            sb.append(str);
        }
        return JSONUtil.toMap(sb.toString());
    }


}
