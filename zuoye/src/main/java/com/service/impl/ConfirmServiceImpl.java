package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.entity.Confirm;
import com.mapper.ConfirmMapper;
import com.service.ConfirmService;
import org.springframework.stereotype.Service;

@Service
public class ConfirmServiceImpl extends ServiceImpl<ConfirmMapper, Confirm> implements ConfirmService {
}
