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
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.common.RuleZookeeperUtil;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.entity.AuthorityRuleCorrectEntity;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoe
 * @date 2020/11/9 11:30
 */
@Component("authorityRuleZookeeperPublisher")
public class AuthorityRuleZookeeperPublisher implements DynamicRulePublisher<List<AuthorityRuleEntity>> {
    @Autowired
    private Converter<List<AuthorityRuleCorrectEntity>, String> converter;

    @Override
    public void publish(String app, List<AuthorityRuleEntity> rules) throws Exception {
        //  转换
        List<AuthorityRuleCorrectEntity> list = rules.stream().map(rule -> {
            AuthorityRuleCorrectEntity entity = new AuthorityRuleCorrectEntity();
            BeanUtils.copyProperties(rule, entity);
            return entity;
        }).collect(Collectors.toList());
        RuleZookeeperUtil.publish(app, list, converter, AuthorityRuleCorrectEntity.class);
    }
}