
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;

import com.study.entity.Provinces;
import com.study.entity.Shopcart;
import com.study.entity.User;

import com.study.service.ShopcartService;
import com.study.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
public class ShopcartController {
    @Autowired
    private ProvincesMapper ProvincesMapper;

    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ShopcartService ShopcartService;
    @Autowired
    private com.study.service.UserService UserService;

    //删除Shopcart
    @RequestMapping("/deleteShopcart")
    @ResponseBody
    public String deleteShopcart(Integer id){
        String message = "no";
        ShopcartService.removeById(id);
        message = "yes";
        return message;
    }

    //添加
    @RequestMapping(value = "/addShopcart")
    @ResponseBody
    public String addShopcart( HttpSession httpSession,Shopcart data) {
        User User = (User)httpSession.getAttribute("user");
        String message = "no";
        if(User!=null){
            QueryWrapper<Shopcart> queryWrapper=new QueryWrapper<Shopcart>();
            queryWrapper.eq("user_id",User.getId());
            queryWrapper.eq("goods_id",data.getGoods_id());
            Shopcart shopcartIfRepeat=ShopcartService.getOne(queryWrapper);
            if(shopcartIfRepeat==null){
                data.setUser_id(User.getId());
                ShopcartService.save(data);
                message = "yes";
                return message;
            }else {//代表已经加入了这个，这个时候只需要修改数量

                message = "repeat";
                return message;
            }
        }else {
            return message;
        }

    }
    @RequestMapping("preOrder")
    public String preOrder(RedirectAttributes attributes, Integer[] goodslist, Model model, HttpServletRequest request, HttpSession httpSession){
        List<Shopcart> cartList=new ArrayList<Shopcart>();
        for (Integer i : goodslist) {
            Shopcart cart = ShopcartService.getById(i);
            cartList.add(cart);
        }
        Double total=0.0;
        Integer state=0;
        for(Shopcart data:cartList){
            data.setGoods(GoodsService.getById(data.getGoods_id()));
            total+=(data.getNums()*data.getGoods().getPrice());
            if(GoodsService.getById(data.getGoods_id()).getState()==1){
                state=1;
            }
        }
        User User = (User)httpSession.getAttribute("user");
        List<Provinces> list = ProvincesMapper.findAllProvince();
        model.addAttribute("list",list);
        model.addAttribute("cartList", cartList);
        model.addAttribute("total", total);
        if(state==1){
            attributes.addFlashAttribute("status", "选中的房屋已被租赁，请重新选择！");
            return "redirect:/ShopcartList";
        }else {
            return "index/confirm_order";
        }
    }
    @RequestMapping("/findCartByUser")
    @ResponseBody
    //Ajax请求的数据
    public List<Shopcart> findCartByUser(HttpSession httpSession,HttpServletRequest request){
        QueryWrapper<Shopcart> queryWrapper = new QueryWrapper<Shopcart>();
        User User = (User)httpSession.getAttribute("user");
        if(User!=null){
            queryWrapper.eq("user_id",User.getId());
        }
        List<Shopcart> list = ShopcartService.list(queryWrapper);

        // 使用迭代器代替普通的for循环，方便在遇到脏数据时安全移除
        java.util.Iterator<Shopcart> iterator = list.iterator();
        while(iterator.hasNext()){
            Shopcart data = iterator.next();
            com.study.entity.Goods goods = GoodsService.getById(data.getGoods_id());

            // 【核心修复】增加判空拦截！如果查不到这个商品（被物理删除或下架了），就直接跳过并移出列表
            if(goods == null){
                iterator.remove();
                continue;
            }

            // 只有商品存在时，才进行赋值和状态判断
            data.setGoods(goods);
            if(goods.getState() == 0){
                data.setStatusstring("未租赁");
            }
            if(goods.getState() == 1){
                data.setStatusstring("租赁中");
            }
        }
        return list;
    }
    //查询
    @RequestMapping("/ShopcartList")
    public String ShopcartList( HttpSession httpSession,Model model){
        QueryWrapper<Shopcart> queryWrapper = new QueryWrapper<Shopcart>();
        User User = (User)httpSession.getAttribute("user");
        if(User!=null){
            queryWrapper.eq("user_id",User.getId());
        }
        List<Shopcart> list = ShopcartService.list(queryWrapper);
        Double totalmoney=0.0;
       model.addAttribute("list",list);
       return "index/shopcart";
    }





}

