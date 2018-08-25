package com.job.lite.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.google.common.base.Splitter;
import com.job.lite.attribute.JobParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public abstract class AbstractBaseDataflowJob<T> implements DataflowJob<T> {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	protected abstract List<T> fetchJobData(JobParameter jobTaskParameter);
	protected abstract void processJobData(List<T> taskList);

	@Override
	public List<T> fetchData(ShardingContext shardingContext) {
		String jobName = shardingContext.getJobName();
		int shardingItem = shardingContext.getShardingItem();
		int shardingTotalCount = shardingContext.getShardingTotalCount();
		String taskId = shardingContext.getTaskId();
		String parameter = shardingContext.getJobParameter();
		final Map<String, String> map = Splitter.on("").withKeyValueSeparator(",").split(parameter);
		JobParameter jobTaskParameter = new JobParameter();
		jobTaskParameter.setShardingItem(shardingItem).setShardingTotalCount(shardingTotalCount);
		logger.info("扫描worker任务列表开始,jobName={}, shardingItem={}, shardingTotalCount={}, taskId={}", jobName, shardingItem, shardingTotalCount, taskId);
		long startTimestamp = System.currentTimeMillis();
		List<T> taskLst = fetchJobData(jobTaskParameter);
		int taskNo = taskLst != null ? taskLst.size() : 0;
		long endTimestamp = System.currentTimeMillis();
		logger.info("扫描worker任务列表结束共计加载[{}]个任务, 耗时=[{}]",taskNo, (endTimestamp - startTimestamp));
		return taskLst;
	}

	/**
	 * Process data.
	 *
	 * @param shardingContext the sharding context
	 * @param workerTask      the worker task
	 */
	@Override
	public void processData(ShardingContext shardingContext, List<T> workerTask) {
		logger.info("任务[" + workerTask.get(0).getClass().getName() + "]开始执行...");
		long startTimestamp = System.currentTimeMillis();
		processJobData(workerTask);
		long endTimestamp = System.currentTimeMillis();
		logger.info("任务[" + workerTask.get(0).getClass().getName() + "]执行完毕:耗时=[{}]", (endTimestamp - startTimestamp));
	}
}
