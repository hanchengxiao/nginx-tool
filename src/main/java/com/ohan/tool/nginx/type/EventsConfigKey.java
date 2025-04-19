package com.ohan.tool.nginx.type;

public enum EventsConfigKey {
    /**
     * event model
     * model value: select,poll,epoll,kqueue,/dev/poll
     * use model
     */
    USE("use"),
    /**
     * Specify the maximum concurrent connections for each worker process
     * worker_connections number
     */
    WORKER_CONNECTIONS("worker_connections"),
    /**
     * Enable multi-accept
     * multi_accept on|off
     */
    MULTI_ACCEPT("multi_accept"),
    /**
     * Enable connection serialization
     * accept_mutex on|off
     */
    ACCEPT_MUTEX("accept_mutex"),
    /**
     * Delay time
     * accept_mutex_delay time
     */
    ACCEPT_MUTEX_DELAY("accept_mutex_delay"),
    /**
     * Request header buffer size
     * client_header_buffer_size size
     */
    CLIENT_HEADER_BUFFER_SIZE("client_header_buffer_size"),
    /**
     * Large request header buffer size
     * large_client_header_buffers number size
     */
    LARGE_CLIENT_HEADER_BUFFERS("large_client_header_buffers"),
    ;
    private final String key;

    EventsConfigKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }
}
