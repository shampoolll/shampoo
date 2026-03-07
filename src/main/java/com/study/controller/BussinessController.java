package com.study.controller;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.study.entity.Admin;
import com.study.entity.Bussiness;
import com.study.entity.Bussiness;
import com.study.entity.Goods;
import com.study.service.BussinessService;
import com.study.service.BussinessService;
import com.study.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class BussinessController {


    @Autowired
    private BussinessService BussinessService;

    //删除用户
    @RequestMapping("/deleteBussiness")
    @ResponseBody
    public String deleteBussiness(Integer id, Model model, HttpSession httpSession){
        String message = "no";
        BussinessService.removeById(id);
        message = "yes";
        return message;
    }
    @RequestMapping("/banBussiness")
    @ResponseBody
    public String banBussiness(Bussiness data, Model model, HttpSession httpSession){
        String message = "no";
        BussinessService.updateById(data);
        message = "yes";
        return message;
    }
    //新增用户页面
    @RequestMapping("/toAddBussiness")
    public String toAddBussinesss( HttpServletRequest request){
        return "bussiness/add";
    }

    @RequestMapping(value = "/addBussiness", method = RequestMethod.POST)
    public String addBussiness(Bussiness data, HttpSession httpSession, Model model) throws UnsupportedEncodingException {
        data.setCtime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        Admin Admin = (Admin)httpSession.getAttribute("admin");//当前登录人
        QueryWrapper<Bussiness> queryWrapper = new  QueryWrapper<>();
        queryWrapper.eq("name",data.getName());
        Bussiness bussinessinfo = BussinessService.getOne(queryWrapper);
            if(bussinessinfo == null){
                if(Admin!=null){
                    data.setState(1);
                }
                BussinessService.save(data);
                if(Admin==null){
                    return("redirect:/outLogin");
                }else {
                    return("redirect:/BussinessList");
                }
            }else{
                if(Admin==null){

                    model.addAttribute("statusbussiness", "用户名重复！");
                    return("redirect:/outLogin");
                }else {
                    model.addAttribute("status", "用户名重复！");
                    return("bussiness/add");
                }

            }
    }



    @RequestMapping(value = "/updateBussiness", method = RequestMethod.POST)
    public String updateBussiness(HttpSession httpSession, Model model, Bussiness data) throws UnsupportedEncodingException {
        Bussiness Bussiness = (Bussiness)httpSession.getAttribute("bussiness");//当前登录人
        Admin Admin = (Admin)httpSession.getAttribute("admin");
        if(Bussiness!=null){
            QueryWrapper<Bussiness> queryWrapper = new  QueryWrapper<>();
            queryWrapper.eq("name",data.getName());
            Bussiness bussinessinfo = BussinessService.getOne(queryWrapper);
            if(!data.getName().equals(Bussiness.getName())){
                if(bussinessinfo == null){
                    BussinessService.updateById(data);
                    httpSession.setAttribute("realname", BussinessService.getById(data.getId()).getRealname());
                    httpSession.setAttribute("bussiness", BussinessService.getById(data.getId()));
                    return("redirect:/BussinessList");
                }else{
                    model.addAttribute("bussinesstype",2);
                    model.addAttribute("status", "用户名重复！");
                    model.addAttribute("data", BussinessService.getById(data.getId()));
                    return("bussiness/update");
                }
            }//账号不变的情况下，直接做修改
            else{
                BussinessService.updateById(data);
                httpSession.setAttribute("realname", BussinessService.getById(data.getId()).getRealname());
                httpSession.setAttribute("bussiness", BussinessService.getById(data.getId()));
                return("redirect:/BussinessList");
            }
        }
        if(Admin!=null){
            BussinessService.updateById(data);

            return("redirect:/BussinessList");
        }else {
            BussinessService.updateById(data);
            return("redirect:/BussinessList");
        }
    }

    //修改页面
    @RequestMapping("/toUpdateBussiness")
    public String toUpdateBussiness(Integer id, Model model){
        Bussiness data =BussinessService.getById(id);
        model.addAttribute("data",data);
        return "bussiness/update";
    }


    //查询用户
    @RequestMapping("/BussinessList")
    public String BussinessList(Model model,HttpSession httpSession, HttpServletRequest request){
        Bussiness Bussiness = (Bussiness)httpSession.getAttribute("bussiness");
        QueryWrapper<Bussiness> queryWrapper = new QueryWrapper<Bussiness>();
        if(Bussiness!=null){
            queryWrapper.eq("id",Bussiness.getId());
        }
        List<Bussiness> list = BussinessService.list(queryWrapper);
        model.addAttribute("list",list);
        return "bussiness/list";
    }

}
