
package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class ComplaintController {

    @Autowired
    private Orders_itemService Orders_itemService;
    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ComplaintService ComplaintService;
    @Autowired
    private com.study.service.UserService UserService;
    @Autowired
    private com.study.service.BussinessService BussinessService;

    //删除Complaint
    @RequestMapping("/deleteComplaint")
    @ResponseBody
    public String deleteComplaint(Integer id){
        String message = "no";
        ComplaintService.removeById(id);
        message = "yes";
        return message;
    }
    //访问添加页面
    @RequestMapping("toAddComplaint")
    public String toAddComplaint(Model model){
        List<Bussiness> bussinesslist = BussinessService.list();
        model.addAttribute("bussinesslist",bussinesslist);
        return "complaint/add";
    }

    //访问修改页面
    @RequestMapping("toUpdateComplaint")
    public String toUpdateComplaint(Integer id, Model model){
        Complaint data =ComplaintService.getById(id);
        model.addAttribute("data",data);
        return "complaint/update";
    }
    @RequestMapping(value = "/updateComplaint", method = RequestMethod.POST)
    public String updateComplaint(HttpServletRequest request,Complaint data) throws UnsupportedEncodingException {
        ComplaintService.updateById(data);
        return "redirect:/ComplaintList";
    }
    @RequestMapping(value = "/addComplaint", method = RequestMethod.POST)
    public String addComplaint(HttpSession httpSession, Complaint data,Model model) throws UnsupportedEncodingException {
        User User = (User)httpSession.getAttribute("user");
            data.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
            data.setResult("待处理");
            data.setUser_id(User.getId());
            ComplaintService.save(data);
            return "redirect:/ComplaintList";
    }



    //查询
    @RequestMapping("/ComplaintList")
    public String ComplaintList( HttpSession httpSession,Model model){
        QueryWrapper<Complaint> queryWrapper = new QueryWrapper<Complaint>();
        User User = (User)httpSession.getAttribute("user");
        Bussiness Bussiness = (Bussiness)httpSession.getAttribute("bussiness");
        if(Bussiness!=null){
            queryWrapper.eq("bussiness_id",Bussiness.getId());
        }
        if(User!=null){
            queryWrapper.eq("user_id",User.getId());
        }
        List<Complaint> list = ComplaintService.list(queryWrapper);
        for(Complaint data:list){
            data.setUser(UserService.getById(data.getUser_id()));
            data.setBussiness(BussinessService.getById(data.getBussiness_id()));
        }
       model.addAttribute("list",list);
       return "complaint/list";
    }





}

