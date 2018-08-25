package com.job.lite.attribute;

import java.io.Serializable;

public class JobParameter implements Serializable {
	private static final long serialVersionUID = -610797345091216847L;
	private int fetchNum;
	int shardingItem;
	int shardingTotalCount;

	public JobParameter setShardingItem(final int shardingItem) {
		this.shardingItem = shardingItem;
		return this;
	}

	public JobParameter setShardingTotalCount(final int shardingTotalCount) {
		this.shardingTotalCount = shardingTotalCount;
		return this;
	}

	public int getFetchNum() {
		return fetchNum;
	}

	public void setFetchNum(int fetchNum) {
		this.fetchNum = fetchNum;
	}
}
