package com.chried.sea;

import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.model.entity.RolePermissionEntity;
import com.chried.sea.system.service.PermissionService;
import com.chried.sea.system.service.RolePermissionService;
import com.chried.sea.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = RolePermissionServiceImplTest.class)
@RunWith(SpringRunner.class)
@ComponentScan(value = "com.chried")
@Slf4j
public class RolePermissionServiceImplTest {

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Test
    public void test_add() {
        List<RoleEntity> roles = roleService.list();
        List<PermissionEntity> permissionEntities = permissionService.list();

        for (int i = 0; i < permissionEntities.size() - 1; i++) {

            RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();

            if (i < 10) {
                RoleEntity roleEntity = roles.get(0);
                rolePermissionEntity.setRoleId(roleEntity.getId());
                rolePermissionEntity.setPermissionId(permissionEntities.get(i).getId());
            } else {
                RoleEntity roleEntity = roles.get(1);
                rolePermissionEntity.setRoleId(roleEntity.getId());
                rolePermissionEntity.setPermissionId(permissionEntities.get(i).getId());
            }

            rolePermissionService.save(rolePermissionEntity);
        }
    }
}
