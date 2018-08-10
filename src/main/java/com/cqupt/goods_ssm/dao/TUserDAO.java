package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.TUser;
import com.cqupt.goods_ssm.domain.TUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserDAO {
    int countByExample(TUserExample example);

    int deleteByExample(TUserExample example);

    int deleteByPrimaryKey(String uid);

    int insert(TUser record);

    int insertSelective(TUser record);

    List<TUser> selectByExample(TUserExample example);

    TUser selectByPrimaryKey(String uid);

    int updateByExampleSelective(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByExample(@Param("record") TUser record, @Param("example") TUserExample example);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}