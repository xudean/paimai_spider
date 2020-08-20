package com.xudean.spider;
import com.xudean.pojo.HouseItem;
import com.xudean.util.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
public class AliFangChanSpier {
    /**
     * 东莞房产初始URL
     */
    private static final String DONG_GUAN_FANGCHAN_URL_PREFIX = "https://sf.taobao.com/item_list.htm?spm=a213w.7398504.pagination.2.63f56888yZmS5j&category=50025969&auction_source=0&city=%B6%AB%DD%B8&st_param=-1&auction_start_seg=-1&page=";
    private static final String DONG_GUAN_FANGCHAN_URL_INDEX = "https://sf.taobao.com/item_list.htm?spm=a213w.7398504.pagination.2.63f56888yZmS5j&category=50025969&auction_source=0&city=%B6%AB%DD%B8&st_param=-1&auction_start_seg=-1&page=1";

    public static void startSpider() throws IOException {
        Integer totalPage = getStartPageAndEndPage();
        log.info("获取到总页数:{}",totalPage);
        for(int i=1;i<=totalPage;i++){
            //分别获取每页的房产信息
            String nextUrl = getNextUrl(i);
            Document document = Jsoup.connect(nextUrl).timeout(5000).get();
            String oriData = document.getElementById("sf-item-list-data").toString();
            //接下来对数据进行处理
            //1.找到“>”标签的位置
            int start = oriData.indexOf(">");
            //2.找到"</"标签的位置
            int end = oriData.indexOf("</");
            //截取出值
            String data = oriData.substring(start+1, end);
            Map<String, Object> itemsListMap = JSONUtil.toMap(data);
            ArrayList<Map<String,Object>> items = (ArrayList<Map<String, Object>>) itemsListMap.get("data");
            //循环获取每页的商品详情
            for(Map<String,Object> item:items){
                Object itemUrl = item.get("itemUrl");
                Document detailItem = Jsoup.connect(getItemDetailUrl(itemUrl.toString())).timeout(5000).get();
                getItemDetailInfo(detailItem);
            }

        }
    }


    /**
     * 解析具體的商品詳情
     */
    private static void getItemDetailInfo(Document document){
        HouseItem houseItem = new HouseItem();
        Elements h1 = document.getElementsByTag("h1");
        houseItem.setHouseAddress(excludeTag(h1.text()));

    }
    /**
     *
     * @return
     * @throws IOException
     */
    private static Integer getStartPageAndEndPage() throws IOException {
        Document document = Jsoup.connect(DONG_GUAN_FANGCHAN_URL_INDEX).timeout(5000).get();
        Elements select = document.select("em[class=page-total]");
        Element total = select.first();
        return Integer.valueOf(total.text());
    }

    /**
     * 拼接URL
     * @param nextPageNum
     * @return
     */
    private static String getNextUrl(Integer nextPageNum){
        return DONG_GUAN_FANGCHAN_URL_PREFIX+nextPageNum;
    }

    private static String getItemDetailUrl(String subUrl){
        return "https:"+subUrl;
    }

    private static String excludeTag(String ori){
        return ori.replace("变卖","").replace("一拍","").replace("二拍","");
    }
}
