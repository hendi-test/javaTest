package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.cts.service.subscounter.SubsCounterAdminBean;
import javax.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsSubsCounterResetJob implements Job {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.info("CtsSubsCounterResetJob: start delete expired counters");
    int deletedRecords = getSubsCounterAdminBean().deleteExpiredCounters();
    log.info("CtsSubsCounterResetJob: Deleted {} expired counters", deletedRecords);
  }

  public SubsCounterAdminBean getSubsCounterAdminBean() {
    return CDI.current().select(SubsCounterAdminBean.class).get();
  }
}
