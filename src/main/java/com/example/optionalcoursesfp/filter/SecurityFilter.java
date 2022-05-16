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
        if (uri.matches(".*(css|jpg|png|gif|js)")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Pattern pattern = Pattern.compile("\\w+\\.");
        Matcher matcher = pattern.matcher(uri);
        String role = "";
        log.info(user);
        log.info(uri);
        while (matcher.find()) {
            role = matcher.group().replaceAll("\\.", "");
        }
        if ((uri.contains("/loginPage.jsp") || uri.contains("/registerPage.jsp")
                 || uri.contains("/changeLocale"))||(user == null &&  uri.contains("/dispatcher-servlet"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;

        }
       else if((user==null && !uri.contains("/dispatcher-servlet"))
                || (!user.getRole().toString().toLowerCase().equals(role) && !uri.contains("/dispatcher-servlet"))){
            httpRequest.getSession().invalidate();
            httpResponse.sendRedirect("loginPage.jsp");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
