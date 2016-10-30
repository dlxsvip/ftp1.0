package com.yyl.client.utils;

import java.io.*;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <br>
 * Created by yl on 2016/9/22.
 */
public class ReadUtil {

    /**
     * 读取配置文件
     *
     * @param file 配置文件路径
     * @return map
     */
    public static Map<String, String> readFile(File file, String charset) throws Exception {
        if (!file.exists()) {
            return new HashMap<String, String>();
        }

        return readInputStream(new FileInputStream(file), charset);
    }


    /**
     * 读 Properties
     *
     * @param path    文件路径
     * @param charset 字符集
     * @return Map
     */
    public static Map<String, String> readProp(String path, String charset) throws Exception {
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        return readInputStream(in, charset);
    }

    /**
     * 读 Properties
     *
     * @param in      文件流
     * @param charset 字符集
     * @return Map
     */
    public static Map<String, String> readInputStream(InputStream in, String charset) throws Exception {
        return readInputStreamReader(new InputStreamReader(in, charset));
    }


    /**
     * 读 文件流
     *
     * @return Map
     */
    public static Map<String, String> readInputStreamReader(Reader r) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        // 读取配置文件
        Properties prop = new Properties();
        prop.load(r);

        // 得到配置文件的名字
        Enumeration en = prop.propertyNames();
        while (en.hasMoreElements()) {
            String strKey = (String) en.nextElement();
            String strValue = prop.getProperty(strKey);
            map.put(strKey, strValue);
        }

        return map;
    }

    /**
     * 获取文件路径
     *
     * @param file
     * @return
     */
    public static String getFilePath(String file) {
        return ReadUtil.class.getResource(file).getPath();
    }

    /**
     * 获取父级路径
     *
     * @return 父级路径
     */
    public static String getParentPath(String path) {
        return new File(path).getParent();
    }

    /**
     * 获取 jar包路径
     *
     * @return jar包的路径
     */
    public static String getJarPath() {
        String jarFilePath = ReadUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            return URLDecoder.decode(jarFilePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return jarFilePath;
    }

    /**
     * 获取 jar包上级路径
     *
     * @return jar包的路径
     */
    public static String getJarParentPath() {
        String jarFilePath = getJarPath();

        return new File(jarFilePath).getParent();
    }


    /**
     * 获取jar包内的文件流
     *
     * @param file jar包内的文件
     * @return 流
     */
    public static InputStream getJarFile(String file) {
        InputStream jarFileStream = ReadUtil.class.getClass().getResourceAsStream(file);


        return jarFileStream;
    }

    public static void closeIOStream(BufferedReader reader) {
        if (null != reader) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
