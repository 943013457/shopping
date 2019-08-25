package com.mapper;

import com.pojo.Trolley;
import com.pojo.example.TrolleyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TrolleyMapper {
    long countByExample(TrolleyExample example);

    int deleteByExample(TrolleyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Trolley record);

    int insertSelective(Trolley record);

    List<Trolley> selectByExample(TrolleyExample example);

    Trolley selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Trolley record, @Param("example") TrolleyExample example);

    int updateByExample(@Param("record") Trolley record, @Param("example") TrolleyExample example);

    int updateByPrimaryKeySelective(Trolley record);

    int updateByPrimaryKey(Trolley record);
}