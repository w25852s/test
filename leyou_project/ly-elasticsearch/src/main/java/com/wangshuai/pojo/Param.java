package com.wangshuai.pojo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Param {
      private  String k;

      private String unit;

      private  Boolean searchable;

      private  Boolean global;

      private Boolean numerical;

      private  Object v;
      private List<String> options;

      /*public static void main(String[] args) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            List<Param> params = new ArrayList<>();
          *//*  Param param = new Param("品牌",false,true,true,"黑马", Arrays.asList("蓝色","黄色","绿色"));
            Param param1 = new Param("品牌",false,true,true,"黑马", Arrays.asList("蓝色","黄色","绿色"));
            Param param2= new Param("品牌",false,true,true,"黑马", Arrays.asList("蓝色","黄色","绿色"));*//*
            GroupParam groupParam = new GroupParam();
            params.add(param);
            params.add(param1);
            params.add(param2);
            groupParam.setParams(params);
            groupParam.setGroup("group");
            String s = mapper.writeValueAsString(groupParam);
            System.out.println(s);



      }*/

}
