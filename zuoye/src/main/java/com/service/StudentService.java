package com.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.entity.Student;

public interface StudentService extends IService<Student> {

    public  void listWithusername();
}
