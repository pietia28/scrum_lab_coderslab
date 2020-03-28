package pl.coderslab.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/app/*")
public class AuthFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();

        if (Objects.nonNull(session.getAttribute("authorised"))) {
            chain.doFilter(req, resp);
        } else {
            RequestDispatcher dispatcher = req.getRequestDispatcher("/login");
            dispatcher.forward(req, resp);
        }
    }


    public void init(FilterConfig config) throws ServletException {

    }

}
