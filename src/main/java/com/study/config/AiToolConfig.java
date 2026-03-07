package com.study.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.Goods;
import com.study.entity.Orders;
import com.study.service.GoodsService;
import com.study.service.OrdersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.List;
import java.util.function.Function;

@Configuration
public class AiToolConfig {

    // 1. 定义查询商品的参数结构
    public record GoodsRequest(String keyword) {}

    // 2. 提供给 AI 的【商品查询工具】
    @Bean
    @Description("当用户想要查询、寻找、购买某个房屋、商品、房源时，调用此工具。输入参数为用户想找的关键词。")
    public Function<GoodsRequest, String> queryGoodsTool(GoodsService goodsService) {
        return request -> {
            QueryWrapper<Goods> qw = new QueryWrapper<>();
            // 根据关键词模糊查询商品名
            qw.like("names", request.keyword()).eq("is_deleted", 0);
            List<Goods> list = goodsService.list(qw);

            if (list == null || list.isEmpty()) {
                return "数据库中没有找到相关的商品或房源。";
            }

            // 将查询到的结果拼接成文字，交给 AI 去总结
            StringBuilder sb = new StringBuilder("查询到的商品如下：\n");
            for (int i = 0; i < Math.min(list.size(), 3); i++) { // 最多返回3个，防止字数超限
                Goods g = list.get(i);
                sb.append(String.format("名称：%s，价格：%s元，详情介绍：%s\n",
                        g.getNames(), g.getPrice(), g.getContent()));
            }
            return sb.toString();
        };
    }

    // 3. 定义查询订单的参数结构
    public record OrderRequest(String orderNo) {}

    // 4. 提供给 AI 的【订单查询工具】
    @Bean
    @Description("当用户想要查询自己的订单状态、订单详情，且提供了订单号时，调用此工具。输入参数为订单号。")
    public Function<OrderRequest, String> queryOrderTool(OrdersService ordersService) {
        return request -> {
            QueryWrapper<Orders> qw = new QueryWrapper<>();
            qw.eq("orders_num", request.orderNo());
            Orders order = ordersService.getOne(qw);

            if (order == null) {
                return "未查到该订单号的订单，请提醒用户核对订单号。";
            }

            // 0未支付，1已支付等（根据你数据库 state 的实际含义调整）
            String status = order.getState() == 0 ? "未支付" : "已支付";
            return String.format("查到订单信息：订单号 %s，总金额 %s，当前状态为：%s",
                    order.getOrdersNum(), order.getZprice(), status);
        };
    }
}