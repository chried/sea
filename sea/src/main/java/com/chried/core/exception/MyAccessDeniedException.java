package com.chried.core.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 自定义权限.
 *
 * @author chried
 */
public class MyAccessDeniedException extends AccessDeniedException {
    /**
     * Constructs an <code>AccessDeniedException</code> with the specified message.
     *
     * @param msg the detail message
     */
    public MyAccessDeniedException(String msg) {
        super(msg);
    }

    /**
     * Constructs an <code>AccessDeniedException</code> with the specified message and
     * root cause.
     *
     * @param msg the detail message
     * @param t   root cause
     */
    public MyAccessDeniedException(String msg, Throwable t) {
        super(msg, t);
    }
}
