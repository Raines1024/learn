package com.raines.javaadvanced.expectionprocess;

import com.raines.comm.$;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionController {

    /**
     * 业务逻辑处理异常
     */
    @ExceptionHandler(LogicException.class)
    public Map<String, Object> handleLogicException(HttpServletRequest request, LogicException exception) {
        log.error("业务逻辑处理异常",exception);
        String message = exception.getMessage();
        List<String> errors = new ArrayList<>();
        errors.add(message);
        Map<String, Object> data = new HashMap<>();
        data.put("errors", errors);
        return $.reason(exception.code,message,data);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    public Object globalException(HttpServletRequest request, Exception e) {
        log.error("未知异常",e);
        return $.reason(500,"未知异常",e.getMessage());
    }

}
