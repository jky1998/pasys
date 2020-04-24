package ntu.jky.filter;

import ntu.jky.authority.LoginStaff;
import ntu.jky.bean.Staff;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 获取登陆的用户
 */
@WebFilter(filterName = "StaffFilter",
        urlPatterns = {"/staff/input","/staff/manage","/authority/index","/plan/common","/plan/formulate",
            "/plan/input","/plan/manage","/plan/query"})
public class StaffFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) req;
        HttpServletResponse httpResponse = (HttpServletResponse) resp;
        HttpSession session = httpRequest.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        if (staff != null) {
            req.setAttribute("staff", staff);
            LoginStaff loginStaff = LoginStaff.getInstance();
            req.setAttribute("loginStaff", loginStaff);
            chain.doFilter(req, resp);
        } else {
            httpResponse.sendRedirect("/common/login.html");
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
