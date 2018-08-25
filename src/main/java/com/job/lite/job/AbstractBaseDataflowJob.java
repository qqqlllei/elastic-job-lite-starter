package com.job.lite.job;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.job.lite.constant.ElasticJobConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractBaseDataflowJob<T> implements DataflowJob<T> {



	Logger logger = LoggerFactory.getLogger(this.getClass());

	protected abstract List<T> fetchJobData(JSONObject jobTaskParameter);
	protected abstract void processJobData(List<T> taskList);

	@Override
	public List<T> fetchData(ShardingContext shardingContext) {
		String jobName = shardingContext.getJobName();
		int shardingItem = shardingContext.getShardingItem();
		int shardingTotalCount = shardingContext.getShardingTotalCount();
		String taskId = shardingContext.getTaskId();
		String parameter = shardingContext.getJobParameter();
		JSONObject jobParameter =JSONObject.parseObject(parameter);
		jobParameter.put(ElasticJobConstant.ELASTIC_SHARDING_ITEM_KEY,String.valueOf(shardingItem));
		jobParameter.put(ElasticJobConstant.ELASTIC_SHARDING_TOTAL_COUNT_KEY,String.valueOf(shardingTotalCount));
		logger.info("扫描worker任务列表开始,jobName={}, shardingItem={}, shardingTotalCount={}, taskId={}", jobName, shardingItem, shardingTotalCount, taskId);
		long startTimestamp = System.currentTimeMillis();
		List<T> taskLst = fetchJobData(jobParameter);
		int taskNo = taskLst != null ? taskLst.size() : 0;
		long endTimestamp = System.currentTimeMillis();
		logger.info("扫描worker任务列表结束共计加载[{}]个任务, 耗时=[{}]",taskNo, (endTimestamp - startTimestamp));
		return taskLst;
	}

	@Override
	public void processData(ShardingContext shardingContext, List<T> workerTask) {
		logger.info("任务[" +shardingContext.getJobName() + "]开始执行...");
		long startTimestamp = System.currentTimeMillis();
		processJobData(workerTask);
		long endTimestamp = System.currentTimeMillis();
		logger.info("任务[" + shardingContext.getJobName() + "]执行完毕:耗时=[{}]", (endTimestamp - startTimestamp));
	}
}
