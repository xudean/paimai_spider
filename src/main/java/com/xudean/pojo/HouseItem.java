package com.xudean.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;


@Data
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
     * 开拍价格
     */
    @ExcelProperty("开拍价格")
    private String openingPrice;
    /**
     * 开拍时间
     */
    @ExcelProperty("开拍时间")
    private String startDate;
    /**
     * 处置单位
     */
    @ExcelProperty("处置单位")
    private String court;

    /**
     * 户型
     */
    @ExcelProperty("户型")
    private String houseType;

    /**
     * 面积
     */
    @ExcelProperty("面积")
    private String areaSize;

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

    /**
     * 保证金
     */
    @ExcelProperty("保证金")
    private String ensurePay;

    /**
     * 产权证号
     */
    @ExcelProperty("产权证号")
    private String certificateNo;

    /**
     * 页面链接
     */
    @ExcelProperty("房产详情地址")
    private String url;

    @ExcelProperty("本地文件路径")
    private String localPath;

}
