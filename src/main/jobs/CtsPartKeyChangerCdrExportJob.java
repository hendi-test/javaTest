package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.cts.common.service.CtsPartKeyChangerCdrExportService;
import javax.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Class responsible to execute part key changer CDR export job.
 */
@Slf4j
public class CtsPartKeyChangerCdrExportJob implements Job {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.info("CtsPartKeyChangerCdrExportJob execute method started");
    getCtsPartKeyChangerCdrExportService().updatePartitionAndExportTickets();
    log.info("CtsPartKeyChangerCdrExportJob execute method finished");
  }

  /**
   * Part key changer CDR export service.
   *
   * @return CtsPartKeyChangerCdrExportService object.
   */
  public CtsPartKeyChangerCdrExportService getCtsPartKeyChangerCdrExportService() {
    return CDI.current().select(CtsPartKeyChangerCdrExportService.class).get();
  }
}
