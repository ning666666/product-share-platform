package com.share.platform.api.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.platform.api.constant.ResultCode;
import com.share.platform.api.utils.ResultVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ShiroFilter extends BasicHttpAuthenticationFilter {

    /**
        sendChallenge重写的目的是避免前端在没有登录的情况下访问@RequiresPermissions()等未授权接口返回401错误，
        给前端调用接口一个数据，让前端去重新登陆
        如果使用浏览器访问，浏览器会弹出一个输入账号密码的弹框，重写后浏览器访问出现接口数据
    **/
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        System.out.println("Authentication required: sending 401 Authentication challenge response.");
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        responseSkip(httpResponse, ResultCode.USER_NOT_LOGIN);
        return false;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("https://example.com"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        //  在配置的ShiroFilterFactoryBean拦截过滤器里，必须使用无状态的token 这里如果没有token 直接告诉前端需要重新登陆
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(ShiroConstant.authorization_token);
        if(authorization == null || authorization.length() == 0){
            //  未携带token  不需要提示前端自动跳转重新登陆
            responseSkip(httpServletResponse, ResultCode.NOT_TOKEN_LOGIN);
            return false;
        }

        //  验证token的正确性
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            // token失效 提示前端需要自动跳转重新登陆
            responseSkip(httpServletResponse, ResultCode.TOKEN_INVALIDATION_JUMP_LOGIN);
            return false;
        }

        return super.preHandle(request, response);
    }

    private void responseSkip(HttpServletResponse response, ResultCode code){
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String str = objectMapper.writeValueAsString(ResultVo.buildCode(code));
            response.getWriter().println(str);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }
    }


}
