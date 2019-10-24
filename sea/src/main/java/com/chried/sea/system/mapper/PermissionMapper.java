package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chried.sea.system.model.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 权限mapper.
 *
 * @author chried
 */
@Mapper
@Repository
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * 根据参数获取.
     *
     * @param name 名称.
     * @param sign 标志.
     * @param url  连接.
     * @return 权限.
     */
    @Select("select id,name,sign,url from permission where (name = #{name} or sign = #{sign} or url = #{url} )")
    PermissionEntity getByParams(@Param("name") String name, @Param("sign") String sign, @Param("url") String url);
}
