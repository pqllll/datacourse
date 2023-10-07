package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.R;
import com.entity.User;
import com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public R<String> login(@RequestBody User user, HttpServletRequest request) {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, user.getUsername());
        queryWrapper.eq(User::getPassword,user.getPassword());
        User user1 = userService.getOne(queryWrapper);
        if (user1 == null) {
            return R.error("没有该用户");
        }

        String key="username";
        redisTemplate.opsForValue().set(key,user1.getUsername(),60, TimeUnit.MINUTES);
        //log.info(String.valueOf(redisTemplate.opsForValue().get(key)));

        log.info("登录ing");
        return R.success("成功登录");
    }

}
