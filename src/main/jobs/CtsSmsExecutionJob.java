package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.cts.service.smsscheduler.SmsSchedulerBean;
import javax.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsSmsExecutionJob implements Job {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    if (log.isDebugEnabled()) {
      log.debug("CtsSmsExecutionJob started");
    }
    getSmsSchedulerBean().sendScheduledSms();

    if (log.isDebugEnabled()) {
      log.debug("CtsSmsExecutionJob finished");
    }
  }

  public SmsSchedulerBean getSmsSchedulerBean() {
    return CDI.current().select(SmsSchedulerBean.class).get();
  }

}
