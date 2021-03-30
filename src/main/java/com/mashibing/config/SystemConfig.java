package com.mashibing.config;

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

    @Value("${system.domain}")
    private String domain;

    public SystemConfig() {
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}
