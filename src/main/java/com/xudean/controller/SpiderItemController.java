package com.xudean.controller;

import com.xudean.config.SpiderControlConfig;
import com.xudean.db.DbOperator;
import com.xudean.pojo.SpiderItem;
import com.xudean.spider.impl.AliFangChanSpierImpl;
import com.xudean.spider.impl.JdFangChanSpiderImpl;
import com.xudean.util.DateUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class SpiderItemController {
    @Resource
    private DbOperator dbOperator;
    private Long taskId;
    private Thread aliThread;
    private Thread jdThread;

    @GetMapping("/items")
    public List<SpiderItem> getAllSpiderItems(){
        return dbOperator.selectAll();
    }
    @GetMapping("/clear")
    public void clearItems(){
        dbOperator.clear();
    }

    @GetMapping("/start_spider")
    public void startSpider(@RequestParam("thread_nums") int threadNums) throws Exception {
        String datePath = DateUtil.formatDate(new Date()).replace(" ","_").replace(":","-");
        if( SpiderControlConfig.isStart == true){
            throw new Exception("已有爬虫在运行，无法启动新的爬虫！");
        }
        SpiderControlConfig.isStart = true;
       aliThread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("################阿里爬虫启动############");
                new AliFangChanSpierImpl(threadNums,datePath).startSpider();
            }
        });
        aliThread.start();
        //启用京东爬虫
        jdThread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info("################京东爬虫启动############");
                new JdFangChanSpiderImpl(threadNums,datePath).startSpider();
            }
        });
        jdThread.start();
        //先插入一条数据
        SpiderItem spiderItem = new SpiderItem();
        spiderItem.setTaskStatus("爬取中");
        spiderItem.setTaskStorePath(new File("files/"+datePath).getAbsolutePath());
        spiderItem.setTaskStartTime(datePath);
        long insert = dbOperator.addItem(spiderItem);
        taskId = spiderItem.getTaskId();
        log.info("创建记录成功{}",datePath);
        new Thread(new Runnable() {
            @Override
            public void run() {
                syncCheckThreadIsOk();
            }
        }).start();
    }

    public void syncCheckThreadIsOk(){
        while (true) {
            if (!aliThread.isAlive()&&!jdThread.isAlive()) {
                //两者都不处于活跃状态，任务爬虫任务结束
                log.info("所有爬虫执行完毕");
                break;
            }
            try {
                //等待三秒再检查
                Thread.sleep(3000);
            } catch (Exception e) {

            }
        }
        SpiderItem spiderItem = dbOperator.getById(taskId);
        //处理结束后的任务
        spiderItem.setTaskEndTime(DateUtil.formatDate(new Date()));
        spiderItem.setTaskStatus("爬取结束");
        dbOperator.updateItem(spiderItem);
        //取消监控
        SpiderControlConfig.isStart = false;
    }
}
