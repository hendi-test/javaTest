package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.common.quartz.exception.TopUpJobExecutionException;
import com.redknee.topup.efill.common.jobs.AbstractReportJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsDedicatedAccountSuccessPerChannelDailyExeJob extends AbstractReportJob {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    log.info(
        "CtsDedicatedAccountSuccessPerChannelDailyReportJob execute method started");

    setJobContext(jobExecutionContext);
    try {
      getReportBean().generateCtsDedicatedAccountSuccessPerChannelDailyReport(getFromDate(), getToDate());
    } catch (RuntimeException ex) {
      log.error("CtsDedicatedAccountSuccessPerChannelDailyReportJob error - " + ex.getMessage());
      throw new TopUpJobExecutionException(ex);
    }

    log.info(
        "CtsDedicatedAccountSuccessPerChannelDailyReportJob execute method finished");
  }

}
