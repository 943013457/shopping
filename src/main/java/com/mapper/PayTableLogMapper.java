package com.mapper;

import com.pojo.PayTableLog;
import com.pojo.PayTableLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PayTableLogMapper {
    long countByExample(PayTableLogExample example);

    int deleteByExample(PayTableLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PayTableLog record);

    int insertSelective(PayTableLog record);

    List<PayTableLog> selectByExample(PayTableLogExample example);

    PayTableLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PayTableLog record, @Param("example") PayTableLogExample example);

    int updateByExample(@Param("record") PayTableLog record, @Param("example") PayTableLogExample example);

    int updateByPrimaryKeySelective(PayTableLog record);

    int updateByPrimaryKey(PayTableLog record);

    List<PayTableLog> selectByPayInfo(Integer time);

    int countByUnFinish();

    int countByFinish();
}