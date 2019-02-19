package com.vince.service;

import com.vince.bean.Clothes;
import com.vince.utils.BusinessException;

import java.util.List;

public interface ClothesService {
    //专门查询列表的接口
    public List<Clothes> list()throws BusinessException;
    //查询衣服的方法
    public Clothes findById(String cid)throws BusinessException;
    public void update()throws BusinessException;
}
