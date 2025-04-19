package com.ohan.tool.nginx.block;

import com.ohan.tool.nginx.type.ParamType;

public class NginxServerBlock extends Block {
    @Override
    public ParamType getBlockType() {
        return ParamType.SERVER;
    }
}
