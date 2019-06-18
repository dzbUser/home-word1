package com.aiwan.client.util;

/**
 * @author dengzebiao
 * @since 2019.6.18
 * 验证指令是否规范的工具
 * */
public class Verification {

    /** 是否可以转化为数字 */
    public static boolean canParseInt(String str){
        //验证是否为空
        if(str == null){
            return false;
        }
        //使用正则表达式判断该字符串是否为数字，第一个\是转义符，\d+表示匹配1个或 //多个连续数字，"+"和"*"类似，"*"表示0个或多个
        return str.matches("\\d+");
    }
}
