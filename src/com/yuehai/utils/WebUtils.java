package com.yuehai.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

/**
 * @author 月海
 * @create 2022/1/8 14:31
 */
public class WebUtils {

    /**
     * 泛型方法
     * 把 Map 中的值注入到对应的 javaBean 属性中
     * @param value
     * @param bean
     */
    public static <T> T copyParamToBean(Map value, T bean){
        try {
            // 把所有请求的参数都注入到传入的 bean 对象中
            BeanUtils.populate(bean, value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回注入完的对象
        return bean;
    }

    /**
     * 将字符串转换为 int 类型的数据
     * @param strInt 传进来的字符串
     * @param defaultValue 默认值
     * @return
     */
    public static int parseInt(String strInt,int defaultValue){
        try {
            return Integer.parseInt(strInt);
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return defaultValue;
    }

}
