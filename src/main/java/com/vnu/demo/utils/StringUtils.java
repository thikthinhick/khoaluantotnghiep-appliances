package com.vnu.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
    public static String DateToString(Date date, String format) {
        SimpleDateFormat dateformatter = new SimpleDateFormat(format);
        return dateformatter.format(date);
    }
}
