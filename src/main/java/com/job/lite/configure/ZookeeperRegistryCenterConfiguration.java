package com.job.lite.configure;

import com.dangdang.ddframe.job.api.ElasticJob;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.job.lite.annotation.ElasticJobConfig;
import com.job.lite.attribute.ZookeeperCenterProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(ElasticJob.class)
@ConditionalOnBean(annotation = ElasticJobConfig.class)
@EnableConfigurationProperties(ZookeeperCenterProperties.class)
public class ZookeeperRegistryCenterConfiguration {

	private final ZookeeperCenterProperties zookeeperCenterProperties;
	@Autowired
	public ZookeeperRegistryCenterConfiguration(ZookeeperCenterProperties zookeeperCenterProperties) {
		this.zookeeperCenterProperties = zookeeperCenterProperties;
	}

	/**
	 * Reg center zookeeper registry center.
	 *
	 * @return the zookeeper registry center
	 */
	@Bean(initMethod = "init")
	@ConditionalOnMissingBean
	public ZookeeperRegistryCenter zookeeperRegistryCenter() {
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(zookeeperCenterProperties.getZkAddressList(), zookeeperCenterProperties.getNamespace());
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(zookeeperCenterProperties.getBaseSleepTimeMilliseconds());
		zookeeperConfiguration.setConnectionTimeoutMilliseconds(zookeeperCenterProperties.getConnectionTimeoutMilliseconds());
		zookeeperConfiguration.setMaxSleepTimeMilliseconds(zookeeperCenterProperties.getMaxSleepTimeMilliseconds());
		zookeeperConfiguration.setSessionTimeoutMilliseconds(zookeeperCenterProperties.getSessionTimeoutMilliseconds());
		zookeeperConfiguration.setMaxRetries(zookeeperCenterProperties.getMaxRetries());
		zookeeperConfiguration.setDigest(zookeeperCenterProperties.getDigest());
		return new ZookeeperRegistryCenter(zookeeperConfiguration);
	}

}
