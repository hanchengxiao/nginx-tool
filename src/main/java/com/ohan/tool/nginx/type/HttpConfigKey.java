package com.ohan.tool.nginx.type;

public enum HttpConfigKey {
    /**
     * include
     * include mime.types
     */
    INCLUDE("include"),
    /**
     * default_type
     * application/octet-stream
     */
    DEFAULT_TYPE("default_type"),
    /**
     * access_log
     * /var/log/nginx/access.log
     */
    ACCESS_LOG("access_log"),
    /**
     * error_log
     * /var/log/nginx/error.log
     */
    ERROR_LOG("error_log"),
    /**
     * sendfile
     * on/off
     */
    SENDFILE("sendfile"),
    /**
     * tcp_nopush
     * on/off
     */
    TOP_NOPUSH("tcp_nopush"),
    /**
     * tcp_nodelay
     * on/off
     */
    TCP_NODELAY("tcp_nodelay"),
    /**
     * keepalive_timeout
     * 60s
     */
    KEEPALIVE_TIMEOUT("keepalive_timeout"),
    /**
     * client_body_buffer_size
     * 4k
     */
    CLIENT_BODY_BUFFER_SIZE("client_body_buffer_size"),
    /**
     * client_header_buffer_size
     * 1k
     */
    CLIENT_HEADER_BUFFER_SIZE("client_header_buffer_size"),
    /**
     * client_max_body_size
     * 1m
     */
    CLIENT_MAX_BODY_SIZE("client_max_body_size"),
    /**
     * gzip
     * on/off
     */
    GZIP("gzip"),
    /**
     * gzip_types
     * text/plain text/css application/json application/javascript
     */
    GZIP_TYPES("gzip_types"),
    /**
     * gzip_min_length
     * 1k
     */
    GZIP_MIN_LENGTH("gzip_min_length"),
    //    upstream backend {
//        server 192.168.1.10:8080;
//        server 192.168.1.11:8080;
//    }
    /**
     * log_format
     * '$remote_addr - $remote_user [$time_local] "$request" '
     * '$status $body_bytes_sent "$http_referer" '
     * '"$http_user_agent"'
     */
    LOG_FORMAT("log_format"),
//    /**
//     * map
//     * map $http_host $host_name {
//     *      default localhost;
//     *      192.168.1.10 www.example.com;
//     *      192.168.1.11 www.example.com;
//     * }
//     */
    /**
     * open_file_cache
     * 64 60s;
     */
    OPEN_FILE_CAHCE("open_file_cache"),
    /**
     * open_file_cache_valid
     * 60s
     */
    OPEN_FILE_CACHE_VALID("open_file_cache_valid"),
    /**
     * open_file_cache_min_uses
     * 2
     */
    OPEN_FILE_CACHE_MIN_USES("open_file_cache_min_uses"),
    /**
     * proxy_set_header
     * Host $host;
     */
    PROXY_SET_HEADER("proxy_set_header"),
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
    ;
    private final String key;

    HttpConfigKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
