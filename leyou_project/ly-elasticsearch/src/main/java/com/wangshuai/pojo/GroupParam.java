package com.wangshuai.pojo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.ognl.ObjectMethodAccessor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupParam {

    private String group;

    private List<Param> params;


    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        GroupParam groupParam = new GroupParam();


    }

}
