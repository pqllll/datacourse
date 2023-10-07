package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.R;
import com.entity.Confirm;
import com.service.ConfirmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/confirm")
public class ConfirmController {

    @Autowired
    ConfirmService confirmService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/add")
    public R<String> add(@RequestBody Confirm confirm) {
        String key = "username";
        String username = String.valueOf(redisTemplate.opsForValue().get(key));
        confirm.setUpname(username);
        //设置id,当前时间

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String id = sdf.format(System.currentTimeMillis());
        log.info(id);
        long l = Long.parseLong(id);

        confirm.setId(l);
        confirm.setStatus("正在审核");

        confirmService.save(confirm);
        return R.success("提交成功");
    }

    @GetMapping("/list")
    public R<List<Confirm>> list() {
        //通过upname=username，来获取该用户提交的表单
        String key = "username";
        String upname = String.valueOf(redisTemplate.opsForValue().get(key));

        LambdaQueryWrapper<Confirm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Confirm::getUpname, upname);
        queryWrapper.orderByDesc(Confirm::getId);

        //放入
        List<Confirm> list = confirmService.list(queryWrapper);

        return R.success(list);
    }

    @DeleteMapping("/delete")
    public  R<String> delete( @RequestBody HashMap<String, String> map){

        String id=map.get("id");
        int length=id.length();
        String newid=id.substring(1,length-1);
        long ids=Long.parseLong(newid);

        confirmService.removeById(ids);
        log.info("删除ing"+ids);

        return null;
    }



}
