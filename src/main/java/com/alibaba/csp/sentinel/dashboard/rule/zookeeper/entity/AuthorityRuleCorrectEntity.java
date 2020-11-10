package com.alibaba.csp.sentinel.dashboard.rule.zookeeper.entity;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.slots.block.Rule;

import java.util.Date;

/**
 * TODO:(这里用一句话描述这个类的作用)
 *
 * @author zoe
 * @date 2020/11/10 14:57
 */
public class AuthorityRuleCorrectEntity implements RuleEntity {
    private Long id;
    private String app;
    private String ip;
    private Integer port;
    private String limitApp;
    private String resource;
    private Date gmtCreate;

    private int strategy = 0;

    public int getStrategy() {
        return strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Override
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getLimitApp() {
        return limitApp;
    }

    public void setLimitApp(String limitApp) {
        this.limitApp = limitApp;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    @Override
    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public Rule toRule() {
        return null;
    }
}