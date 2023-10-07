package com.entity;

import lombok.Data;

@Data
public class Confirm {
    private Long id;

    private  String name;

   private String context;

   private String status;
   //提交人的名字
   private String upname;
}
