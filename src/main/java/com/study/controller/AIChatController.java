package com.study.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AIChatController {

    private final ChatClient chatClient;

    // 让 Spring 自动把配置好的 AI 客户端注进来
    public AIChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai/chat")
    public String chat(@RequestParam("message") String message) {
        try {
            // 调用 Spring AI 发送消息
            return chatClient.prompt()
                    // 给 AI 设定人设（System Prompt）
                    .system("你是一个租房和二手物品交易网站的智能客服，你的名字叫'小管家'。请用简短、热情、友好的语气回答用户的问题。如果用户问关于网站怎么用的问题，请引导他们查看网站的导航栏。")
                    // 用户的真实问题
                    .user(message)
                    .call()
                    .content();
        } catch (Exception e) {
            e.printStackTrace();
            return "哎呀，客服大脑短路了，请稍后再试一下哦~";
        }
    }
}