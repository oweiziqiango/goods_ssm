package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.TOrderitem;
import com.cqupt.goods_ssm.domain.TOrderitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TOrderitemDAO {
    int countByExample(TOrderitemExample example);

    int deleteByExample(TOrderitemExample example);

    int deleteByPrimaryKey(String orderitemid);

    int insert(TOrderitem record);

    int insertSelective(TOrderitem record);

    List<TOrderitem> selectByExample(TOrderitemExample example);

    TOrderitem selectByPrimaryKey(String orderitemid);

    int updateByExampleSelective(@Param("record") TOrderitem record, @Param("example") TOrderitemExample example);

    int updateByExample(@Param("record") TOrderitem record, @Param("example") TOrderitemExample example);

    int updateByPrimaryKeySelective(TOrderitem record);

    int updateByPrimaryKey(TOrderitem record);
}