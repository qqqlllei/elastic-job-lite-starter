package com.job.lite.annotation;

import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ElasticJobConfig {

	String cron();
	int shardingTotalCount() default 1;
	String shardingItemParameters() default "";
	String jobParameter() default "";
	boolean failover() default false;
	boolean misfire() default true;
	String description() default "";
	String jobExceptionHandler() default "";
	String executorServiceHandler() default "";
	boolean streamingProcess() default false;
	String scriptCommandLine() default "";
	boolean monitorExecution() default true;
	int monitorPort() default -1;
	int maxTimeDiffSeconds() default -1;
	String jobShardingStrategyClass() default "";
	int reconcileIntervalMinutes() default 10;
	String eventTraceRdbDataSource() default "dataSource";
	boolean overwrite() default true;
	boolean disabled() default false;
	Class<? extends ElasticJobListener> listener() default ElasticJobListener.class;
	Class<? extends AbstractDistributeOnceElasticJobListener> distributedListener() default AbstractDistributeOnceElasticJobListener.class;
	long startedTimeoutMilliseconds() default Long.MAX_VALUE;
	long completedTimeoutMilliseconds() default Long.MAX_VALUE;
}
