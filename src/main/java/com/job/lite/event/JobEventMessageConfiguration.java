package com.job.lite.event;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.event.JobEventListener;
import com.dangdang.ddframe.job.event.JobEventListenerConfigurationException;
import com.job.lite.listener.JobEventMessageListener;

import java.io.Serializable;

public class JobEventMessageConfiguration implements JobEventConfiguration, Serializable {
    public JobEventMessageConfiguration() {
    }

    public JobEventListener createJobEventListener() throws JobEventListenerConfigurationException {
        return new JobEventMessageListener();
    }

    public String getIdentity() {
        return "message";
    }
}