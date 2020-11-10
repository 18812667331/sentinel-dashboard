/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.rule.zookeeper;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.entity.AuthorityRuleCorrectEntity;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.entity.ParamFlowRuleCorrectEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author zoe
 * @date 2020/11/9 11:30
 */
@Configuration
public class ZookeeperConfig {
    @Value("${zookeeper.address}")
    private String zookeeper;

    @Bean
    public Converter<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public Converter<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder() {
        return s -> JSON.parseArray(s, DegradeRuleEntity.class);
    }

    @Bean
    public Converter<List<ParamFlowRuleCorrectEntity>, String> paramFlowRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<ParamFlowRuleCorrectEntity>> paramFlowRuleEntityDecoder() {
        return s -> JSON.parseArray(s, ParamFlowRuleCorrectEntity.class);
    }

    @Bean
    public Converter<List<SystemRuleEntity>, String> systemRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<SystemRuleEntity>> systemRuleEntityDecoder() {
        return s -> JSON.parseArray(s, SystemRuleEntity.class);
    }

    @Bean
    public Converter<List<AuthorityRuleCorrectEntity>, String> authorityRuleEntityEncoder() {
        return JSON::toJSONString;
    }

    @Bean
    public Converter<String, List<AuthorityRuleCorrectEntity>> authorityRuleEntityDecoder() {
        return s -> JSON.parseArray(s, AuthorityRuleCorrectEntity.class);
    }

    @Bean
    public CuratorFramework zkClient() {
        CuratorFramework zkClient =
                CuratorFrameworkFactory.newClient(zookeeper,
                        new ExponentialBackoffRetry(ZookeeperConfigUtil.SLEEP_TIME, ZookeeperConfigUtil.RETRY_TIMES));
        zkClient.start();

        return zkClient;
    }
}