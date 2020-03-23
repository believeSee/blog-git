package com.chenshijie;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)/*需要指定异常的返回状态，才能让springboot知道是一个什么样的异常*/
public class NotFoundException extends RuntimeException {/*通常继承运行时异常，不继承其他异常*/

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
