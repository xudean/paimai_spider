/**
 * Copyright 2020 bejson.com
 */
package com.xudean.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class CaseItem {
    @ExcelProperty("个案序列号")
    private String serialno;
    @ExcelProperty("委案日期")
    private String outsourceStartDate;
    @ExcelProperty("委案金额")
    private String entrustBalance;
    ///添加一些没用的字段，为了和模板匹配上
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private String reserve4;
    private String reserve5;
    private String reserve6;
    ///
    @ExcelProperty("姓名")
    private String customerName;
    @ExcelProperty("卡号")
    private String certNo;
    @ExcelProperty("证件号")
    private String certId;
    ///添加一些没用的字段，为了和模板匹配上
    private String reserve7;
    private String reserve8;
    private String reserve9;
    private String reserve10;
    private String reserve11;
    private String reserve12;
    private String reserve13;
    private String reserve14;
    private String reserve15;
    ///
    @ExcelProperty("本人手机")
    private String mobiletelePhone;
    private String reserve16;
    private String reserve17;
    private String reserve18;
    @ExcelProperty("单位地址")
    private String workAdd;
    private String reserve19;
    @ExcelProperty("家庭地址")
    private String familyAdd;
    private String reserve20;
    private String reserve21;
    private String reserve22;
    private String reserve23;
    private String reserve24;
    private String reserve25;
    private String reserve26;
    private String reserve27;
    @ExcelProperty("年龄")
    private String age;
    private String reserve28;
    private String reserve29;
    private String reserve30;
    @ExcelProperty("预计退案日")
    private String outsourceEndDate;
    private String reserve31;
    private String reserve32;
    private String reserve33;
    private String reserve34;
    private String reserve35;
    private String reserve36;
    private String reserve37;
    private String reserve38;
    private String reserve39;
    private String reserve40;
    private String reserve41;
    private String reserve42;
    private String reserve43;
    private String reserve44;
    private String reserve45;
    private String reserve46;
    private String reserve47;
    private String reserve48;
    @ExcelProperty("剩余本金")
    private String balance2;
    private String reserve49;
    private String reserve50;
    private String reserve51;
    private String reserve52;
    private String reserve53;
    private String reserve54;
    private String reserve55;
    private String reserve56;
    private String reserve57;
    private String reserve58;
    private String reserve59;
    private String reserve60;
    private String reserve61;
    private String reserve62;
    private String reserve63;
    private String reserve64;
    @ExcelProperty("贷款日期")
    private String putoutTime;
    private String reserve65;
    private String reserve66;
    private String reserve67;
    @ExcelProperty("逾期天数")
    private String currentOverdueDays;
    private String reserve68;
    private String reserve69;
    private String reserve70;
    private String reserve71;
    private String reserve72;
    private String reserve73;
    @ExcelProperty("逾期余额")
    private String balance;
    @ExcelProperty("逾期本金")
    private String debtamount;
    @ExcelProperty("逾期利息")
    private String debtinte;
    private String reserve74;
    private String reserve75;
    private String reserve76;
    private String reserve77;
    @ExcelProperty("超限费")
    private String debtintefine;
    private String reserve78;
    private String reserve79;
    @ExcelProperty("原催收记录")
    private String collectionResult;
    private String reserve80;


    public String getSerialno() {
        return serialno;
    }

    public void setSerialno(String serialno) {
        this.serialno = serialno;
    }

    public String getOutsourceStartDate() {
        return outsourceStartDate;
    }

    public void setOutsourceStartDate(String outsourceStartDate) {
        this.outsourceStartDate = outsourceStartDate;
    }

    public String getEntrustBalance() {
        return entrustBalance;
    }

    public void setEntrustBalance(String entrustBalance) {
        this.entrustBalance = entrustBalance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getMobiletelePhone() {
        return mobiletelePhone;
    }

    public void setMobiletelePhone(String mobiletelePhone) {
        this.mobiletelePhone = mobiletelePhone;
    }

    public String getWorkAdd() {
        return workAdd;
    }

    public void setWorkAdd(String workAdd) {
        this.workAdd = workAdd;
    }

    public String getFamilyAdd() {
        return familyAdd;
    }

    public void setFamilyAdd(String familyAdd) {
        this.familyAdd = familyAdd;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOutsourceEndDate() {
        return outsourceEndDate;
    }

    public void setOutsourceEndDate(String outsourceEndDate) {
        this.outsourceEndDate = outsourceEndDate;
    }

    public String getBalance2() {
        return balance2;
    }

    public void setBalance2(String balance2) {
        this.balance2 = balance2;
    }

    public String getPutoutTime() {
        return putoutTime;
    }

    public void setPutoutTime(String putoutTime) {
        this.putoutTime = putoutTime;
    }

    public String getCurrentOverdueDays() {
        return currentOverdueDays;
    }

    public void setCurrentOverdueDays(String currentOverdueDays) {
        this.currentOverdueDays = currentOverdueDays;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getDebtamount() {
        return debtamount;
    }

    public void setDebtamount(String debtamount) {
        this.debtamount = debtamount;
    }

    public String getDebtinte() {
        return debtinte;
    }

    public void setDebtinte(String debtinte) {
        this.debtinte = debtinte;
    }

    public String getDebtintefine() {
        return debtintefine;
    }

    public void setDebtintefine(String debtintefine) {
        this.debtintefine = debtintefine;
    }

    public String getCollectionResult() {
        return collectionResult;
    }

    public void setCollectionResult(String collectionResult) {
        this.collectionResult = collectionResult;
    }

    @Override
    public String toString() {
        return "CaseItem{" +
                "serialno='" + serialno + '\'' +
                ", outsourceStartDate='" + outsourceStartDate + '\'' +
                ", entrustBalance='" + entrustBalance + '\'' +
                ", customerName='" + customerName + '\'' +
                ", certNo='" + certNo + '\'' +
                ", certId='" + certId + '\'' +
                ", mobiletelePhone='" + mobiletelePhone + '\'' +
                ", workAdd='" + workAdd + '\'' +
                ", familyAdd='" + familyAdd + '\'' +
                ", age='" + age + '\'' +
                ", outsourceEndDate='" + outsourceEndDate + '\'' +
                ", balance2='" + balance2 + '\'' +
                ", putoutTime='" + putoutTime + '\'' +
                ", currentOverdueDays='" + currentOverdueDays + '\'' +
                ", balance='" + balance + '\'' +
                ", debtamount='" + debtamount + '\'' +
                ", debtinte='" + debtinte + '\'' +
                ", debtintefine='" + debtintefine + '\'' +
                ", collectionResult='" + collectionResult + '\'' +
                '}';
    }
}