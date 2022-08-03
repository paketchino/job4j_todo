package ru.job4j_todo.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorizationAccount implements Filter {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        String uri = req.getRequestURI();
        if (uri.endsWith("loginPage")
                || uri.endsWith("addAccount")
                || uri.endsWith("allItems")
                || uri.endsWith("doneItems")
                || uri.endsWith("undoneItems")) {
            filterChain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute("account") == null) {
            res.sendRedirect(req.getContextPath() + "/loginPage");
            return;
        }
        filterChain.doFilter(req, res);
    }
}
