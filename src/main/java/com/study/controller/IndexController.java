package com.study.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.study.dao.ProvincesMapper;
import com.study.entity.*;
import com.study.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
public class IndexController {

    @Autowired
    private CommentsService CommentsService;
    @Autowired
    private GoodsService GoodsService;
    @Autowired
    private ProvincesMapper ProvincesMapper;
    @Autowired
    private UserService UserService;
    @Autowired
    private BussinessService BussinessService;
    @Autowired
    private CategoryService CategoryService;
    @Autowired
    private BannerService BannerService;
    @Autowired
    private BoardService BoardService;
    //主页
    @RequestMapping("/toIndex")
    public String toIndex(Model model, String key,
                          @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false,defaultValue = "8") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();
        List<Goods> goodslist = GoodsService.list(queryWrapper);//全部的房屋
        for(Goods goods:goodslist){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        QueryWrapper<Goods> queryWrapperSalenums = new QueryWrapper<Goods>();
        queryWrapperSalenums.orderByDesc("salenums");//租赁次数榜的排名
        List<Goods> goodslistSalenums = GoodsService.list(queryWrapperSalenums);//全部的房屋
        for(Goods goods:goodslistSalenums){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        QueryWrapper<Goods> queryWrapperCollectnums = new QueryWrapper<Goods>();
        queryWrapperCollectnums.orderByDesc("collectnums");//收藏榜的排名
        List<Goods> goodslistcollectnums = GoodsService.list(queryWrapperCollectnums);//全部的房屋
        for(Goods goods:goodslistcollectnums){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslistcollectnums",goodslistcollectnums.size()>3?goodslistcollectnums.subList(0,3):goodslistcollectnums);

        QueryWrapper<Goods> queryWrapperHot = new QueryWrapper<Goods>();
        queryWrapperHot.eq("ishot",1);
        List<Goods> goodslisthot = GoodsService.list(queryWrapperHot);//热门的房屋
        for(Goods goods:goodslisthot){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslisthot",goodslisthot.size()>3?goodslisthot.subList(0,3):goodslisthot);

        QueryWrapper<Goods> queryWrapperNew = new QueryWrapper<Goods>();
        queryWrapperNew.orderByDesc("createtime");
        List<Goods> goodslistnew = GoodsService.list(queryWrapperNew);//最新的房屋
        for(Goods goods:goodslistnew){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslistnew",goodslistnew.size()>3?goodslistnew.subList(0,3):goodslistnew);

        QueryWrapper<Goods> queryWrapperSee = new QueryWrapper<Goods>();
        queryWrapperSee.orderByDesc("seenums");
        List<Goods> goodslistsee = GoodsService.list(queryWrapperSee);//热门的房屋
        for(Goods goods:goodslistnew){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        model.addAttribute("goodslistsee",goodslistsee.size()>3?goodslistsee.subList(0,3):goodslistsee);

        List<Category> categorylist = CategoryService.list();//全部的房屋类型
        List<Banner> bannerlist = BannerService.list();//全部的轮播
        List<Board> boardlist = BoardService.list();//全部的公告
        model.addAttribute("bannerlist",bannerlist);
        model.addAttribute("categorylist",categorylist);
        model.addAttribute("boardlist",boardlist.size()>4?boardlist.subList(0,4):boardlist);
        model.addAttribute("goodslist",goodslist);
        model.addAttribute("goodslistSalenums",goodslistSalenums.size()>10?goodslistSalenums.subList(0,10):goodslistSalenums);
/*
        model.addAttribute("goodslist",goodslist.subList(0,2));
*/

        return "index/index";
    }
    @RequestMapping("/toGoods")
    public String toGoods(Model model, String names,String pricehigh,String pricelow,String collect,
                          String ishot,String category_id,String bussiness_id,
                          @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();
        queryWrapper.like(StringUtils.hasText(names),"names",names);
        queryWrapper.orderByDesc(pricehigh!=null,"price");
        queryWrapper.orderByAsc(pricelow!=null,"price");
        queryWrapper.orderByDesc(collect!=null,"collectnums");
         queryWrapper.eq(ishot!=null,"ishot",ishot);
        queryWrapper.eq(category_id!=null,"category_id",category_id);
        queryWrapper.eq(bussiness_id!=null,"bussiness_id",bussiness_id);

        List<Goods> goodslist = GoodsService.list(queryWrapper);//全部的房屋
        for(Goods goods:goodslist){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        PageInfo pageInfo = new PageInfo(goodslist);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("url","toGoods");

       List<Category> categorylist = CategoryService.list();//全部的房屋类型
        for(Category data:categorylist){
            QueryWrapper<Goods> queryWrapperBussinessHasCategory = new QueryWrapper<Goods>();
            queryWrapperBussinessHasCategory.eq(bussiness_id!=null,"bussiness_id",bussiness_id);
            queryWrapperBussinessHasCategory.eq("category_id",data.getId());
            List<Goods> Goodslist = GoodsService.list(queryWrapperBussinessHasCategory);
            data.setNums(Goodslist.size());
        }
        model.addAttribute("categorylist",categorylist);
        return "index/goods";
    }

    @RequestMapping("/toGoods_Bussiness")//该方法区别以上是为了展示轮播
    public String toGoods_Bussiness(Model model, String names,String pricehigh,String pricelow,String collect,
                          String ishot,String category_id,String bussiness_id,
                          @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false,defaultValue = "10") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<Goods>();
        queryWrapper.like(StringUtils.hasText(names),"names",names);
        queryWrapper.orderByDesc(pricehigh!=null,"price");
        queryWrapper.orderByAsc(pricelow!=null,"price");
        queryWrapper.orderByDesc(collect!=null,"collectnums");
        queryWrapper.eq(ishot!=null,"ishot",ishot);
        queryWrapper.eq(category_id!=null,"category_id",category_id);
        queryWrapper.eq(bussiness_id!=null,"bussiness_id",bussiness_id);

        List<Goods> goodslist = GoodsService.list(queryWrapper);//全部的房屋
        for(Goods goods:goodslist){
            if(goods.getCategory_id()!=null){
                goods.setCategory(CategoryService.getById(goods.getCategory_id()));
            }
        }
        PageInfo pageInfo = new PageInfo(goodslist);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("url","toGoods");

        List<Category> categorylist = CategoryService.list();//全部的房屋类型
        for(Category data:categorylist){
            QueryWrapper<Goods> queryWrapperBussinessHasCategory = new QueryWrapper<Goods>();
            queryWrapperBussinessHasCategory.eq(bussiness_id!=null,"bussiness_id",bussiness_id);
            queryWrapperBussinessHasCategory.eq("category_id",data.getId());
            List<Goods> Goodslist = GoodsService.list(queryWrapperBussinessHasCategory);
            data.setNums(Goodslist.size());
        }
            Bussiness bussiness=BussinessService.getById(bussiness_id);
            model.addAttribute("bussiness",bussiness);
        model.addAttribute("categorylist",categorylist);
        return "index/goods_bussiness";
    }

    @RequestMapping("/toGoodsDetail")
    public String toGoodsDetail(Model model, Integer id,HttpServletRequest request){
      Goods goods=GoodsService.getById(id);
        goods.setCategory(CategoryService.getById(goods.getCategory_id()));
        goods.setProvinces(ProvincesMapper.findProvinceById(goods.getProvince_Id().toString()));
        goods.setCities(ProvincesMapper.findCityById(goods.getCity_Id().toString()));
        goods.setAreas(ProvincesMapper.findAreaById(goods.getArea_Id().toString()));
        goods.setBussiness(BussinessService.getById(goods.getBussiness_id()));
      model.addAttribute("goods",goods);
        QueryWrapper<Comments> queryWrapperComments = new QueryWrapper<Comments>();
        queryWrapperComments.eq("goods_id",goods.getId());
        List<Comments> commentsList = CommentsService.list(queryWrapperComments);//全部的房屋类型
        for(Comments data:commentsList){
            data.setUser(UserService.getById(data.getUser_id()));
            data.setGoods(GoodsService.getById(data.getGoods_id()));
        }

        model.addAttribute("commentsList",commentsList);

        return "index/goods_detail";
    }

    @RequestMapping("/toBoard")
    public String toBoard(Model model, String title,
                          @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Board> queryWrapper = new QueryWrapper<Board>();
        queryWrapper.like(StringUtils.hasText(title),"title",title);
        List<Board> boardlist = BoardService.list(queryWrapper);//全部的公告
        PageInfo pageInfo = new PageInfo(boardlist);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("url","toBoard");
        return "index/board";
    }

    //房东主页
    @RequestMapping("/toBussiness")
    public String toBussiness(Model model, String realname,
                          @RequestParam(required = false,defaultValue = "1") Integer pageNum,
                          @RequestParam(required = false,defaultValue = "5") Integer pageSize,
                          HttpSession httpSession, HttpServletRequest request){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Bussiness> queryWrapper = new QueryWrapper<Bussiness>();
        queryWrapper.like(StringUtils.hasText(realname),"realname",realname);
        List<Bussiness> bussinesslist = BussinessService.list(queryWrapper);//全部的房东
        for(Bussiness data:bussinesslist){
            QueryWrapper<Goods> queryWrapperGoods = new QueryWrapper<Goods>();
            queryWrapperGoods.eq("bussiness_id",data.getId());
            List<Goods> Goodslist = GoodsService.list(queryWrapperGoods);
            data.setNums(Goodslist.size());
        }
        PageInfo pageInfo = new PageInfo(bussinesslist);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("url","toBussiness");
        List<Category> categorylist = CategoryService.list();//全部的房屋类型
        model.addAttribute("categorylist",categorylist);
        return "index/bussiness";
    }

    @RequestMapping("/toBoard_detail")
    public String toBoard_detail(Model model, Integer id,HttpServletRequest request){
        Board board=BoardService.getById(id);
        model.addAttribute("board",board);
        return "index/board_detail";
    }





}
