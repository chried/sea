package com.chried.core.param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页传输参数.
 *
 * @author chried
 */
@ApiModel(description = "分页传输类")
@Data
public class PageParameter<T> implements Serializable {

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 排序参数.
     * <pre>
     *     key字段名.
     *     value排序方式.desc或者asc.
     * </pre>
     */
    private Map<String, String> orders;

    /**
     * 键值对相同参数.
     */
    private Map<String, Object> params;

    /**
     * 满足一个搜索框可以搜索多个情况，配合columns使用.
     */
    private String q;

    /**
     * 列名.
     * 配合q,代表一个q跟多个列比对.
     */
    private List<String> columns;


    /**
     * 创建分页信息.
     *
     * @return 分页.
     */
    public Page<T> createPage() {

        Page<T> page = new Page<>();

        page.setCurrent(getCurrent());
        page.setTotal(getTotal());
        page.setSize(getSize());

        if (MapUtils.isNotEmpty(orders)) {

            List<OrderItem> orderItems = new ArrayList<>(orders.size());

            orders.forEach((k, v) -> {

                OrderItem orderItem = new OrderItem();

                orderItem.setAsc(Parameter.ASC.equals(v));
                orderItem.setColumn(k);

                orderItems.add(orderItem);
            });

            page.setOrders(orderItems);
        }

        return page;
    }

    /**
     * 创建wrapper.
     *
     * @return wrapper.
     */
    public Wrapper<T> createWrapper() {

        QueryWrapper<T> queryWrapper = new QueryWrapper<>();

        if (MapUtils.isNotEmpty(params)) {

            queryWrapper.allEq(params);
        }

        if (StringUtils.isNotBlank(q) && CollectionUtils.isNotEmpty(columns)) {

            StringBuilder sb = new StringBuilder();

            sb.append("(");

            int size = columns.size();

            for (int i = 0; i < size; i++) {
                if (i < size - 1) {
                    sb.append(columns.get(i)).append(" like '").append(q).append("%' or ");
                } else {
                    sb.append(columns.get(i)).append(" like '").append(q).append("%'");
                }
            }

            sb.append(")");

            queryWrapper.apply(sb.toString());
        }

        return queryWrapper;

    }
}
