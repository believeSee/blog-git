package com.chenshijie.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*拦截所有方法(第一个 "*" )，
    *这些方法是:
    * com.chenshijie.web包下的 所有类(第二个 "*")  的
    * 所有方法(第三个 "*")，
    * 这些方法可以带任意的参数(两个 "..")  */
    @Pointcut("execution(* com.chenshijie.web.*.*(..))" )
    public void log() {}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        /*我们需要获取到请求的url和访问者ip，需要在HttpServletRequest中获取
        * 而HttpServletRequest需要从ServletRequestAttributes中获取
        * 我们需要获取到请求的方法名和参数，需要在JoinPoint对象中获取
        * */

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();

        /*getDeclaringTypeName():获取到类名
        * getName()：获取到方法名
        * */
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() +
                "." +
                joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();

        LogRequestMessage logRequestMessage = new LogRequestMessage(url, ip, classMethod, args);

        /*最后，将获取到的这些信息放到日志中。。。*/
        logger.info("RequestMessage is {}", logRequestMessage);
    }

    /*@After("log()")
    public void doAfter() {
        logger.info("这是切面之--后--的方法！！！");
    }*/

    @AfterReturning(pointcut = "log()", returning = "result")
    public void doAfterReturning(Object result) {
        logger.info("AfterReturning is {}", result);
    }

    private class LogRequestMessage {
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public LogRequestMessage(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "LogRequestMessage{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
