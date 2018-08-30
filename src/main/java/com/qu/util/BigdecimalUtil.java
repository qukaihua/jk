package com.qu.util;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2018/6/23 0023.
 */

public class BigdecimalUtil {

    public static Double mut(Double number,Double price){
        BigDecimal number1 = new BigDecimal(number.toString());
        BigDecimal price1 = new BigDecimal(price.toString());
        return number1.multiply(price1).doubleValue();
    }
}