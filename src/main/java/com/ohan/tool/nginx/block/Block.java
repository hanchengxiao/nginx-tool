package com.ohan.tool.nginx.block;

import com.ohan.tool.nginx.constants.FileConstants;
import com.ohan.tool.nginx.exception.EditParamException;

import java.util.*;

public class Block {

    public Block(int level) {
        this.level = level;
    }

    protected final int level;
    private String key;
    private String name = "";
    private boolean outputName = false;
    private boolean hasBlockBody = false;

    private final Map<String, NginxParam> paramMap = new HashMap<>();

    private final Set<Block> allChildren = new HashSet<>();

    public Block newChild() throws EditParamException {
        Block block = new Block(level + 1);
        allChildren.add(block);
        return block;
    }

    public void removeChild(Block block) {
        allChildren.remove(block);
    }

    public void set(String key, List<String> values) throws EditParamException {
        for (String value : values) {
            add(key, value);
        }
    }

    public void add(String key, String value) throws EditParamException {
        // 空值校验逻辑
        if (key == null || value == null) {
            throw new EditParamException("key or value is null,if u want del this key,please use delParam method");
        }
        NginxParam nginxParam;
        if (!paramMap.containsKey(key)) {
            nginxParam = new NginxParam();
            paramMap.put(key, nginxParam);
        }
        paramMap.get(key).key(key).addValue(value);
    }

    public void del(String key) {
        paramMap.remove(key);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean outputName() {
        return outputName;
    }

    public void setOutputName(boolean outputName) {
        this.outputName = outputName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isHasBlockBody() {
        return hasBlockBody;
    }

    public void setHasBlockBody(boolean hasBlockBody) {
        this.hasBlockBody = hasBlockBody;
    }

    public Set<Block> getAllChildren() {
        return allChildren;
    }

    public Map<String, NginxParam> getParamMap() {
        return paramMap;
    }

    public List<Block> getChildrenBlock() {
        return null;
    }

    public Block getChildByName(String name) {
        Block rs = null;
        for (Block block : allChildren) {
            if ((name == null || name.isEmpty() || (block.name != null && name.trim().equals(block.name.trim())))) {
                rs = block;
                break;
            }
            rs = block.getChild(key, name);
            if (rs != null) {
                break;
            }
        }
        return rs;
    }

    public Block getChild(String key, String name) {
        Block rs = null;
        for (Block block : allChildren) {
            if (key != null && key.equals(block.key) && (name == null || name.isEmpty() || name.equals(block.name))) {
                rs = block;
                break;
            }
            rs = block.getChild(key, name);
            if (rs != null) {
                break;
            }
        }
        return rs;
    }

    public String toString() {
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < level; i++) {
            space.append("\t");
        }
        StringBuilder sb = new StringBuilder();
        // 块声明部分
        if (hasBlockBody) {
            sb.append(space);
            sb.append(key);
            if (outputName) {
                sb.append(" ").append(name);
            }
            sb.append(FileConstants.BRACE_LEFT);
            sb.append(FileConstants.LINE_BREAK);
        }

        // 参数处理部分
        paramMap.values().stream().map(NginxParam::toString).forEach(param -> {
            sb.append(space);
            if (hasBlockBody) {
                sb.append("\t");
            }
            sb.append(param);
            sb.append(FileConstants.LINE_BREAK);
        });

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
        if (hasBlockBody) {
            sb.append(space);
            sb.append(FileConstants.BRACE_RIGHT);
            sb.append(FileConstants.LINE_BREAK);
        }
        return sb.toString();
    }
}
