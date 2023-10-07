package com.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.common.R;
import com.entity.Student;
import com.entity.User;
import com.service.StudentService;
import com.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping
    public R<Student> list() {

        String key = "username";
        String username = String.valueOf(redisTemplate.opsForValue().get(key));

        //根据username查找id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user = userService.getOne(queryWrapper);
        Long id = user.getId();

        //根据id获得student
        LambdaQueryWrapper<Student> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Student::getId, id);
        Student student = studentService.getOne(queryWrapper1);

        return R.success(student);

    }

    @PutMapping("/update")
    public R<String> update(@RequestBody User user) {
        String key = "username";
        String username = String.valueOf(redisTemplate.opsForValue().get(key));

        //根据username查找id
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        User user2 = userService.getOne(queryWrapper);
        Long id = user2.getId();
        user.setId(id);
        user.setUsername(user2.getUsername());


        userService.updateById(user);


        return R.success("修改成功");

    }
}
