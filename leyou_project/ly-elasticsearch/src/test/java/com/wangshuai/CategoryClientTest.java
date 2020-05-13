package com.wangshuai;

import com.wangshuai.bean.Category;
import com.wangshuai.client.CategoryClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryClientTest {
    @Autowired
    private CategoryClient client;

    @Test
    public void test1() {
        List<Category> categories = client.findByCids(Arrays.asList(1L, 2L, 3L));
        for (Category c : categories) {
            System.out.println(c);
        }
    }


}
