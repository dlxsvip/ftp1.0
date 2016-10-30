package com.yyl.client.model;

/**
 * Created by yl on 2016/10/27.
 */
public class FtpConf {

    /**
     * ftp配置文件
     */
    public static final String CONFIG = "/conf/ftp.cfg";

    /**
     * ftp 服务器地址
     */
    private String host;

    /**
     * 端口
     */
    private int port;

    /**
     * 登录名
     */
    private String name;

    /**
     * 登录密码
     */
    private String password;


    /**
     * ftp 服务器根目录下的 子目录
     */
    private String remotePath;

    /**
     * 本地目录
     */
    private String localPath;

    /**
     * 上传的文件
     */
    private String putFiles;

    /**
     * 下载的文件
     */
    private String getFiles;

    /**
     * 定时 延时
     */
    private String delay;

    /**
     * 定时 周期循环
     */
    private String period;


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRemotePath() {
        return remotePath;
    }

    public void setRemotePath(String remotePath) {
        this.remotePath = remotePath;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getPutFiles() {
        return putFiles;
    }

    public void setPutFiles(String putFiles) {
        this.putFiles = putFiles;
    }

    public String getGetFiles() {
        return getFiles;
    }

    public void setGetFiles(String getFiles) {
        this.getFiles = getFiles;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
