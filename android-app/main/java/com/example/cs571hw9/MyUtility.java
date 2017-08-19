package com.example.cs571hw9;


import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class MyUtility {

    public static String formatDate(String str) {
        if (str.equals("N.A")) return "N.A";
        try {
            Date date = DateUtils.parseDate(str, "yyyy-MM-dd");
            return DateFormatUtils.format(date, "MMM dd,yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String capitalize(String str) {
        if (str.equals("N.A")) return "N.A";
        WordUtils wordUtils = new WordUtils();
        return wordUtils.capitalize(str);
    }

}
