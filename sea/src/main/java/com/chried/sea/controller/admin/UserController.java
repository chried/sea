package com.chried.sea.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chried.core.param.PageParameter;
import com.chried.core.param.Parameter;
import com.chried.core.param.Result;
import com.chried.sea.system.model.dto.HandleTreeDTO;
import com.chried.sea.system.model.entity.UserEntity;
import com.chried.sea.system.model.vo.UserInfoResult;
import com.chried.sea.system.service.UserService;
import com.chried.utils.PhoneUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户控制器.
 *
 * @author chried
 */
@RestController("admin$user")
@RequestMapping(value = "admin/user")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 获取用户信息.
     *
     * @param token token.
     * @return 用户信息.
     */
    @GetMapping(value = "getUserInfo")
    public Result<UserInfoResult> getUserInfo(@RequestHeader(value = Parameter.ACCESS_TOKEN) String token) {

        return Result.of(userService.getUserInfo(token));
    }

    /**
     * 分页查询.
     *
     * @return 分页.
     */
    @PostMapping(value = "query")
    @ApiOperation(value = "分页查询")
    public Page<UserEntity> query(@RequestBody PageParameter<UserEntity> page) {

        Page<UserEntity> userPage = (Page<UserEntity>) userService.page(page.createPage(), page.createWrapper());
        userPage.getRecords().forEach(s -> {
            s.setPhone(PhoneUtil.hidePhone(s.getPhone()));
        });

        return userPage;
    }

    /**
     * 处理用户授权角色.
     *
     * @param userRoleDTO 传输类.
     * @return 操作信息.
     */
    @PostMapping(value = "handleUserRole")
    @ApiOperation(value = "处理用户授权角色")
    public Result<String> handleUserRole(@RequestBody HandleTreeDTO userRoleDTO) {

        return userService.handleUserRole(userRoleDTO);
    }
}
