{
@ElasticJobConfig(cron = "0/50 * * * * ? ", jobParameter = "{'fetchNum':'200','taskType':'SENDING_MESSAGE'}",description="还款计划定时查询接口")
@Component
public class DataflowJobDemo extends AbstractBaseDataflowJob<User> {

	Logger logger = LoggerFactory.getLogger(DataflowJobDemo.class);

	private List<User> list = Lists.newArrayList(new User(1L), new User(2L));

	@Override
	protected List<User> fetchJobData(final JSONObject jobTaskParameter) {
		logger.info("fetchJobData - jobTaskParameter={}", jobTaskParameter);

		/**
		 * users = SELECT * FROM user WHERE status = 0 AND MOD(id, shardingTotalCount) = shardingItem Limit 0, 30
		 */

		return list;
	}

	@Override
	protected void processJobData(final List<User> taskList) {
		logger.info("processJobData - taskList={}", taskList);
	}
}
}