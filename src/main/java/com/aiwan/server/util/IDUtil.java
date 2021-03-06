package com.aiwan.server.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author dengzebiao
 * id生成工具
 * */
public class IDUtil {
    private static long tmpID = 0;

    private static boolean tmpIDlocked = false;

    public static synchronized long getId()
    {
        long ltime = 0;

        //当前：（年、月、日、时、分、秒、毫秒）*10000
        ltime = Long.valueOf(new SimpleDateFormat("yyMMddhhmmssSSS").format(new Date()).toString()) * 10000;
        if(tmpID < ltime)
        {
            tmpID = ltime;
        }
        else
        {
            tmpID = tmpID + 1;
            ltime = tmpID;
        }
        tmpIDlocked = false;
        return ltime;
    }
}
