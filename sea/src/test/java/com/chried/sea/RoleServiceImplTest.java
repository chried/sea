package com.chried.sea;

import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = RoleServiceImplTest.class)
@RunWith(SpringRunner.class)
@ComponentScan(value = "com.chried")
@Slf4j
public class RoleServiceImplTest {

    @Resource
    private RoleService roleService;

    @Test
    public void test_add() {

        for (int i = 1; i <= 10; i++) {

            RoleEntity roleEntity = new RoleEntity();

            roleEntity.setName("role-" + i);
            roleEntity.setSign("role-" + i);

            roleService.save(roleEntity);
        }

    }
}
