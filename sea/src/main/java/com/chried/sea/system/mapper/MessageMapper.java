package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.sea.system.model.entity.MessageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息mapper.
 *
 * @author chried
 */
@Repository
@Mapper
public interface MessageMapper extends BaseMapper<MessageEntity> {

    /**
     * 根据用户获取消息.
     *
     * @param read   是否阅读.为Null表示查询所有.
     * @param userId 用户id.
     * @return 消息.
     */
    List<MessageEntity> queryByUserId(@Param("read") Boolean read, @Param("userId") String userId);
}
