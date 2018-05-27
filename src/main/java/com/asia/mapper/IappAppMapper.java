package com.asia.mapper;

import com.asia.entity.IapApp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.annotation.Resources;
import java.util.List;

/**
 * Created by renshunyu on 2018/5/26.
 */
@Mapper
@Repository
public interface IappAppMapper {
    @Select("SELECT * FROM iap_app")
    List<IapApp> getAll();
}
