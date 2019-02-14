package com.vince.service;
/*
接口
 */
import com.vince.bean.User;
import com.vince.utils.BusinessException;

public interface UserService {

    public User register(User user)throws BusinessException;

    public User login(String username,String password) throws BusinessException;
}
