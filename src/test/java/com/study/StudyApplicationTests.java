package com.study;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class StudyApplicationTests {

    @Test
    public void genCode() {
        // 获取当前系统用户所创建的当前项目的路径
        String projectPath = System.getProperty("user.dir");

        // 1、创建代码生成器对象并配置数据源
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/springboot_study?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8", "root", "123456")

                // 2、全局配置
                .globalConfig(builder -> {
                    builder.author("admin") // 设置作者
                            .disableOpenDir() // 生成后不自动打开文件夹
                            .outputDir(projectPath + "/src/main/java"); // 指定输出目录
                })

                // 3、包配置
                .packageConfig(builder -> {
                    builder.parent("com.study") // 设置父包名
                            .entity("entity") // 设置实体类所在的包名
                            // 配置 mapper xml 文件的输出路径
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mapper"));
                })

                // 4、策略配置
                .strategyConfig(builder -> {
                    // 实体类配置
                    builder.entityBuilder()
                            .enableLombok() // 开启 lombok 模型
                            .idType(IdType.AUTO) // 设置主键为自增策略

                            // Service 层配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 去掉 Service 接口的首字母 I

                            // Controller 层配置
                            .controllerBuilder()
                            .enableRestStyle(); // 开启 @RestController 风格 (返回 json 格式)
                })

                // 5、模板引擎配置，默认使用 Velocity
                .templateEngine(new VelocityTemplateEngine())

                // 6、执行生成
                .execute();
    }
}