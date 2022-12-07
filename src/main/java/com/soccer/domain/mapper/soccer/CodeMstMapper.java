package com.soccer.domain.mapper.soccer;

import com.soccer.domain.soccer.CodeMst;
import org.apache.ibatis.annotations.Param;

public interface CodeMstMapper {
    int deleteByPrimaryKey(@Param("groupId") String groupId, @Param("codeId") String codeId);

    int insert(CodeMst record);

    int insertSelective(CodeMst record);

    CodeMst selectByPrimaryKey(@Param("groupId") String groupId, @Param("codeId") String codeId);

    int updateByPrimaryKeySelective(CodeMst record);

    int updateByPrimaryKey(CodeMst record);
}