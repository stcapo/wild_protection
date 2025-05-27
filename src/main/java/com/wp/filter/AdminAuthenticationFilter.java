package com.wp.filter;

import com.wp.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Filter to check if user is logged in as admin
 */
@WebFilter(urlPatterns = {"/admin-dashboard.html", "/admin/*"})
public class AdminAuthenticationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        HttpSession session = httpRequest.getSession(false);
        
        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        boolean isAdmin = false;
        
        if (isLoggedIn) {
            User user = (User) session.getAttribute("user");
            isAdmin = "admin".equals(user.getRole());
        }
        
        if (isLoggedIn && isAdmin) {
            // User is logged in as admin, continue request
            chain.doFilter(request, response);
        } else {
            // User is not admin, redirect to admin login page
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin-login.html");
        }
    }

    @Override
    public void destroy() {
    }
}
