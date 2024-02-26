package com.store.book.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.store.book.enums.URole;

public class LoginFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if the user is logged in
        HttpSession session = httpRequest.getSession(false);
        boolean isLoggedIn = session != null && session.getAttribute("username") != null;

        String requestURI = httpRequest.getRequestURI();
        boolean isLoginPage = requestURI.endsWith("login.jsp") || requestURI.endsWith("login");
        boolean isSignupPage = requestURI.endsWith("register.jsp") || requestURI.endsWith("register");

        if (isLoggedIn || isLoginPage || isSignupPage) {
            chain.doFilter(request, response);
        } else {

            httpResponse.sendRedirect("login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup resources
        this.context.log("AuthenticationFilter destroyed");
    }
}
