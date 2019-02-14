package com.vince.utils;
/*
异常类
注册失败 运行期异常
 */
public class BusinessException extends RuntimeException {

    public BusinessException(){
        super();
    }

    public BusinessException(String errorMessage){
        super(errorMessage);
    }
}
