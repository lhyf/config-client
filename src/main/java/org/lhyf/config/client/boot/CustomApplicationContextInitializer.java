package org.lhyf.config.client.boot;

import org.lhyf.config.client.constant.ClientConstant;
import org.lhyf.config.client.util.ConfigManager;
import com.google.common.base.Splitter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/****
 * @author YF
 * @date 2018-07-09 16:09
 * @desc ApplicationContextInitializer
 *
 **/

public class CustomApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> ,Ordered {

    private static final Logger logger = LoggerFactory.getLogger(CustomApplicationContextInitializer.class);
    private static final Splitter NAMESPACE_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    private int order = -1;

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        CompositePropertySource composite = new CompositePropertySource(ClientConstant.DEFAULT_PROPERTY_SOURCES);
        ConfigurableEnvironment environment = context.getEnvironment();

        String appId = environment.getProperty(ClientConstant.APP_ID);
        String env = environment.getProperty(ClientConstant.ENVIRONMENT);
        String urlPrefix = environment.getProperty(ClientConstant.URL);
        String namespaces = environment.getProperty(ClientConstant.NAMESPACE);

        List<String> namespaceList = new ArrayList<>();
        if (StringUtils.hasText(appId)) {
            if (StringUtils.hasText(namespaces)) {
                namespaceList = NAMESPACE_SPLITTER.splitToList(namespaces);
                for (String namespace : namespaceList) {
                    PropertySource config = ConfigManager.getConfig(urlPrefix, appId, env, namespace);
                    composite.addPropertySource(config);
                }
            } else {
                PropertySource config = ConfigManager.getConfig(urlPrefix, appId, env);
                composite.addPropertySource(config);
            }
        }
        environment.getPropertySources().addFirst(composite);
    }

    @Override
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
