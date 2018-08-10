package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.TCartitem;
import com.cqupt.goods_ssm.domain.TCartitemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCartitemDAO {
    int countByExample(TCartitemExample example);

    int deleteByExample(TCartitemExample example);

    int deleteByPrimaryKey(String cartitemid);

    int insert(TCartitem record);

    int insertSelective(TCartitem record);

    List<TCartitem> selectByExample(TCartitemExample example);

    TCartitem selectByPrimaryKey(String cartitemid);

    int updateByExampleSelective(@Param("record") TCartitem record, @Param("example") TCartitemExample example);

    int updateByExample(@Param("record") TCartitem record, @Param("example") TCartitemExample example);

    int updateByPrimaryKeySelective(TCartitem record);

    int updateByPrimaryKey(TCartitem record);
}