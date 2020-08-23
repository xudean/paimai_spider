package com.xudean.spider.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.xudean.config.SpiderControlConfig;
import com.xudean.pojo.HouseItem;
import com.xudean.spider.ISpider;
import com.xudean.util.DateUtil;
import com.xudean.util.JSONUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xuda
 */
@Slf4j
public class JdFangChanSpiderImpl implements ISpider {
    private static final String DONG_GUAN_JD_INDEX_URL = "https://auction.jd.com/sifa_list.html?childrenCateId=12728";
    //拼接每页的连接
    private static final String REQ_DATA = "{\"apiType\":2,\"page\":\"%s\",\"pageSize\":40,\"reqSource\":0,\"childrenCateId\":\"12728\",\"provinceId\":19,\"cityId\":1655}";
    private static final String EVERY_PAGE_FORMAT = "https://api.m.jd.com/api?appid=paimai-search-soa&functionId=paimai_unifiedSearch&body=%s&loginType=3";

    //拼接获取详情的连接
    private static final String ITEM_INFO_BASIC_DATA = "{\"paimaiId\":%s}";
    private static final String ITEM_INFO_BASIC_URL = "https://api.m.jd.com/api?appid=paimai&functionId=getProductBasicInfo&body=%s&loginType=3";

    //    拼接获取面积、产权号等的连接
    private static final String AREA_INFO_BASIC_DATA = "{\"paimaiId\":%s,\"source\":0}";
    private static final String AREA_INFO_BASIC_URL = "https://api.m.jd.com/api?appid=paimai&functionId=queryProductDescription&body=%s&loginType=3";

    //    附件信息下载地址
    private static final String FILE_INFO_BASIC_DATA = "{\"custom\":0,\"paimaiId\":%s,\"source\":0}";
    private static final String FILE_INFO_BASIC_URL = "https://api.m.jd.com/api?appid=paimai&functionId=queryAttachFilesForIntro&body=%s&loginType=3";


    private List<HouseItem> allHouse;
    private ExecutorService cachedThreadPool;
    private String datePath;

    /**
     * 爬取的条数
     */
    private AtomicInteger index = new AtomicInteger(1);

    public JdFangChanSpiderImpl(int threadNums,String datePath) {
        this.allHouse = new CopyOnWriteArrayList<>();
        this.cachedThreadPool = Executors.newFixedThreadPool(threadNums);
        this.datePath = datePath;
    }


