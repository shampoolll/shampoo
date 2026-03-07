package com.study.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.study.entity.Provinces;
import com.study.entity.Statics;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface EchartsMapper extends BaseMapper<Provinces> {



    @Select(" select count(*) as counts ,goods.category_id,category.name as names from goods,category where goods.category_id=category.id GROUP  BY category.name")
    List<Statics> BrandEcharts();

    @Select(" SELECT \n" +
            "count(*) as counts,p.province_Name as names\n" +
            "FROM orders_item oi, goods o,provinces p\n" +
            "where oi.goods_id=o.id\n" +
            "and p.province_Id = o.province_Id\n" +
            "GROUP BY names")
    List<Statics> mapsEcharts();


    @Select(" select avg(goods.normalprice) as counts ,category.name as names from goods,category where goods.category_id=category.id GROUP BY category.name")
    List<Statics> Normal_priceEcharts();

    @Select(" select avg(goods.shop_price) as counts ,goods.brand_name as  names from goods GROUP BY names")
    List<Statics> Shop_priceEcharts();
}
