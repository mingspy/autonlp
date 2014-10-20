package com.mingspy.walee.core;

import java.util.Properties;

public class Configuration extends Properties
{
    /**
     *
     */
    private static final long serialVersionUID = 8885066233223011328L;

    public double getDouble(String key, double defaultVal)
    {
        String val = getProperty(key);
        if(val != null) {
            return Double.parseDouble(val);
        }

        return defaultVal;
    }

    private static final Configuration _config = new Configuration();
    public static synchronized Configuration instance()
    {
        return _config;
    }
}