    @Override
    public void startSpider() throws IOException {
        Map<String, Object> rspDataMap = getRspDataMap("1");
        Integer totalItem = (Integer) rspDataMap.get("totalItem");
        Integer pageSize = (Integer) rspDataMap.get("pageSize");
        Integer totalPage = totalItem % pageSize == 0 ? (totalItem / pageSize) : (totalItem / pageSize + 1);
        for (int i = 1; i <= totalPage; i++) {
            log.info("===========当前爬取第{}页===========", i);
            if(i==2 && SpiderControlConfig.isDebug==true){
                break;
            }
            List<Map<String, Object>> thisPageDataList = getDataListOfThisPage(i);
            for (Map<String, Object> dataMap : thisPageDataList) {
                cachedThreadPool.execute(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        getHouseDetail(dataMap);
                        log.info("----------已爬取{}条---------------", index.incrementAndGet());
                    }
                });

            }
        }

        while (true) {
            if (((ThreadPoolExecutor) cachedThreadPool).getActiveCount()==0) {
                log.info("京东-所有线程执行完毕，开始保存文件");
                break;
            }
            try {
                //等待三秒再检查
                Thread.sleep(3000);
            } catch (Exception e) {

            }
        }
        //保存Excel
        EasyExcel.write("files/"+datePath+"/京东-东莞住宅用房拍卖-司法拍卖-阿里拍卖_拍卖房产汽车车牌土地海关罚没等.xlsx", HouseItem.class)
                .sheet().doWrite(allHouse);
        log.info("保存文件成功！");
        cachedThreadPool.shutdown();

    }


    /**
     * 获取每件拍卖房产的详情
     *
     * @param dataMap
     */
    private void getHouseDetail(Map<String, Object> dataMap) throws IOException {
        HouseItem houseItem = new HouseItem();
        String id = dataMap.get("id").toString();
        //房屋地址
        houseItem.setHouseAddress(dataMap.get("title").toString());
        log.info("开始爬取【{}】的信息", houseItem.getHouseAddress());
        //评估价
        houseItem.setAppraisalPrice(dataMap.get("assessmentPriceCN").toString());


        //开拍时间
        Object endTimeMills = dataMap.get("startTimeMills");
        Date date = new Date(Long.valueOf(endTimeMills.toString()));
        houseItem.setStartDate(DateUtil.formatDate(date));

        //处置单位
        houseItem.setCourt(dataMap.get("shopName").toString());
        //户型-无
        //首付-无
        //保证金
        houseItem.setEnsurePay(dataMap.get("ensurePrice").toString());


        //詳情頁面
        houseItem.setUrl("https://paimai.jd.com/" + id);
        //文件连接
        String detailUrl = getItemDetailUrl(id);
        Document document = Jsoup.connect(detailUrl)
                .timeout(5000).get();
        Elements body = document.getElementsByTag("body");
        //detailmap中包含了图片地址
        Map<String, Object> detailRspMap = JSONUtil.toMap(body.text());
        Map<String, Object> detailMap = (Map<String, Object>) detailRspMap.get("data");
        //开拍价格
        houseItem.setOpeningPrice(detailMap.get("startPrice").toString());
        //todo 面积
        //todo 产权证号
//        https://api.m.jd.com/api?appid=paimai&functionId=queryProductDescription&body={"paimaiId":116364001,"source":0}&loginType=3
        String areaUrl = getAreaDetailUrl(id);
        Document documentArea = Jsoup.connect(areaUrl)
                .timeout(5000).get();
        Elements tr = documentArea.getElementsByTag("tr");
        for (Element element : tr) {
            String text = element.text();
            if (text.contains("建筑面积") && text.contains("房产登记")) {
                String area = StrUtil.subBetween(text, "建筑面积：", "平方米");
                String no = StrUtil.subBetween(text, "房产登记号：", "号，");
                houseItem.setAreaSize(area.replace("△ ☆ ※","").replace("☆ ※ ",""));
                houseItem.setCertificateNo(no);
                break;
            }
            if (element.text().contains("建筑面积") && element.text().contains("㎡")) {
                houseItem.setAreaSize(element.text());
            }
        }

        //保存图片,图片地址，也可以从上面详情中拿到
        Elements imgEles = documentArea.getElementsByTag("img");
        for (Element element : imgEles) {
            String imgUri = "https:" + element.attr("src");
            String localPath = saveToImages(imgUri, houseItem.getHouseAddress());
            houseItem.setLocalPath(localPath);
            houseItem.setHasPhoto("有");
        }
//附件地址：https://api.m.jd.com/api?appid=paimai&functionId=queryAttachFilesForIntro&body={"custom":0,"paimaiId":116364001,"source":0}&loginType=3
        //保存附件
        String fileUrl = getAttachFilelUrl(id);
        Document documentFile = Jsoup.connect(fileUrl)
                .timeout(5000).get();
        String fileBody = documentFile.getElementsByTag("body").text();
        Map<String, Object> urlList = JSONUtil.toMap(fileBody);
        ArrayList<Map<String, Object>> data = (ArrayList) urlList.get("data");
        for (Map<String, Object> detail : data) {
            Object url = detail.get("attachmentAddress");
            Object filename = detail.get("attachmentName");
            if (url != null && filename != null) {
                String localPath = saveToAttachFile(url.toString(), houseItem.getHouseAddress(), filename.toString());
                houseItem.setLocalPath(localPath);
            }
        }
        allHouse.add(houseItem);

    }


    /**
     * 获取响应值中的数据部分
     *
     * @param page
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> getDataListOfThisPage(int page) throws IOException {
        Map<String, Object> rspDataMap = getRspDataMap(page + "");
        return (ArrayList<Map<String, Object>>) rspDataMap.get("datas");
    }

    /**
     * 获取响应值
     *
     * @param pageStr
     * @return
     * @throws IOException
     */
    public Map<String, Object> getRspDataMap(String pageStr) throws IOException {
        String pageUrl = getPageUrl(pageStr);
        Document document = Jsoup.connect(pageUrl).header("Referer", "https://auction.jd.com/sifa_list.html?childrenCateId=12728")
                .timeout(5000).get();
        Elements body = document.getElementsByTag("body");
        return JSONUtil.toMap(body.text());
    }

    /**
     * 拼接的到正确的地址
     *
     * @param currentPage
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getPageUrl(String currentPage) throws UnsupportedEncodingException {
        String url = String.format(REQ_DATA, currentPage + "");
        String encodeUrl = URLEncoder.encode(url, Charset.defaultCharset().name());
        return String.format(EVERY_PAGE_FORMAT, encodeUrl);
    }

    /**
     * 获取商品详情页的URL
     */
    public String getItemDetailUrl(String id) throws UnsupportedEncodingException {
        String detailUrl = String.format(ITEM_INFO_BASIC_DATA, id);
        String encodeUrl = URLEncoder.encode(detailUrl, Charset.defaultCharset().name());
        return String.format(ITEM_INFO_BASIC_URL, encodeUrl);
    }

    /**
     * 获取可以解析面积、产权号的URL
     *
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAreaDetailUrl(String id) throws UnsupportedEncodingException {
        String detailUrl = String.format(AREA_INFO_BASIC_DATA, id);
        String encodeUrl = URLEncoder.encode(detailUrl, Charset.defaultCharset().name());
        return String.format(AREA_INFO_BASIC_URL, encodeUrl);
    }

    /**
     * 获取可以解析面积、产权号的URL
     *
     * @param id
     * @return
     * @throws UnsupportedEncodingException
     */
    public String getAttachFilelUrl(String id) throws UnsupportedEncodingException {
        String detailUrl = String.format(FILE_INFO_BASIC_DATA, id);
        String encodeUrl = URLEncoder.encode(detailUrl, Charset.defaultCharset().name());
        return String.format(FILE_INFO_BASIC_URL, encodeUrl);
    }

    // 爬取网络的图片到本地
    public String saveToImages(String destUrl, String dirName) throws IOException {
        URL url = new URL(destUrl);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.connect();
        InputStream inputStream = httpUrl.getInputStream();
        //将图片保存成files/房产地址/xxx.jpg的形式
        String filePath = "files/"+datePath+"/京东/" + dirName + "/images/" + UUID.randomUUID().toString() + ".jpg";
        downloadImg(inputStream, filePath);
        return new File("files/"+datePath+"/京东/" + dirName ).getAbsolutePath();
    }


    public boolean downloadImg(InputStream inputStream, String path) {
        boolean flag = true;
        File file = new File(path.replace("?", "").replace("!", "").replace("【", "").replace("】", ""));
        File fileParent = file.getParentFile();
        if (!fileParent.exists()) {
            fileParent.mkdirs();//创建路径
        }
        try {
            FileUtils.copyToFile(inputStream, file);
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    // 爬取附件
    public String saveToAttachFile(String destUrl, String dirName, String filename) throws IOException {
        URL url = new URL(destUrl);
        HttpURLConnection httpUrl = (HttpURLConnection) url.openConnection();
        httpUrl.connect();
        String replace = filename.replaceAll("\\\\", "");
        InputStream inputStream = httpUrl.getInputStream();
        //将图片保存成files/2020-0211/房产地址/xxx.jpg的形式
        String filePath = "files/"+datePath+"/京东/" + dirName + "/attach/" + replace;
        downloadImg(inputStream, filePath);
        return new File(filename).getAbsolutePath();
    }

}
