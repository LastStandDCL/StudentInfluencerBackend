package com.backend.last_stand.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Slf4j
@Component
public class DeleteFilesUtil {


    /**
     * 每周星期天凌晨1点执行，清理本地磁盘大于7天的音频文件
     */
    @Scheduled(cron = "0 0 1 ? * L")
    public void checkClenFile() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("======定时清理文件任务开始于：{}", sdf.format(new Date()));
        //文件过期天数
        int howDays = 7;
        //文件存储路径;
        String filePath = "/Users/laststand/uploadFile";
        // 执行删除方法
        int delCount = moveFileToReady(filePath, howDays);
        if (delCount > 0) {
            log.info("======本次从：{}" + filePath + "下清理" + delCount + "份文件");
        } else {
            log.info("======暂时没有要清理的文件");
        }
        log.info("======定时清理文件任务结束于：{}", sdf.format(new Date()));
    }


    /**
     * 定时清理
     * @param filePath 文件目录
     * @param howDays 清理大于的天数
     * @return 删除文件的数量
     */
    public static Integer moveFileToReady(String filePath, int howDays) {
        File srcDir = new File(filePath);
        //判断文件或路径是否存在
        if (!srcDir.exists()) {
            return 0;
        }
        //获取该目录下所有文件，返回一个数组
        File[] files = srcDir.listFiles();
        if (files == null || files.length <= 0) {
            return 0;
        }
        // 删除文件总数
        int delTotal = 0;
        //获取当前时间
        Date today = new Date();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                try {
                    File ff = files[i];
                    //获取当前文件创建时间戳
                    long time = ff.lastModified();
                    Calendar cal = Calendar.getInstance();
                    cal.setTimeInMillis(time);
                    Date lastModified = cal.getTime();
                    //获取过期天数
                    long days = getDistDates(today, lastModified);
                    if (days >= howDays) {
                        files[i].delete();
                        delTotal++;
                    }
                } catch (Exception e) {
                    log.error("定时清理文件任务失败", e);
                }
            }
        }
        return delTotal;
    }

    /**
     * 将时间戳转天数
     * @param startDate 执行方法开始
     * @param endDate 文件创建时间
     * @return 文件创建天数
     */
    public static long getDistDates(Date startDate, Date endDate) {
        long totalDate = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        long timestart = calendar.getTimeInMillis();
        calendar.setTime(endDate);
        long timeend = calendar.getTimeInMillis();
        totalDate = Math.abs((timeend - timestart)) / (1000 * 60 * 60 * 24);
        return totalDate;
    }

}
