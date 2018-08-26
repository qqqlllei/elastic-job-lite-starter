package com.job.lite.listener;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.event.JobEventListener;
import com.dangdang.ddframe.job.event.type.JobExecutionEvent;
import com.dangdang.ddframe.job.event.type.JobStatusTraceEvent;

public class JobEventMessageListener implements JobEventListener {
    public JobEventMessageListener() {
    }

    public void listen(JobExecutionEvent jobExecutionEvent) {
        //TODO: 2018/8/26 0026  发送消息来聚合事件追踪的数据存储  例如可以放到mysal 或者es里存储
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(jobExecutionEvent);
        System.out.println(jsonObject);
    }

    public void listen(JobStatusTraceEvent jobStatusTraceEvent) {
        JSONObject jsonObject = (JSONObject)JSONObject.toJSON(jobStatusTraceEvent);
        System.out.println(jsonObject);
    }

    public String getIdentity() {
        return "message";
    }
}