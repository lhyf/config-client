package org.lhyf.config.client.util;

import com.alibaba.fastjson.JSON;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Map;

/****
 * @author YF
 * @date 2018-07-09 19:09
 * @desc ConfigManager
 *
 **/
public class ConfigManager {

    /**
     * 获取指定namespace 下的配置项
     * @param urlPrefix 配置服务请求前缀
     * @param appId 项目id
     * @param env 项目环境
     * @param namespace
     * @return
     */
    public static PropertySource getConfig(String urlPrefix, String appId, String env, String namespace){
        String result = HttpUtils.sendPost(urlPrefix + "/" + appId + "/" + env + "/" + namespace, null);
        Map config = JSON.parseObject(result, Map.class);
        PropertySource ps = new MapPropertySource(namespace,config);

        return ps;
    }

    /**
     * 获取环境下所有配置项
     * @param urlPrefix
     * @param appId
     * @param env
     * @return
     */
    public static PropertySource getConfig(String urlPrefix, String appId, String env) {
        String result = HttpUtils.sendPost(urlPrefix + "/" + appId + "/" + env , null);
        Map config = JSON.parseObject(result, Map.class);
        PropertySource ps = new MapPropertySource(env,config);
        return ps;
    }
}
