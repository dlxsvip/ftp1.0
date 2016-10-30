package com.yyl.client;

import com.yyl.client.model.FtpConf;
import com.yyl.client.utils.*;
import com.yyl.client.utils.JsonUtil;
import com.yyl.client.utils.LogUtil;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yl on 2016/10/27.
 */
public class RunFtp {

    private static final Logger log = Logger.getLogger(RunFtp.class);

    /**
     * 本地调试开关
     */
    private static boolean localDebugger = false;

    /**
     * 上传 or 下载
     */
    private static String type;

    /**
     * ftp配置
     */
    static FtpConf ftpConf = new FtpConf();


    public static void main(String[] args) {
        LogUtil.initLog(localDebugger);

        for (int i = 0; i < args.length; i++) {
            type = args[0];
        }

        execute();
    }


    private static void execute() {
        // 读取配置文件
        readConf();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if ("get".equals(type)) {
                    get();
                } else if ("put".equals(type)) {
                    put();
                }
            }
        };

        // 创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        long delay = TimeStr.getTime(ftpConf.getDelay());
        long period = TimeStr.getTime(ftpConf.getPeriod());
        service.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);

    }

    // 读取配置文件
    private static void readConf() {
        try {
            String confPath = "";

            if (localDebugger) {
                String workPath = System.getProperty("user.dir");
                confPath = workPath + "/build" + FtpConf.CONFIG;
            } else {
                // jar上级路径
                String path = FileUtil.getJarParentPath();

                // jar上上级路径
                path = FileUtil.getParentPath(path);
                confPath = path + FtpConf.CONFIG;
            }

            confPath = FileUtil.switchPath(confPath);
            log.info("开始读取配置文件" + confPath);

            File ftpCfg = new File(confPath);

            Map<String, String> prop = ReadUtil.readFile(ftpCfg, "UTF-8");
            log.info("配置文件:" + JsonUtil.obj2str(prop));
            if (null != prop && prop.size() > 0) {
                if (prop.containsKey("host")) {
                    ftpConf.setHost(prop.get("host"));
                }
                if (prop.containsKey("port")) {
                    ftpConf.setPort(Integer.valueOf(prop.get("port")));
                }
                if (prop.containsKey("name")) {
                    ftpConf.setName(prop.get("name"));
                }
                if (prop.containsKey("pwd")) {
                    ftpConf.setPassword(prop.get("pwd"));
                }

                if (prop.containsKey("remotePath")) {
                    ftpConf.setRemotePath(prop.get("remotePath"));
                }
                if (prop.containsKey("putFile")) {
                    ftpConf.setPutFiles(FileUtil.switchPath(prop.get("putFile")));
                }

                if (prop.containsKey("localPath")) {
                    ftpConf.setLocalPath(FileUtil.switchPath(prop.get("localPath")));
                }

                if (prop.containsKey("getFile")) {
                    ftpConf.setGetFiles(prop.get("getFile"));
                }

                if (prop.containsKey("delay")) {
                    ftpConf.setDelay(prop.get("delay"));
                }

                if (prop.containsKey("period")) {
                    ftpConf.setPeriod(prop.get("period"));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void get() {
        boolean b = FtpUtil.downloadFile(ftpConf.getHost(), ftpConf.getPort(), ftpConf.getName(), ftpConf.getPassword(),
                ftpConf.getRemotePath(), ftpConf.getGetFiles(), ftpConf.getLocalPath());
        if (b) {
            System.out.println(DUtil.getCurrentStr() + " -- " + ftpConf.getGetFiles() + " -- 下载成功");
        } else {
            System.out.println(DUtil.getCurrentStr() + " -- " + ftpConf.getGetFiles() + " -- 下载失败");
        }

    }

    private static void put() {
        boolean b = FtpUtil.uploadFiles(ftpConf.getHost(), ftpConf.getPort(), ftpConf.getName(), ftpConf.getPassword(),
                ftpConf.getRemotePath(), ftpConf.getPutFiles());
        if (b) {
            System.out.println(DUtil.getCurrentStr() + " -- " + ftpConf.getPutFiles() + " -- 上传成功");
        } else {
            System.out.println(DUtil.getCurrentStr() + " -- " + ftpConf.getPutFiles() + " -- 上传失败");
        }

    }

    // 过时的方法
    private static void task() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if ("get".equals(type)) {
                    get();
                } else if ("put".equals(type)) {
                    put();
                }
            }
        };


        Timer timer = new Timer();
        long delay = TimeStr.getTime(ftpConf.getDelay());
        long period = TimeStr.getTime(ftpConf.getPeriod());
        timer.scheduleAtFixedRate(task, delay, period);
    }



}
