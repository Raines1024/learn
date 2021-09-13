package com.raines.springcloudsentinelsample.conf;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 限流后返回统一json格式错误
 */
@Service
public class CustomUrlBlockHandler implements UrlBlockHandler {

    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        httpServletResponse.setHeader("Content-Type","application/json;charset=UTF-8");
        String message = "{\"code\":\"500\",\"msg\":\"访问人数过多\"}";
        httpServletResponse.getWriter().write(message);
    }
}
