package com.chried.sea.security.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chried.sea.security.model.SelfUserDetails;
import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.model.entity.RoleEntity;
import com.chried.sea.system.model.entity.UserEntity;
import com.chried.sea.system.service.RolePermissionService;
import com.chried.sea.system.service.UserRoleService;
import com.chried.sea.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 获取用户权限的service
 *
 * @author chried
 */
@Component
@Slf4j
public class SelfUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RolePermissionService rolePermissionService;

    /**
     * 加载用户信息&权限
     *
     * @param username 用户名.
     * @return 返回用户信息.
     * @throws UsernameNotFoundException 用户未找到异常.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq("account", username);

        UserEntity userEntity = userService.getOne(queryWrapper);

        if (userEntity == null) {
            log.error("用户[{}]不存在", username);
            throw new UsernameNotFoundException("用户[" + username + "]不存在");
        }

        SelfUserDetails selfUserDetails = new SelfUserDetails();

        selfUserDetails.setId(userEntity.getId());
        selfUserDetails.setUsername(userEntity.getAccount());
        selfUserDetails.setPassword(userEntity.getPassword());

        // 查询角色.
        List<RoleEntity> roles = userRoleService.findRoleByUserId(userEntity.getId());
        // 筛选出roleId.
        List<String> roleIds = roles.stream().map(RoleEntity::getId).collect(Collectors.toList());

        // 查询关联全部角色.
        List<PermissionEntity> permissionEntities = rolePermissionService.selectPermissionByRoleIds(roleIds);

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        for (RoleEntity role : roles) {
            if (StringUtils.isNotBlank(role.getName())) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                //此处将角色信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
            }
        }

        for (PermissionEntity permission : permissionEntities) {
            if (permission != null && permission.getSign() != null) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getSign());
                //此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                grantedAuthorities.add(grantedAuthority);
            }
        }

        selfUserDetails.setAuthorities(grantedAuthorities);

        return selfUserDetails;

    }
}
