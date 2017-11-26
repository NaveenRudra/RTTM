package com.realtimescrapper.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;


public class PropertyUtilities {
    private static final Pattern DOT_REGEXP = Pattern.compile("\\.");

    public static Properties filterAndShiftByFirstDot(Properties p, String prefix) {
        Properties result = new Properties();
        for (String key : p.stringPropertyNames()) {
            String[] split = splitByFirstDot(key);
            String kfirst=split[0];
            if (prefix.equals(kfirst)) {
                String knew = split.length > 1 ? split[1] : "";
                result.setProperty(knew, p.getProperty(key));
            }
        }
        return result;
    }
    
    public static Map<String, Properties> propertiesGroupByFirstDot(Properties p) {
        HashMap<String, Properties> result = new HashMap<String, Properties>();
        for (String key : p.stringPropertyNames()) {
            String[] split = splitByFirstDot(key);
            String kfirst=split[0];
            String knew=split.length>1 ? split[1] : "";
            Properties vp; 
            if (result.containsKey(kfirst)) {
                vp = result.get(kfirst);
            } else {
                vp = new Properties();
                result.put(kfirst, vp);
            }
            vp.setProperty(knew, p.getProperty(key));
        }
        return result;
    }
    
    public static String[] splitByFirstDot(String s) {
        return DOT_REGEXP.split(s, 2);
    }
}
