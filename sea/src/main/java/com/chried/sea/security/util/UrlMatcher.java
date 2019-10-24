package com.chried.sea.security.util;

/**
 * url过滤接口.
 *
 * @author chried
 */
public interface UrlMatcher {
    Object compile(String paramString);

    boolean pathMatchesUrl(Object paramObject, String paramString);

    String getUniversalMatchPattern();

    boolean requiresLowerCaseUrl();
}
