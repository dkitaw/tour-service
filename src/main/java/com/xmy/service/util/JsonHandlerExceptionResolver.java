
package com.xmy.service.util;


import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JsonHandlerExceptionResolver implements HandlerExceptionResolver {

    private static Logger logger = LoggerFactory.getLogger(JsonHandlerExceptionResolver.class);

    @ResponseBody
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception exception) {
        ModelAndView mv = new ModelAndView();
        Map<String, String[]> parameterMap = request.getParameterMap();
        logException(handler, request, exception, parameterMap);
        JsonResponse jsonResponse = new JsonResponse(exception);
        JSONObject result = new JSONObject();
        result.put("code", jsonResponse.getCode());
        result.put("message", jsonResponse.getMessage());
        try {
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            response.getWriter().write(result.toString());
        } catch (IOException e) {
            logger.error("json response error", exception);
            logger.error("error", e);
        }
        return mv;
    }

    private void logException(Object handler, HttpServletRequest request,
                              Exception exception, Map<String, String[]> parameterMap) {
        if (handler != null && HandlerMethod.class.isAssignableFrom(handler.getClass())) {
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                Class<?> beanType = handlerMethod.getBeanType();
                String methodName = handlerMethod.getMethod().getName();
                RequestMapping controllerRequestMapping = beanType.getDeclaredAnnotation(RequestMapping.class);
                String classMapping = "";
                if (controllerRequestMapping != null) {
                    classMapping = controllerRequestMapping.value()[0];
                }
                RequestMapping methodRequestMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
                String methodMapping = "";
                if (methodRequestMapping != null) {
                    methodMapping = methodRequestMapping.value()[0];
                }
                logger.error("异常：\n Class:"+classMapping+"\n MethodName:"+methodName+"\n MethodMapping:"+methodMapping);
                if (exception instanceof BusinessException) {
                        logger.error("业务异常：");
                        logger.error("code:"+((BusinessException) exception).getCode()+" Message:"+exception.getMessage());
                } else {
                    logger.error("系统异常：");
                    logger.error(exception.toString(),exception);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error(handler + " execute failed.", exception);
                logger.error("error", e);
            }
        } else {
            logger.error(handler + " execute failed.", exception);
        }
    }

    private String msg(Exception e) {
        StringBuilder builder = new StringBuilder();
        StackTraceElement[] elements = e.getStackTrace();
        builder.append(e.getClass());
        builder.append(":");
        builder.append(e.getMessage());
        builder.append("\n");

        for (StackTraceElement element : elements) {
            String className = element.getClassName();
            if (className != null && className.startsWith("com.icekredit")) {
                builder.append("\t");
                builder.append(element.getClassName());
                builder.append("#");
                builder.append(element.getMethodName());
                builder.append("(");
                builder.append(element.getFileName());
                builder.append(":");
                builder.append(element.getLineNumber());
                builder.append(")\n");
            }
        }

        return builder.toString();
    }

}
