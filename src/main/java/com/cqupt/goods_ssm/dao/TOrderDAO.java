package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.TOrder;
import com.cqupt.goods_ssm.domain.TOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TOrderDAO {
    int countByExample(TOrderExample example);

    int deleteByExample(TOrderExample example);

    int deleteByPrimaryKey(String oid);

    int insert(TOrder record);

    int insertSelective(TOrder record);

    List<TOrder> selectByExample(TOrderExample example);

    TOrder selectByPrimaryKey(String oid);

    int updateByExampleSelective(@Param("record") TOrder record, @Param("example") TOrderExample example);

    int updateByExample(@Param("record") TOrder record, @Param("example") TOrderExample example);

    int updateByPrimaryKeySelective(TOrder record);

    int updateByPrimaryKey(TOrder record);
}