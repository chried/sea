package com.chried.sea.redis.repository;

import com.chried.sea.redis.cache.UserCache;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户缓存操作.
 *
 * @author chried
 */
@Repository
public interface UserCacheRepository extends KeyValueRepository<UserCache, String> {
}
