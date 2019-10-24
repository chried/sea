package com.chried.sea.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chried.core.exception.SystemException;
import com.chried.core.param.Refer;
import com.chried.core.param.Result;
import com.chried.sea.security.util.JwtTokenUtils;
import com.chried.sea.system.mapper.UserMapper;
import com.chried.sea.system.model.dto.HandleTreeDTO;
import com.chried.sea.system.model.entity.*;
import com.chried.sea.system.model.vo.MenuResult;
import com.chried.sea.system.model.vo.UserInfoResult;
import com.chried.sea.system.utils.MenuConvert;
import com.chried.service.AbstractEntityServiceImpl;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户接口实现.
 *
 * @author chried
 */
@Service
@Slf4j
public class UserServiceImpl extends AbstractEntityServiceImpl<UserMapper, UserEntity> implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    @Resource
    private RoleMenuService roleMenuService;

    /**
     * 根据token获取用户信息.
     *
     * @param token 令牌.
     * @return 用户信息.
     */
    @Override
    public UserInfoResult getUserInfo(String token) {

        Claims claims = JwtTokenUtils.parseToken(token);
        if (claims == null) {

            log.error("获取用户信息错误，参考信息:token无效");
            throw new SystemException("获取用户信息错误");
        }
        //获取用户名
        String username = claims.getSubject();
        String userId = claims.getId();

        UserEntity userEntity = userMapper.selectById(userId);

        UserInfoResult result = new UserInfoResult();

        result.setUserId(userId);
        result.setAccount(username);
        result.setNickname(userEntity.getNickname());
        result.setEmail(userEntity.getEmail());
        result.setPortrait(userEntity.getPortrait());

        // 获取用户拥有的角色.
        List<RoleEntity> roles = userRoleService.findRoleByUserId(userEntity.getId());

        List<Refer> roleRefers = roles.stream().map(r -> new Refer(r.getId(), r.getSign(), r.getName())).collect(Collectors.toList());

        result.setRoles(roleRefers);
        // 提取出roleIds.
        List<String> roleIds = roleRefers.stream().map(Refer::getId).collect(Collectors.toList());

        // 获取用户拥有的权限.
        List<PermissionEntity> permissionEntities = rolePermissionService.selectPermissionByRoleIds(roleIds);
        result.setPermissions(permissionEntities.stream().map(p -> new Refer(p.getId(), p.getSign(), p.getName())).collect(Collectors.toList()));

        // 查询菜单.
        List<MenuEntity> menuEntities = roleMenuService.queryByRoleIds(roleIds);

        if (CollectionUtils.isEmpty(menuEntities)) {

            throw new SystemException("您没有任何菜单，请联系管理员");
        }

        // 按照父节点分组.
        // key为null，表示父级节点.也可以只有一个菜单.
        Map<Optional<String>, List<MenuEntity>> parentMap = menuEntities.stream().collect(Collectors.groupingBy(s -> Optional.ofNullable(s.getParentId())));

        List<MenuResult> results = new ArrayList<>(menuEntities.size());

        parentMap.forEach((k, v) -> {

            if (!k.isPresent()) {

                for (MenuEntity menuEntity : v) {
                    if (parentMap.get(Optional.of(menuEntity.getId())) == null) {
                        results.add(MenuConvert.toMenuResult(menuEntity));
                    }
                }
            } else {

                Optional<MenuEntity> menuEntityOptional = parentMap.get(Optional.empty()).stream().filter(s -> s.getId().equals(k.get())).findFirst();
                if (menuEntityOptional.isPresent()) {
                    MenuResult parentResult = MenuConvert.toMenuResult(menuEntityOptional.get());
                    List<MenuResult> childMenuResults = v.stream().map(MenuConvert::toMenuResult).collect(Collectors.toList());
                    parentResult.setChildren(childMenuResults);
                    results.add(parentResult);
                }
            }

        });
        result.setMenus(results);
        return result;
    }

    /**
     * 处理用户授权角色.
     *
     * @param userRoleDTO 用户授权角色传输类.
     * @return 返回信息.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<String> handleUserRole(HandleTreeDTO userRoleDTO) {
        log.info("处理用户授权角色....");

        QueryWrapper<UserRoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userRoleDTO.getId());

        userRoleService.remove(queryWrapper);

        List<UserRoleEntity> userRoleEntities = userRoleDTO.getIds().stream().map(s -> {
            UserRoleEntity userRoleEntity = new UserRoleEntity();
            userRoleEntity.setUserId(userRoleDTO.getId());
            userRoleEntity.setRoleId(s);
            // 默认都是一年.
            userRoleEntity.setExpireDate(LocalDateTime.now().plusYears(1));
            return userRoleEntity;
        }).collect(Collectors.toList());

        userRoleService.saveBatch(userRoleEntities);

        return Result.of("操作成功");
    }
}
