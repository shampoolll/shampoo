package com.study.entity;


import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("complaint")
public class Complaint {
    @TableId(type = IdType.AUTO)   //用于说明谁是主键
    private Integer id;
    private Integer bussiness_id;
    private String detail;
    private String result;

    private Integer user_id;
    private String create_time;
    @TableLogic
    private Integer is_deleted;
    @TableField(exist = false)//非数据库的字段
    User user;
    @TableField(exist = false)//非数据库的字段
    Bussiness bussiness;
    private String title;



}