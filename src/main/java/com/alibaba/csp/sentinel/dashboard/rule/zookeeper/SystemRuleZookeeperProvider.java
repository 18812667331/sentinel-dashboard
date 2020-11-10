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

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.common.RuleZookeeperUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zoe
 * @date 2020/11/9 11:30
 */
@Component("systemRuleZookeeperProvider")
public class SystemRuleZookeeperProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {
    @Autowired
    private Converter<String, List<SystemRuleEntity>> converter;

    @Override
    public List<SystemRuleEntity> getRules(String appName) throws Exception {
        return RuleZookeeperUtil.getRules(appName, converter, SystemRuleEntity.class);
    }
}