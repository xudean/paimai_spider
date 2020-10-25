package com.xudean.spider;

import com.alibaba.excel.EasyExcel;
import com.xudean.handler.CustomCellWriteHandler;
import com.xudean.pojo.HouseItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    private String test = "";
    public static void main(String[] args) {
        NumberFormat nf = new DecimalFormat("#,###.####");
        Double d = 147000.1;
        String str = nf.format(d);
        System.out.println(test);
    }
}
