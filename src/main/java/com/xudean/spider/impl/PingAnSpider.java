package com.xudean.spider.impl;

import com.alibaba.excel.EasyExcel;
import com.xudean.handler.CustomCellWriteHandler;
import com.xudean.pojo.CaseItem;
import com.xudean.pojo.HouseItem;
import com.xudean.spider.ISpider;
import com.xudean.util.DateUtil;
import com.xudean.util.JSONUtil;
import com.xudean.util.PathUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.*;
import java.text.ParseException;
import java.util.*;

public class PingAnSpider implements ISpider {
    public static Map<String, String> header = new HashMap<>();

    private int size;
    private int page;
    private List<CaseItem> caseItems = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Iterator<Map.Entry<String, String>> iterator = header.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> next = iterator.next();
            System.out.println(next.getKey()+":"+next.getValue());
        }
    }

    public PingAnSpider(int size, int page, String token, String session) {
        this.size = size;
        this.page = page;
        header.put("isaps-token", token);
//        header.put("isaps-token", token);
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
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36");
    }

    @Override
    public void startSpider() throws IOException {
        for (int index = 1; index <= page; index++) {
            Map<String, Object> pageContent = getPageContentList(index);
            //获取每一页的列表
            Map<String, Object> data = (Map<String, Object>) pageContent.get("data");
            List<Map<String, Object>> dataList = (List) data.get("list");
            if (data == null) {
                throw new IOException("获取数据失败");
            }
            for (int i = 0; i < dataList.size(); i++) {
                CaseItem caseItem = new CaseItem();
                Map<String, Object> contentMap = dataList.get(i);
                String serialno = (String) contentMap.get("serialno");
                caseItem.setSerialno(serialno);
                String outsourceStartDate = (String) contentMap.get("outsourceStartDate");
                Date date = null;
                try {
                    date = DateUtil.parseDatePingAn(outsourceStartDate);
                    outsourceStartDate = DateUtil.formatDateInSimpleFormat(date);
                } catch (ParseException e) {
                    System.out.println("date format failed");
                }
                caseItem.setOutsourceStartDate(outsourceStartDate);
                String entrustBalance = (String) contentMap.get("entrustBalance");
                caseItem.setEntrustBalance(entrustBalance);
                String customerName = (String) contentMap.get("customerName");
                caseItem.setCustomerName(customerName);
                String certNo = "11111111";
                caseItem.setCertNo(certNo);
                String certId = (String) contentMap.get("certId");
                caseItem.setCertId(certId);
                String debtAmount = (String) contentMap.get("debtAmount");
                caseItem.setDebtamount(debtAmount);
                String currentOverdueDays = (String) contentMap.get("currentOverdueDays");
                caseItem.setCurrentOverdueDays(currentOverdueDays);
                String balance = (String) contentMap.get("balance");
                caseItem.setBalance(balance);
                caseItem.setBalance2(balance);
                String debtinte = (String) contentMap.get("debtinte");
                caseItem.setDebtinte(debtinte);
                String debtintefine = (String) contentMap.get("debtintefine");
                caseItem.setDebtintefine(debtintefine);
                String collectionResult = (String) contentMap.get("collectionResult");
                if (collectionResult == null) {
                    collectionResult = "未失联";
                }
                caseItem.setCollectionResult(collectionResult);
                String telePhone = (String) contentMap.get("telePhone");
                caseItem.setMobiletelePhone(telePhone);

                Map<String, Object> detailMap = getPageContentDetail(index, serialno);
                Map<String, Object> detailData = (Map<String, Object>) detailMap.get("data");
                String outsourceEndDate = DateUtil.formatDateInSimpleFormat(DateUtil.dateAddDay(date, 90));
                caseItem.setOutsourceEndDate(outsourceEndDate);
                if (detailData == null) {
                    caseItems.add(caseItem);
                    System.out.println("have get size :" + caseItems.size() + "");
                    continue;
                }
                Map<String, Object> caseData = (Map<String, Object>) detailData.get("data");
                String workAdd = (String) caseData.get("workAdd");
                caseItem.setWorkAdd(workAdd);
                String familyAdd = (String) caseData.get("familyAdd");
                caseItem.setFamilyAdd(familyAdd);
                String age = (String) caseData.get("age");
                caseItem.setAge(age);

                String putoutTime = (String) caseData.get("putoutTime");
                caseItem.setPutoutTime(putoutTime);
                caseItems.add(caseItem);
                System.out.println("have get size :" + caseItems.size() + "");
                System.out.println(caseItem.toString());
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    System.out.println("sleep failed!");
//                }
            }
        }
        System.out.println(caseItems.size());
        EasyExcel.write("files/案件导入模板-标准案件模板.xls", CaseItem.class).withTemplate(PathUtils.getPingAnTempatePath()).sheet().doWrite(caseItems);
    }


    public Map<String, Object> getPageContentList(int page) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://bfiles.pingan.com.cn/brcp/stp/openapi/servgw/servicegateway/casedeal/selectCaseList");
//        https://bfiles.pingan.com.cn/brcp/stp/openapi/servgw/servicegateway/casedeal/selectCaseList
        String json = String.format("{\"isfocus\":\"\",\"islawcase\":2,\"size\":50,\"page\":\"%d\"}", page);
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
//        response.close();
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
//        response.close();
        return JSONUtil.toMap(sb.toString());
    }


}
