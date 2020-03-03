package com.mapper;

import com.pojo.PayTable;
import com.pojo.example.PayTableExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface PayTableMapper {
    long countByExample(PayTableExample example);

    int deleteByExample(PayTableExample example);

    int deleteByPrimaryKey(String orderId);

    int insert(PayTable record);

    int insertSelective(PayTable record);

    List<PayTable> selectByExample(PayTableExample example);

    PayTable selectByPrimaryKey(String orderId);

    int updateByExampleSelective(@Param("record") PayTable record, @Param("example") PayTableExample example);

    int updateByExample(@Param("record") PayTable record, @Param("example") PayTableExample example);

    int updateByPrimaryKeySelective(PayTable record);

    int updateByPrimaryKey(PayTable record);
}