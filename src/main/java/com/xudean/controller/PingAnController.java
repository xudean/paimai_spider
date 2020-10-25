package com.xudean.controller;

import com.xudean.spider.impl.PingAnSpider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class PingAnController {
    @GetMapping("/start_pingan")
    public void startPingAnSpider(@RequestParam("page") int page, @RequestParam("token") String token,@RequestParam("session")String session) throws IOException {
        PingAnSpider pingAnSpider = new PingAnSpider(50, page, token,session);
        pingAnSpider.startSpider();
    }
}
