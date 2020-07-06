package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.common.quartz.exception.TopUpJobExecutionException;
import com.redknee.topup.efill.common.jobs.AbstractReportJob;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
@SuppressWarnings("PMD.AvoidCatchingGenericException")
public class CtsMainAccountSuccessPerChannelDailyReportJob extends AbstractReportJob {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    log.info(
        "CtsMainAccountSuccessPerChannelDailyReportJob execute method started");

    setJobContext(jobExecutionContext);
    try {
      getReportBean().generateCtsMaSuccessPerCHDailyReport(get2FromDate(), get2ToDate());
    } catch (RuntimeException ex) {
      log.error("CtsMainAccountSuccessPerChannelDailyReportJob error - " + ex.getMessage());
      throw new TopUpJobExecutionException(ex);
    }
    log.info(
        "CtsMainAccountSuccessPerChannelDailyReportJob execute method finished");
  }

}
