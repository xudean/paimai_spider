package com.xudean.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class HouseItem {
    /**
     * 房源地址
     */
    private String houseAddress;
    /**
     * 评估价格
     */
    private Double appraisalPrice;

    /**
     * 开拍价格
     */
    private Double openingPrice;
    /**
     * 开拍时间
     */
    private Date startDate;
    /**
     * 处置单位
     */
    private String court;

    /**
     * 户型
     */
    private String houseType;

    /**
     * 面积
     */
    private int areaSize;

    /**
     * 首付
     */
    private int firstPay;

    /**
     * 是否有照片
     */
    private int hasPhoto;

    /**
     * 保证金
     */
    private int ensurePay;

    /**
     * 产权证号
     */
    private String certificateNo;


}
