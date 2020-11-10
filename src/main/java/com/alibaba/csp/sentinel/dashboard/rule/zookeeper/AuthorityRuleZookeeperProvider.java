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

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.common.RuleZookeeperUtil;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.entity.AuthorityRuleCorrectEntity;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.entity.ParamFlowRuleCorrectEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2020/11/9 11:30
 */
@Component("authorityRuleZookeeperProvider")
public class AuthorityRuleZookeeperProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {
    @Autowired
    private CuratorFramework zkClient;
    @Autowired
    private Converter<String, List<AuthorityRuleCorrectEntity>> converter;

    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        String zkPath = ZookeeperConfigUtil.getPath(appName, AuthorityRuleCorrectEntity.class);
        Stat stat = zkClient.checkExists().forPath(zkPath);
        if (stat == null) {
            return new ArrayList<>(0);
        }
        byte[] bytes = zkClient.getData().forPath(zkPath);
        if (null == bytes || bytes.length == 0) {
            return new ArrayList<>();
        }
        String s = new String(bytes);
        List<AuthorityRuleCorrectEntity> list = converter.convert(s);
        // 转换
        return list.stream().map(rule -> {
            AuthorityRule authorityRule = new AuthorityRule();
            BeanUtils.copyProperties(rule, authorityRule);
            AuthorityRuleEntity entity = AuthorityRuleEntity.fromAuthorityRule(rule.getApp(), rule.getIp(), rule.getPort(), authorityRule);
            entity.setId(rule.getId());
            entity.setGmtCreate(rule.getGmtCreate());
            return entity;
        }).collect(Collectors.toList());
    }
}