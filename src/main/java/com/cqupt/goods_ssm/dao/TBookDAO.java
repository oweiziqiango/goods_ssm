package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.TBook;
import com.cqupt.goods_ssm.domain.TBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TBookDAO {
    int countByExample(TBookExample example);

    int deleteByExample(TBookExample example);

    int deleteByPrimaryKey(String bid);

    int insert(TBook record);

    int insertSelective(TBook record);

    List<TBook> selectByExample(TBookExample example);

    TBook selectByPrimaryKey(String bid);

    int updateByExampleSelective(@Param("record") TBook record, @Param("example") TBookExample example);

    int updateByExample(@Param("record") TBook record, @Param("example") TBookExample example);

    int updateByPrimaryKeySelective(TBook record);

    int updateByPrimaryKey(TBook record);
}