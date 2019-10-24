package com.chried.utils;

import com.alibaba.fastjson.JSON;
import com.chried.core.enums.EnumResult;
import com.chried.core.param.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回结果工具类
 * 用于封装返回结果，返回json.
 *
 * @author chried
 */
@Slf4j
public class ResultUtil {

    /**
     * 使用response输出JSON
     *
     * @param response  输出.
     * @param resultMap json.
     */
    public static void out(ServletResponse response, Map<String, Object> resultMap) {

        out(response, resultMap, 1);
    }

    /**
     * 使用response输出JSON
     *
     * @param response 输出.
     * @param result   json.
     */
    public static void out(ServletResponse response, Result result) {

        out(response, result, 2);

    }

    /**
     * 转换为json.
     *
     * @param response 返回.
     * @param object   对象.
     * @param type     标志而已.
     */
    public static void out(ServletResponse response, Object object, int type) {

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(object));
        } catch (Exception e) {
            log.error(e + "输出JSON出错,type:" + type);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * 转换为json
     *
     * @param code 编号.
     * @param msg  消息.
     * @return json.
     */
    public static Result resultMap(Integer code, String msg) {

        return Result.of(code, msg);
    }

    /**
     * 返回json.
     *
     * @param result 枚举.
     * @return 返回.
     */
    public static Result resultMap(EnumResult result) {

        return Result.of(result);
    }

    /**
     * 转换为json
     *
     * @param code 编号.
     * @param msg  消息.
     * @param data 数据.
     * @return json.
     */
    public static Result resultMap(Integer code, String msg, Object data) {

        Result<Object> of = Result.of(code, msg);
        of.setData(data);

        return of;
    }
}
