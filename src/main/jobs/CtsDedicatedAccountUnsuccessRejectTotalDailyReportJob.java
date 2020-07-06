package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.common.quartz.exception.TopUpJobExecutionException;
import com.redknee.topup.efill.common.jobs.AbstractReportJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsDedicatedAccountUnsuccessRejectTotalDailyReportJob extends AbstractReportJob {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    log.info(
        "CtsDedicatedAccountUnsuccessRejectTotalDailyReportJob execute method started");

    setJobContext(jobExecutionContext);

    try {
      getReportBean().generateCtsDedicatedAccountUnsuccessRejectTotalDaily(getFromDate(), getToDate());
    } catch (RuntimeException ex) {
      throw new TopUpJobExecutionException(ex);
    }

    log.info(
        "CtsDedicatedAccountUnsuccessRejectTotalDailyReportJob execute method finished");
  }

}
