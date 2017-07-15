package com.github.wei86609.osmanthus.translator;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

public class FileRuleSetTranslatorTest extends TestCase {

    public void testGetNodes() {
        String idNo="340823198609122917";
        int leh = idNo.length();
        String dates="";
        if (leh == 18) {
            dates = idNo.substring(6, 10);
            SimpleDateFormat df = new SimpleDateFormat("yyyy");
            String year=df.format(new Date());
            int u=Integer.parseInt(year)-Integer.parseInt(dates);
            System.out.println(u);
        }else{
            dates = idNo.substring(6, 8);
            System.out.println(dates);
        }

    }

}
