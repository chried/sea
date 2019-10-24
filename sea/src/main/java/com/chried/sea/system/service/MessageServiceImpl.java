package com.chried.sea.system.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chried.core.enums.EnumResult;
import com.chried.core.exception.SystemException;
import com.chried.core.exception.TokenException;
import com.chried.core.param.Parameter;
import com.chried.sea.redis.cache.UserCache;
import com.chried.sea.redis.repository.UserCacheRepository;
import com.chried.sea.system.mapper.MessageMapper;
import com.chried.sea.system.model.entity.MessageEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 消息服务实现.
 *
 * @author chried
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageEntity> implements MessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private UserCacheRepository userCacheRepository;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private MessageService messageService;

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
    @Override
    public int count(Boolean read, String token) {
        log.info("统计消息数量");
        Optional<UserCache> userCacheOptional = userCacheRepository.findById(token.substring(Parameter.JWT_TOKEN_HEADER_PREFIX.length()));

        if (!userCacheOptional.isPresent()) {

            throw new TokenException(EnumResult.TOKEN_EXPIRE);
        }

        return queryByUserId(read, userCacheOptional.get().getUserId()).size();
    }

    /**
     * 获取用户Id获取.
     *
     * @param userId 用户id.
     * @return 消息.
     */
    @Override
    public List<MessageEntity> queryByUserId(Boolean read, String userId) {
        log.info("根据用户id获取消息");
        // read不存在查询全部，存在按照read值查询.
        if (userId == null) {

            log.error("根据用户[{}]获取消息错误", userId);
            throw new SystemException("获取消息错误");
        }

        return messageMapper.queryByUserId(read, userId);
    }
}
