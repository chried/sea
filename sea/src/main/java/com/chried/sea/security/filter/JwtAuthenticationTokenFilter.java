package com.chried.sea.security.filter;

import com.alibaba.fastjson.JSONObject;
import com.chried.core.param.Parameter;
import com.chried.sea.redis.cache.UserCache;
import com.chried.sea.redis.repository.UserCacheRepository;
import com.chried.sea.security.util.JwtTokenUtils;
import com.chried.utils.ResultUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * jwt拦截器.
 *
 * @author chried
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {

    private RequestMatcher authenticationRequestMatcher;

    @Resource
    private UserCacheRepository userCacheRepository;

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, RequestMatcher authenticationRequestMatcher) {
        super(authenticationManager);
        this.authenticationRequestMatcher = authenticationRequestMatcher;
    }

    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, RequestMatcher authenticationRequestMatcher) {
        super(authenticationManager, authenticationEntryPoint);
        this.authenticationRequestMatcher = authenticationRequestMatcher;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        //过滤掉不需要token验证的url
//        if (authenticationRequestMatcher != null && !authenticationRequestMatcher.matches(request)) {
//            chain.doFilter(request, response);
//            return;
//        }

        String authHeader = request.getHeader(Parameter.ACCESS_TOKEN);

        if (authHeader != null && authHeader.startsWith(Parameter.JWT_TOKEN_HEADER_PREFIX)) {
            final String accessToken = authHeader.substring(Parameter.JWT_TOKEN_HEADER_PREFIX.length());

            UsernamePasswordAuthenticationToken authentication = getAuthentication(response, accessToken);

            if (authentication != null) {

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletResponse response, String accessToken) {
        if (StringUtils.isNotBlank(accessToken)) {

            // 验证redis token是否过期.
            Optional<UserCache> userCacheOptional = userCacheRepository.findById(accessToken);
            if (!userCacheOptional.isPresent()) {
                // 不存在那么过期.
                log.error("token超过有效期，请重新登");
                ResultUtil.out(response, ResultUtil.resultMap(401, "令牌过期，请重新登录"));
                return null;
            }

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(JwtTokenUtils.publicKey)
                        .parseClaimsJws(accessToken)
                        .getBody();
                //获取用户名
                String username = claims.getSubject();
                String userId = claims.getId();
                //获取权限（角色）
                List<GrantedAuthority> authorities = new ArrayList<>();
                String authority = claims.get(JwtTokenUtils.AUTH_HEADER).toString();
                if (!StringUtils.isEmpty(authority)) {
                    List<Map<String, String>> authrityMap = JSONObject.parseObject(authority, List.class);
                    for (Map<String, String> role : authrityMap) {
                        if (MapUtils.isNotEmpty(role)) {
                            authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                        }
                    }
                }
                if (StringUtils.isNotBlank(username)) {
                    //此处password不能为null
                    User principal = new User(username, "", authorities);
                    return new UsernamePasswordAuthenticationToken(principal, userId, authorities);
                }
            } catch (ExpiredJwtException e) {

                UserCache userCache = userCacheOptional.get();
                // 重新生成token.
                String token = JwtTokenUtils.generateToken(userCache);
                userCache.setId(token);
                userCache.setCreateDate(LocalDateTime.now());
                userCacheRepository.save(userCache);
                response.setHeader(Parameter.ACCESS_TOKEN, Parameter.JWT_TOKEN_HEADER_PREFIX + token);
            } catch (Exception e) {
                ResultUtil.out(response, ResultUtil.resultMap(401, "令牌无效，请重新登录"));
            }
        }
        return null;
    }
}