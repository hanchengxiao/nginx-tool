package com.ohan.tool.nginx.type;

public enum ServerConfigKey {
    /**
     * domain name
     * tool.tencent.com
     */
    SERVER_NAME("server_name"),
    /**
     * listen
     * [::]:443 ssl http2
     */
    LISTEN("listen"),
    /**
     * root
     * /home/www/tool.tencent.com
     */
    ROOT("root"),
    /**
     * index
     * index.html index.htm
     */
    INDEX("index"),
    /**
     * ssl_certificate
     * /etc/nginx/ssl/tool.tencent.com.pem
     */
    SSL_CERTIFICATE("ssl_certificate"),
    /**
     * ssl_certificate_key
     * /etc/nginx/ssl/tool.tencent.com.key
     */
    SSL_CERTIFICATE_KEY("ssl_certificate_key"),
    /**
     * ssl_protocols
     * TLSv1 TLSv1.1 TLSv1.2
     */
    SSL_PROTOCOLS("ssl_protocols"),
    /**
     * ssl_ciphers
     * HIGH:!aNULL:!MD5
     */
    SSL_CIPHERS("ssl_ciphers"),
    /**
     * ssl_prefer_server_ciphers
     * on/off
     */
    SSL_PREFER_SERVER_CIPHERS("ssl_prefer_server_ciphers"),
    /**
     * access_log
     * /var/log/nginx/tool.tencent.com.access.log
     */
    ACCESS_LOG("access_log"),
    /**
     * error_log
     * /var/log/nginx/tool.tencent.com.error.log
     */
    ERROR_LOG("error_log"),
    /**
     * client_body_buffer_size
     * 1k/2k/4k/8k/16k/32k/64k/128k/256k/512k/1m/2m/4m/8m/16m/32m/64m/128m/256m/512m/1g
     */
    CLIENT_MAX_BODY_SIZE("client_max_body_size"),
    /**
     * error_page
     * 404 /404.html
     */
    ERR_PAGE("error_page"),
    /**
     * add_header
     * Strict-Transport-Security max-age=31536000
     */
    ADD_HEADER("add_header"),
    ;
    private final String key;

    ServerConfigKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
