package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.cts.common.jobs.base.BaseCtsTransactionCleanerJob;
import com.redknee.topup.cts.common.logging.CommonLogger;
import com.redknee.topup.cts.common.logging.ParamList;
import com.redknee.topup.legacy.cts.scap.at.siemens.nexus.connector.scap.exception.ConnectorNoConnectionException;
import com.redknee.topup.legacy.cts.ucip.exception.INException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@Slf4j
@DisallowConcurrentExecution
public class CtsTransactionCleanerRollbackRetryJob extends BaseCtsTransactionCleanerJob  {

  private static final CommonLogger COMMON_LOGGER = CommonLogger.getCommonLogger(log);
  public static final String ROLLBACK_RETRY = "ROLLBACK_RETRY";

  @Override
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    boolean rollbackRetry = getBooleanConfig(jobExecutionContext, ROLLBACK_RETRY);
    // retry in case rollback is pending
    if (rollbackRetry) {
      try {
        rollbackRetryTask();
      } catch (INException e) {
        e.printStackTrace();
      } catch (ConnectorNoConnectionException e) {
        e.printStackTrace();
      }
    }
  }

  private void rollbackRetryTask() throws INException, ConnectorNoConnectionException {
    final String method = COMMON_LOGGER.start("rollbackRetryTask", new ParamList(COMMON_LOGGER));
    try {
      getCtsTransactionCleanerBean().failedRollbackRetry();
    } catch (RuntimeException e) {
      COMMON_LOGGER.error(method, "error during rollbackRetryTask", e);
    }

    COMMON_LOGGER.end(method);
  }
}
