package com.chried.sea.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chried.sea.system.model.entity.MenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * 菜单mapper.
 *
 * @author chried
 */
@Mapper
@Repository
public interface MenuMapper extends BaseMapper<MenuEntity> {

    /**
     * 名称或者编号查询菜单.
     *
     * @param name 名称.
     * @param code 编号.
     * @return 菜单.
     */
    @Select("select id,name,code,parent_id,url from menu where ( name = #{name} or code = #{code} )")
    MenuEntity findByNameOrCode(@Param("name") String name, @Param("code") String code);

}
