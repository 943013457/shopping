package com.mapper;

import com.pojo.OrderTable;
import com.pojo.example.OrderTableExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface OrderTableMapper {
    long countByExample(OrderTableExample example);

    int deleteByExample(OrderTableExample example);

    int deleteByPrimaryKey(String id);

    int insert(OrderTable record);

    int insertSelective(OrderTable record);

    List<OrderTable> selectByExample(OrderTableExample example);

    OrderTable selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") OrderTable record, @Param("example") OrderTableExample example);

    int updateByExample(@Param("record") OrderTable record, @Param("example") OrderTableExample example);

    int updateByPrimaryKeySelective(OrderTable record);

    int updateByPrimaryKey(OrderTable record);
}