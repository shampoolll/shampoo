package com.study;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.study.entity.Goods;
import com.study.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class StudyApplicationTests {

    @Autowired
    private GoodsService goodsService;

    // ====================================================
    // ⬇️ 终极扩充版：海量真实房源生成器 ⬇️
    // ====================================================
    @Test
    public void addMassiveData() {
        System.out.println("====== 开始疯狂生成【海量真实房源】数据 ======");
        List<Goods> list = new ArrayList<>();
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 1. 扩充的房源标题库
        String[] titles = {
                "精装一室一厅，拎包入住", "近地铁两居室，采光极佳", "豪华大三居，适合家庭", "温馨单间，独卫带阳台",
                "CBD核心区公寓，视野开阔", "大学城附近高性价比好房", "朝南主卧，限女生", "复式挑高Loft，年轻人首选",
                "市中心绝版大平层，带车位", "南北通透两居室，老小区新装修", "高档小区精装四居，全套智能家居",
                "临街商住两用公寓，采光好", "花园洋房，绿化极佳，天然氧吧", "顶层带露台复式，享受私密空间"
        };

        // 2. 扩充的地址库（城市 + 区域 + 街道 + 小区）
        String[] cities = {"北京市", "上海市", "广州市", "深圳市", "杭州市", "成都市", "南京市", "武汉市", "苏州市", "西安市"};
        String[] districts = {"朝阳区", "海淀区", "南山区", "福田区", "浦东新区", "徐汇区", "天河区", "越秀区", "西湖区", "武侯区"};
        String[] streets = {"阳光大道", "幸福路", "科技园路", "深南大道", "世纪大道", "星湖街", "月光路", "中华路", "建设路", "人民路"};
        String[] communities = {"时代花园", "水岸星城", "御景湾", "书香门第", "世纪绿洲", "罗马假日", "碧海蓝天", "城市绿洲"};

        // 3. 扩充的房屋特色描述库
        String[] descriptions = {
                "房子保养得非常好，家电家具齐全，南北通透，采光无敌！",
                "小区绿化率高，周边有大型超市和菜市场，生活非常便利。",
                "房东直租，没有中介费，随时可以看房，照片均为实景拍摄。",
                "刚刚重新装修过，全新环保材料，带大阳台，视野非常开阔。",
                "交通极度便利，出门步行5分钟就是地铁站，适合上班族。",
                "带独立卫浴，干湿分离，隔音效果好，室友作息规律。",
                "全屋智能家居，指纹锁，密码门，安全有保障，非常省心。",
                "附近有重点中小学和医院，配套资源极为丰富，家庭居住首选。"
        };

        // 4. 扩充的20张高清无版权室内实景大图
        String[] onlineImages = {
                "https://images.unsplash.com/photo-1502672260266-1c1de2d9204b?w=800&q=80",
                "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=800&q=80",
                "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?w=800&q=80",
                "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=800&q=80",
                "https://images.unsplash.com/photo-1484154218962-a197022b5858?w=800&q=80",
                "https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=800&q=80",
                "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=800&q=80",
                "https://images.unsplash.com/photo-1505691938895-1758d7feb511?w=800&q=80",
                "https://images.unsplash.com/photo-1513694203232-719a280e022f?w=800&q=80",
                "https://images.unsplash.com/photo-1554995207-c18c203602cb?w=800&q=80",
                "https://images.unsplash.com/photo-1583847268964-b28dc8f51f92?w=800&q=80",
                "https://images.unsplash.com/photo-1598928506311-c55d43f12711?w=800&q=80",
                "https://images.unsplash.com/photo-1524758631624-e2822e304c36?w=800&q=80",
                "https://images.unsplash.com/photo-1560185127-6ed189bf02f4?w=800&q=80",
                "https://images.unsplash.com/photo-1502005097973-f542523d15a6?w=800&q=80",
                "https://images.unsplash.com/photo-1494526585095-c41746248156?w=800&q=80",
                "https://images.unsplash.com/photo-1556020685-e631933f1fc7?w=800&q=80",
                "https://images.unsplash.com/photo-1512918728675-ed5a9ecdebfd?w=800&q=80",
                "https://images.unsplash.com/photo-1588854337221-4cf9fa965084?w=800&q=80",
                "https://images.unsplash.com/photo-1484154218962-a197022b5858?w=800&q=80"
        };

        // 【关键】这里的 500 代表生成 500 套房源。想要更多可以直接改成 1000 或 2000
        int generateCount = 500;

        for (int i = 1; i <= generateCount; i++) {
            Goods goods = new Goods();

            // 随机组合极具真实感的标题
            String title = titles[random.nextInt(titles.length)] + " - " + communities[random.nextInt(communities.length)];
            goods.setNames(title);

            // 随机生成合理的租金 (1500元 - 8500元)
            double price = 1500 + random.nextInt(7000);
            goods.setPrice(price);
            goods.setNormalprice(price + random.nextInt(800) + 200); // 划线价

            // 随机拼凑超真实的地址
            String fullAddress = cities[random.nextInt(cities.length)] + districts[random.nextInt(districts.length)] +
                    streets[random.nextInt(streets.length)] + communities[random.nextInt(communities.length)] +
                    (random.nextInt(50) + 1) + "栋" + (random.nextInt(30) + 1) + "0" + (random.nextInt(4) + 1) + "室";
            goods.setAddress(fullAddress);

            // 随机挑选一张作为列表【封面图】
            String coverPhotoPath = onlineImages[random.nextInt(onlineImages.length)];
            goods.setPhoto(coverPhotoPath);

            // 随机生成 3 到 6 张详情页内部的高清实景图
            StringBuilder extraImagesHtml = new StringBuilder();
            int imageCount = 3 + random.nextInt(4);
            for (int j = 0; j < imageCount; j++) {
                String innerPicPath = onlineImages[random.nextInt(onlineImages.length)];
                extraImagesHtml.append("<img src='").append(innerPicPath).append("' style='width:100%; max-width:800px; margin-top:20px; border-radius:8px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); display:block;'/>");
            }

            goods.setCreatetime(sdf.format(new Date()));
            goods.setCategory_id(random.nextInt(4) + 1);
            goods.setBussiness_id(1);
            goods.setState(1);
            goods.setIshot(random.nextInt(10) > 6 ? 1 : 0); // 大约30%的概率是热门房源
            goods.setSeenums(random.nextInt(5000) + 100);   // 浏览量更真实
            goods.setCollectnums(random.nextInt(500) + 10); // 收藏量更真实
            goods.setSalenums(random.nextInt(50));          // 模拟成交/带看次数

            // 组装终极高颜值富文本内容
            String randomDesc = descriptions[random.nextInt(descriptions.length)];
            goods.setContent(
                    "<div style='padding:15px; font-family: sans-serif;'>" +
                            "<h1 style='color:#2c3e50; border-bottom: 2px solid #eee; padding-bottom: 10px;'>" + title + "</h1>" +
                            "<div style='display:flex; margin-top:15px; gap:15px;'>" +
                            "  <span style='background:#e8f4fd; color:#2980b9; padding:4px 10px; border-radius:4px; font-size:14px;'>随时看房</span>" +
                            "  <span style='background:#e8f4fd; color:#2980b9; padding:4px 10px; border-radius:4px; font-size:14px;'>精装全配</span>" +
                            "  <span style='background:#fdf0e8; color:#c0392b; padding:4px 10px; border-radius:4px; font-size:14px;'>无中介费</span>" +
                            "</div>" +
                            "<p style='font-size:16px; color:#555; line-height: 1.8; margin-top:20px;'>" + randomDesc + " 欢迎随时联系看房，联系人：王管家。</p>" +
                            "<div style='background-color:#fff9f9; border-left: 4px solid #e74c3c; padding: 15px; margin: 20px 0;'>" +
                            "<p style='color:#e74c3c; font-size:22px; font-weight:bold; margin: 0;'>🔥 特惠租金：" + price + " 元/月</p>" +
                            "<p style='color:#999; font-size:14px; margin: 5px 0 0 0;'>详细地址：" + fullAddress + "</p>" +
                            "</div>" +
                            "<h3 style='color:#333; margin-top:30px;'>📸 室内实景图展示：</h3>" +
                            extraImagesHtml.toString() +
                            "</div>"
            );

            list.add(goods);

            // 每 100 条保存一次，防止内存溢出
            if (list.size() == 100) {
                goodsService.saveBatch(list);
                list.clear();
            }
        }
        if (!list.isEmpty()) {
            goodsService.saveBatch(list);
        }
        System.out.println("====== 恭喜你！" + generateCount + "套【海量真实房源】数据生成成功！ ======");
    }

    // ====================================================
    // ⬇️ 原有的代码生成器（原封不动保留，不要运行它） ⬇️
    // ====================================================
    @Test
    public void genCode() {
        String projectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/springboot_study?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("admin")
                            .disableOpenDir()
                            .outputDir(projectPath + "/src/main/java");
                })
                .packageConfig(builder -> {
                    builder.parent("com.study")
                            .entity("entity")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.entityBuilder()
                            .enableLombok()
                            .idType(IdType.AUTO)
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .controllerBuilder()
                            .enableRestStyle();
                })
                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }
}