package com.study.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.dao.ComplaintMapper;
import com.study.entity.Complaint;
import com.study.service.ComplaintService;
import org.springframework.stereotype.Service;


@Service
public class ComplaintServiceImpl extends ServiceImpl<ComplaintMapper, Complaint> implements ComplaintService {
}
