package com.asia.controller;

import com.asia.Result.ProOperateNum;
import com.asia.entity.IapApp;
import com.asia.mapper.IappAppMapper;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by renshunyu on 2018/5/26.
 */

@RestController
@RequestMapping("/auditpd")
public class ProductionDataController {

    Logger log = Logger.getLogger(ProductionDataController.class);
    @Autowired
    private IappAppMapper iappAppMapper;

    @RequestMapping("/pon")
    public ProOperateNum[] pon(){
        ProOperateNum p1 = new ProOperateNum("陕西",100000);
        ProOperateNum p2 = new ProOperateNum("上海",50000);
        ProOperateNum[] p = {p1,p2};
        return p;
    }

    @RequestMapping("/value")
    public Integer[] value(){
        Integer[] v = {1,2};
        return v;
    }
    @RequestMapping("/allapp")
    public List<IapApp> allapp(){
        List<IapApp> ia = iappAppMapper.getAll();
        return ia;
    }


}
