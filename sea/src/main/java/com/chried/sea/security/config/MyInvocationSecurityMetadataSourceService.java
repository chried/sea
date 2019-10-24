package com.chried.sea.security.config;

import com.chried.sea.security.util.UrlMatcher;
import com.chried.sea.system.model.entity.PermissionEntity;
import com.chried.sea.system.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 加载资源与权限的对应关系,实现FilterInvocationSecurityMetadataSource接口也是必须的。
 * 这个类的工作是从数据库中获取授权信息。
 * 1.其中loadResourceDefine方法不是必须的，这个只是加载所有的资源与权限的对应关系并缓存起来，避免每次获取权限都访问数据库（提高性能），然后getAttributes根据参数（被拦截url）返回权限集合。
 * 这种缓存的实现其实有一个缺点，因为loadResourceDefine方法是放在构造器上调用的，而这个类的实例化只在web服务器启动时调用一次，那就是说loadResourceDefine方法只会调用一次，
 * 2.如果资源和权限的对应关系在启动后发生了改变，那么缓存起来的权限数据就和实际授权数据不一致，那就会授权错误了。但如果资源和权限对应关系是不会改变的，这种方法性能会好很多。
 * 要想解决权限数据的一致性,可以直接在getAttributes方法里面调用数据库操作获取权限数据，通过被拦截url获取数据库中的所有权限，封装成Collection<ConfigAttribute>返回就行了。（灵活、简单
 * 3.容器启动加载顺序：1：调用loadResourceDefine()方法  2：调用supports()方法   3：调用getAllConfigAttributes()方法
 *
 * @author chried
 */
@Component
@Slf4j
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    /**
     * 存放资源配置对象.
     */
    public static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Resource
    private UrlMatcher urlMatcher;

    @Resource
    private PermissionService permissionService;

    /**
     * 参数是要访问的url，返回这个url对于的所有权限（或角色）
     * 每次请求后台就会调用 得到请求所拥有的权限
     * 这个方法在url请求时才会调用，服务器启动时不会执行这个方法
     * getAttributes这个方法会根据你的请求路径去获取这个路径应该是有哪些权限才可以去访问。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //取消这段代码注释情况下,每次服务启动后请求后台只有到数据库中取一次权限;如果注释掉这段代码则每次请求都会到数据库中取权限
        if (resourceMap == null) {
            // 每次请求 都会去数据库查询权限  貌似很耗性能
            loadResourceDefine();
        }
        // object 是一个URL，被用户请求的url。
        String url = ((FilterInvocation) object).getRequestUrl();
        log.info("请求 url:{}", url);
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        //循环已有的角色配置对象 进行url匹配
        for (String s : resourceMap.keySet()) {
            String resUrl = s.trim();
            // 路径支持Ant风格的通配符.
            if (urlMatcher.pathMatchesUrl(resUrl, url)) {
                Collection<ConfigAttribute> configAttributes = resourceMap.get(resUrl);
                return configAttributes;
            }
        }
        // 返回null，如果未配置url权限，那么不进行验证.
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        //要返回true  不然要报异常　　 SecurityMetadataSource does not support secure object class: class
        return FilterInvocation.class.isAssignableFrom(aClass);
    }


    /**
     * 初始化资源 ,提取系统中的所有权限，加载所有url和权限（或角色）的对应关系， web容器启动就会执行
     * 如果启动@PostConstruct 注解   则web容器启动就会执行
     */
    @PostConstruct
    public void loadResourceDefine() {
        resourceMap = new ConcurrentHashMap<>(256);
        /**
         * 存储的是项目中权限的列表，用集合的方式存储是因为一个url可能会对应多种权限，
         * 在数据库的字段中可以通过一个特殊的字符进行分割，这样就可以做到不同权限的用户
         * 查看不通的界面效果。
         */
        Collection<ConfigAttribute> perList;
        //用来存储权限的容器
        ConfigAttribute cfg;
        //从数据库查询全部的权限信息
        List<PermissionEntity> permissions = permissionService.list();
        //循环权限信息并通过数据库的url字段为key,传入不通的权限值，并将其存入到urlPerMap中去。
        for (PermissionEntity permission : permissions) {
            perList = new HashSet<>();
            cfg = new SecurityConfig(permission.getSign());
            perList.add(cfg);
            resourceMap.put(permission.getUrl(), perList);
        }
    }
}
