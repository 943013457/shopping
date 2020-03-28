package com.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Creator Ming
 * @Time 2020/3/22
 * @Other 根据用户不同的角色进行页面分发
 */

public class PageDistribute {
    public static String getRoleIndex(HttpServletRequest request) {
        String url = "/index";
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("管理员")) {
            return "/doAdmin";
        }
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        return savedRequest != null ? savedRequest.getRequestUrl() : url;
    }
}