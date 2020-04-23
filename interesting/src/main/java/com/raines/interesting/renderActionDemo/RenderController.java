package com.raines.interesting.renderActionDemo;

import com.raines.interesting.renderActionDemo.render.Render;
import com.raines.interesting.renderActionDemo.render.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/render")
public class RenderController {

    /**
     * 通过request获取参数，通过response响应
     * @param response
     * @param request
     */
    @RequestMapping("/demo")
    public void demo(HttpServletResponse response, HttpServletRequest request){
        Result result= new Result();
        String param1 = request.getParameter("param1");
        result.setSuccess("200");
        result.setMsg("成功");
        result.setData(param1);
        Render.rend(response, result);
    }

}
