package com.study.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("Bussiness")
public class Bussiness {
    @TableId(type = IdType.AUTO)//设置主键自动递增
    private Integer id;
    private String name;
    private String photo;
    private String banner;
    private String email;
    private String detail;
    private String phone;
    private String ctime;
    private String pwd;
    private String realname;
    @TableLogic
    private Integer is_deleted;
    @TableField(exist = false)//非数据库的字段
    Integer nums;//店内房屋数
    private Integer state;
}
