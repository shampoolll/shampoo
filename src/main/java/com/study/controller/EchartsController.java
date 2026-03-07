package com.study.controller;

import com.study.entity.Statics;
import com.study.dao.EchartsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class EchartsController {
    @Autowired
    private EchartsMapper EchartsMapper;



    //echarts1
    @RequestMapping("/toEcharts1")
    @ResponseBody
    public ModelAndView toEcharts1(ModelAndView mv){
        mv.setViewName("echarts/echarts1");
        return mv;
    }
    @RequestMapping("BrandEcharts")
    @ResponseBody
    public List<Statics> BrandEcharts(){
        return EchartsMapper.BrandEcharts();
    }
/*————————————————————————————————————————分割线————————————————————————————————————————————*/

    //echarts2
    @RequestMapping("/toEcharts2")
    @ResponseBody
    public ModelAndView toEcharts2(ModelAndView mv){
        mv.setViewName("echarts/echarts2");
        return mv;
    }
    @RequestMapping("mapsEcharts")
    @ResponseBody
    public List<Statics> mapsEcharts(){
        return EchartsMapper.mapsEcharts();
    }

    /*————————————————————————————————————————分割线————————————————————————————————————————————*/


    //echarts3
    @RequestMapping("/toEcharts3")
    @ResponseBody
    public ModelAndView Normal_priceEcharts(ModelAndView mv){
        mv.setViewName("echarts/echarts3");
        return mv;
    }
    @RequestMapping("Normal_priceEcharts")
    @ResponseBody
    public List<Statics> Normal_priceEcharts(){
        return EchartsMapper.Normal_priceEcharts();
    }
    /*————————————————————————————————————————分割线————————————————————————————————————————————*/

    //echarts4
    @RequestMapping("/toShop_priceEcharts")
    @ResponseBody
    public ModelAndView Shop_priceEcharts(ModelAndView mv){
        mv.setViewName("echarts/echarts_shop_price");
        return mv;
    }
    @RequestMapping("Shop_priceEcharts")
    @ResponseBody
    public List<Statics> Shop_priceEcharts(){
        return EchartsMapper.Shop_priceEcharts();
    }

}
