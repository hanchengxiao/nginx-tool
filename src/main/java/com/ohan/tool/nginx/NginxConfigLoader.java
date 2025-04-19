package com.ohan.tool.nginx;

import com.ohan.tool.nginx.block.NginxConfig;
import com.ohan.tool.nginx.constants.FileConstants;
import com.ohan.tool.nginx.constants.SystemConstants;
import com.ohan.tool.nginx.exception.EditParamException;
import com.ohan.tool.nginx.exception.ReadOsTypeException;

import java.io.*;
import java.text.ParseException;

/**
 * Nginx配置文件加载器，提供从指定路径或自动识别系统类型的配置文件加载能力
 */
public class NginxConfigLoader {

    /**
     * 从指定路径加载Nginx配置文件
     *
     * @param nginxConfPath nginx配置文件绝对路径
     * @return 解析完成的Nginx配置对象
     * @throws IOException           当文件读取失败时抛出
     * @throws ParseException        当配置文件解析失败时抛出
     * @throws EditParamException    当配置参数不符合规范时抛出
     * @throws FileNotFoundException 当配置文件不存在时抛出
     */
    public static NginxConfig load(String nginxConfPath) throws IOException, ParseException, EditParamException {
        File configFile = new File(nginxConfPath);
        // 前置校验：配置文件必须存在
        if (!configFile.exists()) {
            throw new FileNotFoundException("nginx config file not found,please check and try again");
        }

        // 使用解析器逐行处理配置文件
        NginxParser parser = new NginxParser();
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            reader.readLine(); // 跳过首行（通常为nginx版本声明）

            // 过滤注释行并进行解析
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith(FileConstants.HASH)) {
                    continue;
                }
                parser.handler(line);
            }
        }
        return parser.getConfig();
    }

    /**
     * 自动识别操作系统类型并加载对应默认路径的Nginx配置文件
     *
     * @return 解析完成的Nginx配置对象
     * @throws ReadOsTypeException 当无法识别操作系统类型时抛出
     * @throws IOException         当文件读取失败时抛出
     * @throws ParseException      当配置文件解析失败时抛出
     * @throws EditParamException  当配置参数不符合规范时抛出
     */
    public static NginxConfig load() throws ReadOsTypeException, IOException, ParseException, EditParamException {
        // 获取系统类型并标准化处理
        String os = System.getProperty("os.name");
        if (os == null || os.trim().isEmpty()) {
            throw new ReadOsTypeException("read os type err,please use load(String nginxConfPath) try load again");
        }
        os = os.toLowerCase();

        // 根据操作系统类型选择配置文件路径
        if (os.contains("win")) {
            return load(SystemConstants.NGINX_CONF_PATH_WIN);
        } else if (os.contains("mac")) {
            return load(SystemConstants.NGINX_CONF_PATH_MAC);
        } else if (os.contains("linux")) {
            return load(SystemConstants.NGINX_CONF_PATH_LINUX);
        } else {
            throw new ReadOsTypeException("unknown os type,please use load(String nginxConfPath) try load again");
        }
    }
}
