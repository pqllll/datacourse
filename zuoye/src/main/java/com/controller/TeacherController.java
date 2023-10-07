package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.R;
import com.entity.Confirm;
import com.service.ConfirmService;
import com.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    TeacherService teacherService;

    @Autowired
    ConfirmService confirmService;

    @Autowired
    RedisTemplate redisTemplate;

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
    @PostMapping("/update")
    public  R<String> update( @RequestBody HashMap<String, String> map){

        String id=map.get("id");
        int length=id.length();
        String newid=id.substring(1,length-1);
        long ids=Long.parseLong(newid);
        LambdaQueryWrapper<Confirm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Confirm::getId,ids);
        Confirm confirm=confirmService.getOne(queryWrapper);
        confirm.setStatus("已通过");
        confirmService.updateById(confirm);


        return R.success("成功修改");
    }
}
