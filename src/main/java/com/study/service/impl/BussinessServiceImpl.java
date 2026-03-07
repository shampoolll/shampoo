package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.BussinessMapper;
import com.study.entity.Bussiness;
import com.study.service.BussinessService;
import org.springframework.stereotype.Service;


@Service
public class BussinessServiceImpl extends ServiceImpl<BussinessMapper, Bussiness> implements BussinessService {
}
