package com.chried.sea;

import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = PermissionServiceImplTest.class)
@RunWith(SpringRunner.class)
@ComponentScan(value = "com.chried")
@Slf4j
public class PermissionServiceImplTest {

    @Resource
    private PermissionService permissionService;

    @Test
    public void test_add() {

        for (int i = 1; i <= 20; i++) {

            PermissionEntity permissionEntity = new PermissionEntity();

            permissionEntity.setName("permission-" + i);
            permissionEntity.setSign("permission-" + i);
            permissionEntity.setUrl("order/get/" + i);

            permissionService.save(permissionEntity);
        }

    }

    @Test
    public void test_update_url() {
        List<PermissionEntity> list = permissionService.list();

        for (PermissionEntity permissionEntity : list) {
            permissionEntity.setUrl("/" + permissionEntity.getUrl());
            permissionService.updateById(permissionEntity);
        }

    }
}
