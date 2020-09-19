package com.xudean.spider;

import com.alibaba.excel.EasyExcel;
import com.xudean.handler.CustomCellWriteHandler;
import com.xudean.pojo.HouseItem;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    public static void main(String[] args) {
        //保存Excel
        List<HouseItem> allHouse = new ArrayList<>();
        HouseItem item = new HouseItem();
        item.setAppraisalPrice("100");
        item.setHouseAddress("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        allHouse.add(item);
        String templateFileName = "C:\\Users\\xuda\\Downloads\\淘宝-东莞住宅用房拍卖-司法拍卖-阿里拍卖_拍卖房产汽车车牌土地海关罚没等.xlsx";
        EasyExcel.write("files/demo.xlsx", HouseItem.class).registerWriteHandler(new CustomCellWriteHandler()).withTemplate(templateFileName).sheet().doWrite(allHouse);
    }
}
