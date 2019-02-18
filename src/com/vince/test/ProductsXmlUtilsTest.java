package com.vince.test;

import com.vince.bean.Clothes;
import com.vince.utils.ProductsXmlUtils;

import java.util.Arrays;
import java.util.List;

public class ProductsXmlUtilsTest {


    public void test(){
        List<Clothes> clothes = ProductsXmlUtils.perserProductFormXml();
        System.out.println(Arrays.toString(clothes.toArray()));
    }
}
