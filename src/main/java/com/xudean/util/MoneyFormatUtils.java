package com.xudean.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MoneyFormatUtils {
    public static String convertMoneyStr(String value){
        NumberFormat nf = new DecimalFormat("#,###.####");
        Double d = Double.valueOf(value);
        return nf.format(d);
    }
}
