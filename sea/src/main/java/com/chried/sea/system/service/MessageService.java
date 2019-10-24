package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chried.sea.system.model.entity.MessageEntity;

import java.util.List;

/**
 * 消息服务.
 *
 * @author chried
 */
public interface MessageService extends IService<MessageEntity> {

    /**
     * 根据token统计消息数量.
     * <pre>
     *     根据token找出当前用户.
     *     根据当前用户查找当前角色.
     *     根据角色查询当前消息.
     * </pre>
     *
     * @param read  是否阅读.
     * @param token token.
     * @return 未阅读消息数量.
     */
    int count(Boolean read, String token);

    /**
     * 获取用户Id获取.
     *
     * @param userId 用户id.
     * @return 消息.
     */
    List<MessageEntity> queryByUserId(Boolean read, String userId);
}
