package com.chried.sea;

import com.alibaba.fastjson.JSON;
import com.chried.core.param.TreeNode;
import com.chried.sea.system.model.entity.MenuEntity;
import com.chried.sea.system.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MenuServiceImplTest.class)
@RunWith(SpringRunner.class)
@ComponentScan(value = "com.chried")
@Slf4j
public class MenuServiceImplTest {

    @Resource
    private MenuService menuService;

    @Test
    public void test_add() {

        for (int i = 0; i < 3; i++) {
        }
    }

    @Test
    public void test_queryMenuTree() {

        List<TreeNode> treeNodes = menuService.queryMenuTree("1178505622140203009");

        log.info("result:{}", JSON.toJSONString(treeNodes));

    }
}
