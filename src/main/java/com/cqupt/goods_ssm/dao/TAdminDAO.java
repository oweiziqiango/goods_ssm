package com.cqupt.goods_ssm.dao;

import com.cqupt.goods_ssm.domain.TAdmin;
import com.cqupt.goods_ssm.domain.TAdminExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAdminDAO {
    int countByExample(TAdminExample example);

    int deleteByExample(TAdminExample example);

    int deleteByPrimaryKey(String adminid);

    int insert(TAdmin record);

    int insertSelective(TAdmin record);

    List<TAdmin> selectByExample(TAdminExample example);

    TAdmin selectByPrimaryKey(String adminid);

    int updateByExampleSelective(@Param("record") TAdmin record, @Param("example") TAdminExample example);

    int updateByExample(@Param("record") TAdmin record, @Param("example") TAdminExample example);

    int updateByPrimaryKeySelective(TAdmin record);

    int updateByPrimaryKey(TAdmin record);
}