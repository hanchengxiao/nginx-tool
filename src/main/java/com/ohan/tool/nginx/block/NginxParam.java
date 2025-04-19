package com.ohan.tool.nginx.block;

import com.ohan.tool.nginx.constants.FileConstants;
import com.ohan.tool.nginx.type.ParamType;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示Nginx配置参数的数据结构
 *
 * <p>用于构建和操作Nginx配置中的参数条目，支持链式调用
 */
public class NginxParam {
    private final ParamType type;
    private String key;
    private List<String> values = new ArrayList<>();

    /**
     * 构造Nginx参数对象
     *
     * @param type 参数类型枚举，决定参数在nginx配置中的行为特征
     */
    public NginxParam(ParamType type) {
        this.type = type;
    }

    /**
     * 创建Nginx参数对象的工厂方法
     *
     * @param type 参数类型枚举
     * @return 新创建的NginxParam实例
     */
    public static NginxParam of(ParamType type) {
        return new NginxParam(type);
    }

    /**
     * 获取参数类型
     *
     * @return 当前参数的类型枚举值
     */
    public ParamType type() {
        return type;
    }

    /**
     * 获取参数键名
     *
     * @return 当前参数的键名字符串
     */
    public String key() {
        return key;
    }

    /**
     * 设置参数键名（链式调用）
     *
     * @param key 要设置的键名字符串
     * @return 当前对象用于链式调用
     */
    public NginxParam key(String key) {
        this.key = key;
        return this;
    }

    /**
     * 获取参数值列表
     *
     * @return 当前参数的值列表（可修改的列表引用）
     */
    public List<String> values() {
        return values;
    }

    /**
     * 替换整个值列表（链式调用）
     *
     * @param values 新的值列表
     * @return 当前对象用于链式调用
     */
    public NginxParam values(List<String> values) {
        this.values = values;
        return this;
    }

    /**
     * 添加单个参数值（链式调用）
     *
     * @param value 要添加的值
     * @return 当前对象用于链式调用
     */
    public NginxParam addValue(String value) {
        values.add(value);
        return this;
    }

    /**
     * 删除指定参数值（链式调用）
     *
     * @param value 要删除的值
     * @return 当前对象用于链式调用
     */
    public NginxParam delValue(String value) {
        values.remove(value);
        return this;
    }

    /**
     * 生成nginx配置格式字符串
     *
     * <p>格式为：key value1 value2 ...; 其中多个值用空格分隔，
     * 结尾添加分号（通过FileConstants常量获取符号）
     *
     * @return 符合nginx配置语法的参数字符串
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 拼接多个参数值，值之间用空格分隔
        for (int i = 0; i < values.size(); i++) {
            sb.append(values.get(i));
            if (i != values.size() - 1) {
                sb.append(" ");
            }
        }
        return key + " " + sb + FileConstants.SEMICOLON;
    }
}
