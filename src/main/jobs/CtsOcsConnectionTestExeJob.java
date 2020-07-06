package com.redknee.topup.cts.common.jobs;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsOcsConnectionTestExeJob implements Job {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    log.info("CtsOcsConnectionTestJob execute method dummy implementation executed");
  }
}
