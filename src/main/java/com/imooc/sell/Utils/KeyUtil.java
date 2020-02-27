package com.imooc.sell.Utils;

import java.util.Random;

//生成随机ID:时间+6位随机数
public class KeyUtil {
    public synchronized static String genUniqueKey(){
        Random random = new Random();
        Integer a = random.nextInt(900000) + 100000;
        return String.valueOf(a)+ System.currentTimeMillis();
    }
}
