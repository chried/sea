package com.chried.sea;

import com.chried.sea.system.model.entity.UserRoleEntity;
import com.chried.sea.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = UserRoleServiceImplTest.class)
@RunWith(SpringRunner.class)
@ComponentScan(value = "com.chried")
@Slf4j
public class UserRoleServiceImplTest {

    @Resource
    private UserRoleService userRoleService;

    @Test
    public void test_add() {


    }
}
