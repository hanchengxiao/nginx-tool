package com.ohan.tool.nginx.block;

import com.ohan.tool.nginx.constants.FileConstants;
import com.ohan.tool.nginx.exception.EditParamException;
import com.ohan.tool.nginx.type.ParamType;

import java.util.*;

/**
 * 表示Nginx配置块的基础结构，用于构建配置文件的层级结构
 * 包含参数集合和子配置块的管理功能，支持多种块类型（通过ParamType区分）
 */
public class Block {
    /**
     * 构造空名称的配置块
     */
    public Block() {
        this.name = null;
    }

    /**
     * 构造指定名称的配置块
     *
     * @param name 配置块的名称（如location块的路径匹配规则）
     */
    public Block(String name) {
        this.name = name;
    }

    private String name;
    /**
     * 存储当前块的所有参数，键为参数名，值为参数对象
     */
    private final Map<String, NginxParam> paramMap = new HashMap<>();
    /**
     * 存储当前块的所有直接子块
     */
    private final Set<Block> allChildren = new HashSet<>();

    /**
     * 添加子配置块
     *
     * @param block 要添加的子配置块对象
     */
    public void addChild(Block block) {
        allChildren.add(block);
    }

    /**
     * 移除子配置块
     *
     * @param block 要移除的子配置块对象
     */
    public void removeChild(Block block) {
        allChildren.remove(block);
    }

    /**
     * 设置参数值（覆盖式）
     *
     * @param key    参数名称
     * @param values 参数值列表
     * @throws EditParamException 当参数键/值为空时抛出
     */
    public void set(String key, List<String> values) throws EditParamException {
        for (String value : values) {
            add(key, value);
        }
    }

    /**
     * 添加单个参数值（追加式）
     *
     * @param key   参数名称
     * @param value 参数值
     * @throws EditParamException 当参数键/值为空时抛出
     */
    public void add(String key, String value) throws EditParamException {
        // 空值校验逻辑
        if (key == null || value == null) {
            throw new EditParamException("key or value is null,if u want del this key,please use delParam method");
        }
        NginxParam nginxParam;
        if (!paramMap.containsKey(key)) {
            nginxParam = NginxParam.of(getBlockType());
            paramMap.put(key, nginxParam);
        }
        paramMap.get(key).key(key).addValue(value);
    }

    /**
     * 删除指定参数
     *
     * @param key 要删除的参数名称
     */
    public void del(String key) {
        paramMap.remove(key);
    }

    /**
     * 设置块名称
     *
     * @param name 新的块名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取块名称
     *
     * @return 当前块的名称（可能为null）
     */
    public String getName() {
        return name;
    }

    /**
     * 获取块类型（需子类覆盖实现具体类型）
     *
     * @return 默认返回NONE类型
     */
    public ParamType getBlockType() {
        return ParamType.NONE;
    }

    /**
     * 获取参数映射表
     *
     * @return 当前块的所有参数键值对
     */
    public Map<String, NginxParam> getParamMap() {
        return paramMap;
    }

    /**
     * 获取子块集合（需子类实现具体逻辑）
     *
     * @return 当前返回null，需子类覆盖
     */
    public List<Block> getChildrenBlock() {
        return null;
    }

    /**
     * 通过名称查找子块
     *
     * @param name 要查找的子块名称
     * @return 匹配的第一个子块，未找到返回null
     */
    public Block getChildByName(String name) {
        for (Block block : allChildren) {
            if (block.getName() != null && block.getName().equals(name)) {
                return block;
            }
        }
        return null;
    }

    /**
     * 通过类型关键字查找子块
     *
     * @param type 类型关键字字符串
     * @return 匹配的第一个子块，未找到返回null
     */
    public Block getChildByType(String type) {
        for (Block block : allChildren) {
            if (block.getBlockType().key() != null && block.getBlockType().key().equals(type)) {
                return block;
            }
        }
        return null;
    }

    /**
     * 通过ParamType枚举查找子块
     *
     * @param type ParamType枚举值
     * @return 匹配的第一个子块，未找到返回null
     */
    public Block getChildByType(ParamType type) {
        for (Block block : allChildren) {
            if (block.getBlockType() != null && block.getBlockType() == type) {
                return block;
            }
        }
        return null;
    }

    /**
     * 生成Nginx配置格式的字符串
     *
     * @return 符合Nginx配置语法的字符串表示
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // 块声明部分
        if (getBlockType().hasBlockBody()) {
            sb.append(getBlockType().key());
            // location块特殊处理：添加路径匹配规则
            if (getBlockType() == ParamType.LOCATION && name != null && !name.isEmpty()) {
                sb.append(" ").append(name);
            }
            sb.append(FileConstants.BRACE_LEFT);
        }

        // 参数处理部分
        paramMap.values().stream().map(NginxParam::toString).forEach(sb::append);

        // 子块处理部分
        List<Block> childrenBlock = getChildrenBlock();
        if (childrenBlock != null && !childrenBlock.isEmpty()) {
            for (Block block : childrenBlock) {
                if (block == null) {
                    continue;
                }
                sb.append(block);
            }
        }
        for (Block block : allChildren) {
            sb.append(block);
        }

        // 块闭合处理
        if (getBlockType().hasBlockBody()) {
            sb.append(FileConstants.BRACE_RIGHT);
        }
        return sb.toString();
    }
}
