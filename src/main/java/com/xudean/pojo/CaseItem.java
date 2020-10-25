/**
 * Copyright 2020 bejson.com
 */
package com.xudean.pojo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;



public class CaseItem {
    @ExcelProperty("个案序列号")
    private String serialno;
    @ExcelProperty("委案日期")
    private String outsourceStartDate;
    @ExcelProperty("委案金额")
    private String entrustBalance;
    @ExcelProperty("姓名")
    private String customerName;
    private String certNo;
    private String certId;
    private String mobiletelePhone;
    private String workAdd;
    private String familyAdd;
    private String age;
    @ExcelProperty("预计退案日期")
    private String outsourceEndDate;
    @ExcelProperty("剩余本金")
    private String debtAmount;
    @ExcelProperty("贷款日期")
    private String putoutTime;
    @ExcelProperty("逾期天数")
    private String currentOverdueDays;
    @ExcelProperty("逾期余额")
    private String balance;
    @ExcelProperty("逾期本金")
    private String debtamount;
    @ExcelProperty("逾期利息")
    private String debtinte;
    @ExcelProperty("超限费")
    private String debtintefine;
    @ExcelProperty("原催收记录")
    private String collectionResult;

}