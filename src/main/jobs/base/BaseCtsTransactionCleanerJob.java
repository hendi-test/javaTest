package com.redknee.topup.cts.common.jobs.base;

import com.redknee.topup.common.config.Config;
import com.redknee.topup.cts.service.recharge.CtsTransactionCleanerBean;
import javax.enterprise.inject.spi.CDI;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

public abstract class BaseCtsTransactionCleanerJob implements Job {

  protected boolean getBooleanConfig(JobExecutionContext jobExecutionContext, String configName) {
    return jobExecutionContext.getMergedJobDataMap().getBoolean(configName);
  }

  public CtsTransactionCleanerBean getCtsTransactionCleanerBean() {
    return CDI.current().select(CtsTransactionCleanerBean.class).get();
  }

  public Config getConfig() {
    return CDI.current().select(Config.class).get();
  }
}
