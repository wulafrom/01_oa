package com.mashibing.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 系统配置相关
 * @author: h'mm
 * @date: 2021/3/9 18:31:55
 */
@Component
public class SystemConfig {

    @Value("${system.name}")
    private String systemName;

    public SystemConfig() {
    }

    public SystemConfig(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
