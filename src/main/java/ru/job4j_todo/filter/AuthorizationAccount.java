package ru.job4j_todo.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthorizationAccount implements Filter {

    /**
     * Фильтр является связующим элементом для получения информации
     * об аккаунтах пользователя
     * @param servletRequest - принимает запрос от пользователся
     * @param servletResponse - сервер отправяет ответ на запрос
     * @param filterChain - обрабатывает входящую информацию
     *                    от пользователя req/res(запрос/ответ)
     * @throws IOException может вызвать IOException в связи с неправильным запросом
     * @throws ServletException может вызвать ServletException
     */
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
                || uri.endsWith("login")
                || uri.endsWith("createAccount")) {
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
