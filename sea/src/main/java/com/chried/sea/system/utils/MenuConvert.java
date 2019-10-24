package com.chried.sea.system.utils;

import com.chried.sea.system.model.entity.MenuEntity;
import com.chried.sea.system.model.vo.MenuResult;
import org.springframework.beans.BeanUtils;

/**
 * 菜单转换类.
 *
 * @author chried
 */
public final class MenuConvert {

    /**
     * 转换为实体类.
     *
     * @param result vo.
     * @return 实体类.
     */
    public static MenuEntity toMenuEntity(MenuResult result) {

        MenuEntity menuEntity = new MenuEntity();

        BeanUtils.copyProperties(result, menuEntity, "children");

        return menuEntity;
    }

    /**
     * 转为vo.
     *
     * @param menuEntity 实体类.
     * @return vo.
     */
    public static MenuResult toMenuResult(MenuEntity menuEntity) {

        MenuResult result = new MenuResult();

        BeanUtils.copyProperties(menuEntity, result);

        return result;
    }
}
