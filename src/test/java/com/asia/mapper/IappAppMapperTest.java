package com.asia.mapper;

import com.asia.entity.IapApp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by renshunyu on 2018/5/26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IappAppMapperTest {
    @Autowired
    private IappAppMapper IappAppMapper;
    @Test
    public void getAll() throws Exception {
        List<IapApp> users = IappAppMapper.getAll();
        System.out.println(users.toString());
    }

}