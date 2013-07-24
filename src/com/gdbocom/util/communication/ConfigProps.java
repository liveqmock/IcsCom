package com.gdbocom.util.communication;

import java.io.InputStream;
import java.util.Properties;

/**
 * Properties类的单例实现。初始化后将寻找项目中的config.properties文件，并加载。
 * 
 * @author qm
 * 
 */
public class ConfigProps extends Properties {

    private static final long serialVersionUID = 1L;
    private static volatile ConfigProps INSTANCE = null;

    /**
     * 获取单例
     * 
     * @return Props单例
     */
    public static ConfigProps getInstance() {
        if (null == INSTANCE) {
            synchronized (ConfigProps.class) {
                if (null == INSTANCE) {
                    INSTANCE = new ConfigProps();
                    try {
                        InputStream is = Thread.currentThread()
                              .getContextClassLoader()
                              .getResourceAsStream("config.properties");
                        /*InputStream is = ClassLoader
                                .getSystemResourceAsStream("config.properties");*/
                        INSTANCE.load(is);

                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new IllegalArgumentException();
                    }
                }
            }
        }
        return INSTANCE;
    }

    /* 不使用 */
    private ConfigProps() {
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {

        System.out.println(ConfigProps.getInstance().getProperty("TrmNo"));
        System.out.println(ConfigProps.getInstance().getProperty("TlrId"));
        System.out.println(ConfigProps.getInstance().getProperty("NodNo"));

    }

}
