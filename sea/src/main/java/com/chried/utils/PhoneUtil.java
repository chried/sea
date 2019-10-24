package com.chried.utils;

/**
 * 电话工具类.
 *
 * @author chried
 */
public final class PhoneUtil {

    /**
     * 隐藏手机号.
     *
     * @param phone 手机号.
     * @return 返回手机号信息.
     */
    public static String hidePhone(String phone) {

        return phone.substring(0, 3) + "****" + phone.substring(7);
    }
}