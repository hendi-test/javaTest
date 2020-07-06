package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.common.quartz.exception.TopUpJobExecutionException;
import com.redknee.topup.efill.common.jobs.AbstractReportJob;
import com.redknee.topup.efill.service.report.impl.DefaultEfillReportBean.TotalAggregationReportType;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsMainAccountSuccessTotalAggExecutionJob extends AbstractReportJob {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.info(
        "CtsMainAccountSuccessTotalAggExecutionJob execute method started");

    setJobContext(jobExecutionContext);
    try {
      final Date currentDate = getPreviousDay();
      getReportBean().generateCtsTotalAggregationReport(atStartOfDay(currentDate),
          atEndOfDay(currentDate), TotalAggregationReportType.MA_SUCCESS_TOTAL);
    } catch (RuntimeException ex) {
      log.error("CtsMainAccountSuccessTotalAggExecutionJob error - " + ex.getMessage());
      throw new TopUpJobExecutionException(ex);
    }

    log.info(
        "CtsMainAccountSuccessTotalAggExecutionJob execute method finished");
  }
}
