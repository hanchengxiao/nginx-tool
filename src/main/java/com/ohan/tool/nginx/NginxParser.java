package com.ohan.tool.nginx;

import com.ohan.tool.nginx.block.Block;
import com.ohan.tool.nginx.block.NginxConfig;
import com.ohan.tool.nginx.constants.FileConstants;
import com.ohan.tool.nginx.exception.EditParamException;
import com.ohan.tool.nginx.type.ServerConfigKey;
import com.sun.istack.internal.NotNull;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Nginx配置文件解析器，负责将文本配置转换为结构化的NginxConfig对象
 * 通过维护节点层级关系构建配置树，支持server/location等块的嵌套解析
 */
public class NginxParser {
    private final NginxConfig config = new NginxConfig(-1);
    private Block nowNode = config;
    private final Map<Block/*child*/, Block/*parent*/> nodeMap = new HashMap<>();

    /**
     * 获取解析完成的Nginx配置对象
     *
     * @return 完整结构的Nginx配置对象，包含所有解析出的配置块和参数
     */
    public NginxConfig getConfig() {
        return config;
    }

    /**
     * 处理单行配置文本，进行词法分析和语法解析
     *
     * @param line 原始配置行文本（需包含完整语法元素）
     * @throws ParseException     当解析到非法格式或未知配置项时抛出
     * @throws EditParamException 当参数编辑不符合规范时抛出
     *                            <p>
     *                            实现逻辑：
     *                            1. 按优先级处理三种关键符号：分号(;)/左大括号({)/右大括号(})
     *                            2. 分号表示参数结束，解析键值对并存入当前配置块
     *                            3. 左大括号表示新配置块开始，创建对应类型的Block并建立层级关系
     *                            4. 右大括号表示当前块结束，回退到父级配置块
     */
    public void handler(String line) throws ParseException, EditParamException {
        String formatLine = formatLine(FileConstants.SPACE + line);
        int paramIndex;
        int blockStartIndex;
        int blockEndIndex;
        int handleIndex;
        // 循环处理当前行直到所有符号解析完毕
        while ((paramIndex = formatLine.indexOf(FileConstants.SEMICOLON)) != -1
                | (blockStartIndex = formatLine.indexOf(FileConstants.BRACE_LEFT)) != -1
                | (blockEndIndex = formatLine.indexOf(FileConstants.BRACE_RIGHT)) != -1) {
            // 确定最优先处理的符号位置
            handleIndex = Math.max(paramIndex, Math.max(blockStartIndex, blockEndIndex));
            char handelChar = formatLine.charAt(handleIndex);
            String handleStr = formatLine.substring(0, handleIndex);
            switch (handelChar) {
                case FileConstants.SEMICOLON_CHAR: {
                    // 解析键值对参数
                    String[] paramArr = handleStr.split(FileConstants.SPACE);
                    if (paramArr.length < 2) {
                        throw new ParseException("param has no value,please check and try again:" + paramArr[0], -1);
                    }
                    for (int i = 1; i < paramArr.length; i++) {
                        // 特殊处理server_name参数
                        if (ServerConfigKey.SERVER_NAME.key().equals(paramArr[0])) {
                            nowNode.setName(paramArr[i]);
                        }
                        nowNode.add(paramArr[0], paramArr[i]);
                    }
                    break;
                }
                case FileConstants.BRACE_LEFT_CHAR: {
                    String paramStr = formatLine.substring(0, blockStartIndex);
                    String[] paramArr = paramStr.split(FileConstants.SPACE);
                    Block block = nowNode.newChild();
                    block.setKey(paramArr[0]);
                    block.setHasBlockBody(true);
                    if (paramArr.length > 1) {
                        block.setName(formatLine(paramStr.replaceFirst(paramArr[0], "")));
                        block.setOutputName(true);
                    }
                    nodeMap.put(block, nowNode);
                    nowNode = block;
                    break;
                }
                case FileConstants.BRACE_RIGHT_CHAR: {
                    nowNode = nodeMap.get(nowNode);
                    break;
                }
            }
            formatLine = formatLine.substring(handleIndex + 1);
        }
    }

    /**
     * 格式化配置行文本，标准化空格和换行符
     *
     * @param line 原始配置行（可能包含多余空格或换行符）
     * @return 标准化后的字符串，保证：
     * 1. 去除所有换行符
     * 2. 合并连续多个空格为单个
     * 3. 保留必要分隔空格
     */
    public static String formatLine(@NotNull String line) {
        char[] charArray = line.toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean pass = true;
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (c == '\r' || c == '\n') {
                continue;
            }
            if (c == ' ') {
                if (pass) {
                    continue;
                }
            }
            sb.append(c);
            pass = c == ' ' || i == charArray.length - 1;
        }
        return sb.toString();
    }
}
