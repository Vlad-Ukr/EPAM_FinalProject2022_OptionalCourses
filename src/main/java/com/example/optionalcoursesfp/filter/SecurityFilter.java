package com.example.optionalcoursesfp.filter;


import com.example.optionalcoursesfp.entity.User;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecurityFilter implements Filter {
    private static final Logger log = Logger.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        User user = (User) httpRequest.getSession().getAttribute("user");
        String uri = httpRequest.getRequestURI();
        log.info(uri);
        Pattern pattern = Pattern.compile("\\w+\\.");
        Matcher matcher = pattern.matcher(uri);
        String role = "";
        while (matcher.find()) {
            role = matcher.group().replaceAll("\\.", "");
        }
        if (user == null && !uri.equals("/OptionalCoursesFP_war_exploded/loginPage.jsp")
                && !uri.equals("/OptionalCoursesFP_war_exploded/registerPage.jsp")) {
            httpRequest.getRequestDispatcher("securityError.jsp").forward(httpRequest, httpResponse);
        } else if (!user.getRole().toString().toLowerCase().equals(role)) {
            httpRequest.getRequestDispatcher("securityError.jsp").forward(httpRequest, httpResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
