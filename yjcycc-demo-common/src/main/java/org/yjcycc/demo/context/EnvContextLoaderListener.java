package org.yjcycc.demo.context;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.yjcycc.tools.zk.prop.SystemProperties;

import javax.servlet.ServletContextEvent;
import java.util.Properties;

/**
 * Tomcat启动时加载环境变量
 */
public class EnvContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        super.contextInitialized(event);

        String env = "test";
        // 加载环境变量
        try {
            Properties prop = new Properties();
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("env.properties"));
            env = prop.getProperty("env");
        } catch (Exception e) {
            e.printStackTrace();
        }
        SystemProperties.setEnviroment(StringUtils.isEmpty(env) ? "test" : env);
    }

}
