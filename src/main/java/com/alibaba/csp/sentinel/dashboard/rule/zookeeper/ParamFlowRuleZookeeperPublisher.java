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

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.common.RuleZookeeperUtil;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.entity.ParamFlowRuleCorrectEntity;
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
@Component("paramFlowRuleZookeeperPublisher")
public class ParamFlowRuleZookeeperPublisher implements DynamicRulePublisher<List<ParamFlowRuleEntity>> {
    @Autowired
    private Converter<List<ParamFlowRuleCorrectEntity>, String> converter;

    @Override
    public void publish(String app, List<ParamFlowRuleEntity> rules) throws Exception {
        List<ParamFlowRuleCorrectEntity> list = rules.stream().map(rule -> {
            ParamFlowRuleCorrectEntity entity = new ParamFlowRuleCorrectEntity();
            BeanUtils.copyProperties(rule, entity);
            return entity;
        }).collect(Collectors.toList());
        RuleZookeeperUtil.publish(app, list, converter, ParamFlowRuleCorrectEntity.class);
    }
}