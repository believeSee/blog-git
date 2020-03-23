package com.chenshijie.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice /*会拦截有Controller注解*/
public class ControllerExceptionHandler {

    //获取到一个日志对象，org包下
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class) //加上这个这家，就是一个异常处理器
    public ModelAndView exceptionHandler(HttpServletRequest request, Exception e) throws Exception {

        /*获取异常产生的路径和异常信息，平放入到日志中*/
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), e);

        /*这里做一个判断，获取如果自定义异常类标注了异常信息，
        * 那就把这个异常抛出，让springboot帮我们处理
        * 此处是和NotFoundException异常类相关
        * NotFoundException异常类标注了HttpStatus，异常抛出后springboot会帮我们去找到404.html
        * */
        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        /*
        * 不是自定义的异常，就：
        * 将异常信息放入到ModelAndView中，方便前台打印
        * */
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", e);
        mv.setViewName("error/error");

        return mv;
    }
}
