package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.common.counter.CounterHelper;
import com.redknee.topup.common.counter.exception.TopupCounterAccessException;
import com.redknee.topup.cts.common.Const.PerfCounterName;
import javax.enterprise.inject.spi.CDI;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsPerfCounterResetExecutionJob implements Job {

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    log.debug("CtsPerfCounterResetJob started");

    resetCounters();

    log.debug("CtsPerfCounterResetJob finished");
  }

  private void resetCounters() {
    CounterHelper counterHelper = getCounterHelper();
    for (PerfCounterName perfCounterName : PerfCounterName.values()) {
      try {
        log.debug("CtsPerfCounterResetJob BEGIN reset of counter with name {}", perfCounterName.getName());
        counterHelper.reset(perfCounterName.getName());
        log.debug("CtsPerfCounterResetJob END reset of counter with name {}", perfCounterName.getName());
      } catch (TopupCounterAccessException ex) {
        log.error("Error during reset counter with name: {}", perfCounterName.getName(), ex);
      }
    }
  }

  public CounterHelper getCounterHelper() {
    return CDI.current().select(CounterHelper.class).get();
  }
}
