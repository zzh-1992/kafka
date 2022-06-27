package com.grapefruit.zzh;

import com.grapefruit.zzh.sender.KafkaSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * kafka消息提供者
 *
 * @author Grapefruit
 * @version 1.0
 * @date 2021/4/22
 */
@RequestMapping("/")
@RestController
public class MsgProvider {
    /**
     * kafka发送工具类
     */
    @Autowired
    KafkaSender sender;

    @RequestMapping("/send")
    public String index(@PathParam("msg") String msg) {
        return sender.send(msg);
    }
}
