package com.ohan.tool.nginx.type;

import com.ohan.tool.nginx.block.*;

import java.util.HashMap;
import java.util.Map;

public enum ParamType {
    NONE("", false) {
        @Override
        public Block createBlock() {
            return null;
        }
    },
    GLOBAL("", false) {
        @Override
        public Block createBlock() {
            return new NginxConfig();
        }
    },
    EVENTS("events", true) {
        @Override
        public Block createBlock() {
            return new NginxEventsBlock();
        }
    },
    HTTP("http", true) {
        @Override
        public Block createBlock() {
            return new NginxHttpBlock();
        }
    },
    SERVER("server", true) {
        @Override
        public Block createBlock() {
            return new NginxServerBlock();
        }
    },
    LOCATION("location", true) {
        @Override
        public Block createBlock() {
            return new NginxLocationBlock();
        }
    },
    ;
    private static final Map<String, ParamType> typeMap = new HashMap<>();

    static {
        for (ParamType type : values()) {
            typeMap.put(type.key(), type);
        }
    }

    private final String key;
    private final boolean hasBlockBody;

    ParamType(String key, boolean hasBlockBody) {
        this.key = key;
        this.hasBlockBody = hasBlockBody;
    }

    public String key() {
        return key;
    }

    public boolean hasBlockBody() {
        return hasBlockBody;
    }

    public abstract Block createBlock();

    public static ParamType of(String key) {
        return typeMap.get(key);
    }
}
