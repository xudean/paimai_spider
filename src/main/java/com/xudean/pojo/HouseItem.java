package com.xudean.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import lombok.Data;


@ContentRowHeight(29)
public class HouseItem {
    /**
     * 房源地址
     */
    @ExcelProperty("房源地址")
    private String houseAddress;
    /**
     * 评估价格
     */
    @ExcelProperty("评估价格")
    private String appraisalPrice;
    /**
     * 保证金
     */
    @ExcelProperty("保证金")
    private String ensurePay;
    /**
     * 开拍价格
     */
    @ExcelProperty("开拍价格")
    private String openingPrice;

    /**
     *增价幅度
     */
    @ExcelProperty("增价幅度")
    private String priceStep;

    /**
     * 面积
     */
    @ExcelProperty("面积")
    private String areaSize;
    /**
     * 开拍时间
     */
    @ExcelProperty("开拍时间")
    private String startDate;

    /**
     * 看样时间
     */
    @ExcelProperty("看样时间")
    private String seeDemoTime;
//    /**
//     * 处置单位
//     */
//    @ExcelProperty("处置单位")
//    private String court;

    /**
     * 首付
     */
    @ExcelProperty("首付")
    private String firstPay;

    /**
     * 是否有照片
     */
    @ExcelProperty("是否有照片")
    private String hasPhoto;


//    /**
//     * 产权证号
//     */
//    @ExcelProperty("产权证号")
//    private String certificateNo;

    /**
     * 页面链接
     */
    @ExcelProperty("房产详情地址")
    private String url;

    @ExcelProperty("本地文件路径")
    private String localPath;


    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getAppraisalPrice() {
        return appraisalPrice;
    }

    public void setAppraisalPrice(String appraisalPrice) {
        this.appraisalPrice = appraisalPrice;
    }

    public String getEnsurePay() {
        return ensurePay;
    }

    public void setEnsurePay(String ensurePay) {
        this.ensurePay = ensurePay;
    }

    public String getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(String openingPrice) {
        this.openingPrice = openingPrice;
    }


    public String getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(String areaSize) {
        this.areaSize = areaSize;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getPriceStep() {
        return priceStep;
    }

    public void setPriceStep(String priceStep) {
        this.priceStep = priceStep;
    }

    public String getSeeDemoTime() {
        return seeDemoTime;
    }

    public void setSeeDemoTime(String seeDemoTime) {
        this.seeDemoTime = seeDemoTime;
    }

    public String getFirstPay() {
        return firstPay;
    }

    public void setFirstPay(String firstPay) {
        this.firstPay = firstPay;
    }

    public String getHasPhoto() {
        return hasPhoto;
    }

    public void setHasPhoto(String hasPhoto) {
        this.hasPhoto = hasPhoto;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }
}
