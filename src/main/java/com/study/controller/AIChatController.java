package com.study.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class AIChatController {

    private final ChatClient chatClient;
    // 开启内存级聊天记忆（记住用户的上下文）
    private final ChatMemory chatMemory = new InMemoryChatMemory();

    // 简易敏感词库
    private final List<String> SENSITIVE_WORDS = Arrays.asList("傻逼", "去死", "暴力", "违禁词");

    // 知识库 FAQ（常见问题解答）
    private final String FAQ_KNOWLEDGE = """
        【知识库FAQ】
        1. 怎么退租？答：请在个人中心点击“申请退租”，等待房东审核。
        2. 怎么支付？答：我们支持支付宝扫码支付。
        3. 联系不上房东怎么办？答：请拨打平台24小时客服热线 1900-8888。
        """;

    public AIChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai/chat")
    public String chat(@RequestParam("message") String message, HttpServletRequest request) {

        // ================= 1. 敏感词检测 =================
        for (String word : SENSITIVE_WORDS) {
            if (message.contains(word)) {
                return "⚠️ 小管家提示：您的话语中包含敏感词汇，请注意文明用语哦。";
            }
        }

        // 获取用户的当前 Session ID，用来区分不同人的记忆
        String sessionId = request.getSession().getId();

        try {
            // ================= 2. 调用 Spring AI 生成回答 =================
            return chatClient.prompt()
                    // 设定系统人设，并注入 FAQ 知识库
                    .system("你是一个专业的租房系统智能客服小管家。" +
                            "请保持友好、热情的态度。" +
                            "当用户问到常规问题时，请参考以下知识库回答：" + FAQ_KNOWLEDGE +
                            "当你需要查商品或查订单时，请直接调用工具。")
                    // 用户说的话
                    .user(message)
                    // 绑定工具箱（填入刚才 AiToolConfig 里面 @Bean 的名字）
                    .functions("queryGoodsTool", "queryOrderTool")
                    // 开启历史记忆，记住这个 sessionId 过去 10 条的对话记录
                    .advisors(new MessageChatMemoryAdvisor(chatMemory, sessionId, 10))
                    .call()
                    .content();

        } catch (Exception e) {
            e.printStackTrace();
            return "小管家的大脑暂时短路了，可能是 API 额度不足或网络异常，请联系管理员。";
        }
    }
}