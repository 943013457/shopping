package com.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Creator Ming
 * @Time 2019/8/14
 * @Other
 */
@Controller
public class GetUserController {

    @RequestMapping(value = {"/getUser"}, method = RequestMethod.GET)
    private void getUser(HttpServletResponse response) throws IOException {
        Object name = SecurityUtils.getSubject().getPrincipal();
        if (name == null) {
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(name.toString());
    }
}
