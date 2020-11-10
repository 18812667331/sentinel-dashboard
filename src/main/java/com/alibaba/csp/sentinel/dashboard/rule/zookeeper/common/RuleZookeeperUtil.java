package com.alibaba.csp.sentinel.dashboard.rule.zookeeper.common;

import com.alibaba.csp.sentinel.dashboard.rule.zookeeper.ZookeeperConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.util.AssertUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author zoe
 * @date 2020/11/6 11:30
 */
@Component
public class RuleZookeeperUtil {
    private static CuratorFramework zkClient;

    @Autowired
    public void setZkClient(CuratorFramework zkClient) {
        RuleZookeeperUtil.zkClient = zkClient;
    }


    public static <T> List<T> getRules(String appName, Converter<String, List<T>> converter, Class<T> clazz) throws Exception {
        String zkPath = ZookeeperConfigUtil.getPath(appName, clazz);
        Stat stat = zkClient.checkExists().forPath(zkPath);
        if (stat == null) {
            return new ArrayList<>(0);
        }
        byte[] bytes = zkClient.getData().forPath(zkPath);
        if (null == bytes || bytes.length == 0) {
            return new ArrayList<>();
        }
        String s = new String(bytes);
        return converter.convert(s);
    }

    public static <T> void publish(String appName, List<T> rules, Converter<List<T>, String> converter, Class<T> clazz) throws Exception {
        AssertUtil.notEmpty(appName, "app name cannot be empty");
        String path = ZookeeperConfigUtil.getPath(appName, clazz);
        Stat stat = zkClient.checkExists().forPath(path);
        if (stat == null) {
            zkClient.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path, null);
        }
        byte[] data = CollectionUtils.isEmpty(rules) ? "[]".getBytes() : converter.convert(rules).getBytes();
        zkClient.setData().forPath(path, data);
    }
}
