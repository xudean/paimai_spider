package com.xudean.runner;

import com.xudean.spider.AliFangChanSpier;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SpiderRunner implements ApplicationRunner {

    @Resource
    private AliFangChanSpier aliFangChanSpier;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        aliFangChanSpier.startSpider();
    }
}
