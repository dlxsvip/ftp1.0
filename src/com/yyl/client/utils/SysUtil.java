package com.yyl.client.utils;

/**
 * Created byyyll on 2016/10/19.
 */
public class SysUtil {

    public static void print(Object str) {
        System.out.print(str);
    }

    public static void println(Object str) {
        System.out.println(str);
    }

    public static String osName() {
        return System.getProperty("os.name");
    }

    public static String osArch() {
        return System.getProperty("os.arch");
    }

    public static boolean isWin() {
        return osName().toUpperCase().contains("WINDOWS");
    }

    public static boolean isLinux() {
        return osName().toUpperCase().contains("LINUX");
    }

    public static String jVersion() {
        return System.getProperty("java.version");
    }

    public static String jHome() {
        return System.getProperty("java.home");
    }

    public static String vmName() {
        return System.getProperty("java.vm.name");
    }


    public static String classPath() {
        return System.getProperty("java.class.path");
    }

    //用户的账户名称
    public static String uName() {
        return System.getProperty("user.name");
    }

    //用户的主目录
    public static String uHome() {
        return System.getProperty("user.home");
    }

    //用户的当前工作目录
    public static String uDir() {
        return System.getProperty("user.dir");
    }

    //文件分隔符（在 UNIX 系统中是“/”）
    public static String fileSep() {
        return System.getProperty("file.separator");
    }

    //路径分隔符（在 UNIX 系统中是“:”）
    public static String pathSep() {
        return System.getProperty("path.separator");
    }

    //行分隔符（在 UNIX 系统中是“/n”）
    public static String lineSep() {
        return System.getProperty("line.separator");
    }
}
