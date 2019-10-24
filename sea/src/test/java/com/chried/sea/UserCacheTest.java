package com.chried.sea;

import com.chried.sea.redis.cache.UserCache;
import com.chried.sea.redis.repository.UserCacheRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Optional;

@SpringBootTest(classes = RoleServiceImplTest.class)
@RunWith(SpringRunner.class)
@ComponentScan(value = "com.chried")
@Slf4j
public class UserCacheTest {

    @Resource
    private UserCacheRepository userCacheRepository;

    @Test
    public void test() {

        UserCache userCache = new UserCache();

        userCache.setId("SEA+eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJjaHJpZWQiLCJqdGkiOiIxMTc3MDI3MTgzOTk0MzU1NzE0IiwiZXhwIjoxNTcxMTk1Mzg0LCJhdXRoIjoiW3tcImF1dGhvcml0eVwiOlwicm9sZS0xXCJ9LHtcImF1dGhvcml0eVwiOlwicm9sZS0yXCJ9LHtcImF1dGhvcml0eVwiOlwicGVybWlzc2lvbi0xXCJ9LHtcImF1dGhvcml0eVwiOlwicGVybWlzc2lvbi0yXCJ9LHtcImF1dGhvcml0eVwiOlwiUk9MRV9BTk9OWU1PVVNcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTRcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTVcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTZcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTdcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLThcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTlcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTEwXCJ9LHtcImF1dGhvcml0eVwiOlwicGVybWlzc2lvbi0xMVwifSx7XCJhdXRob3JpdHlcIjpcInBlcm1pc3Npb24tMTJcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTEzXCJ9LHtcImF1dGhvcml0eVwiOlwicGVybWlzc2lvbi0xNFwifSx7XCJhdXRob3JpdHlcIjpcInBlcm1pc3Npb24tMTVcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTE2XCJ9LHtcImF1dGhvcml0eVwiOlwicGVybWlzc2lvbi0xN1wifSx7XCJhdXRob3JpdHlcIjpcInBlcm1pc3Npb24tMThcIn0se1wiYXV0aG9yaXR5XCI6XCJwZXJtaXNzaW9uLTE5XCJ9XSJ9.B8o1gbMdiuWljTorysNMr5Cp9uX9qg0-CmYbGh7nWa_4ZZRf9rIob2F5MxykvVadEpalimuQVb3pWIIrXnnqt7GoKoQCWdzIHzZ3BKHkv545ylsqNL3b8TKA5jVTPdwAY16Oz1ETgyW4GdmClxC9w8OquX30eedsNArcBKZTwTA");
        userCache.setNickname("gwb");

        userCacheRepository.save(userCache);
    }

    @Test
    public void test_get() {
        Optional<UserCache> byId = userCacheRepository.findById("123456");

        if (byId.isPresent()) {
            System.out.println(byId.get());
        } else {
            System.out.println("123456");
        }
    }
}
