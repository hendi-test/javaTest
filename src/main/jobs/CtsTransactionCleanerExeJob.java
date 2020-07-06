package com.redknee.topup.cts.common.jobs;

import com.redknee.topup.common.exception.TopUpException;
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
public class CtsTransactionCleanerExeJob extends BaseCtsTransactionCleanerJob {

  private static final CommonLogger COMMON_LOGGER = CommonLogger.getCommonLogger(log);
  public static final String BPART_EXPIRED = "BPART_EXPIRED";
  public static final String BPART_SADAD_EXPIRED = "BPART_SADAD_EXPIRED";
  public static final String HANGING_TRANSACTIONS = "HANGING_TRANSACTIONS";
  public static final String PENDING_TX_STUCK_TIME_DELAY = "PendingTxStuckTimeDelay";

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    boolean bpartExpired = getBooleanConfig(context, BPART_EXPIRED);
    boolean bpartSadadExpired = getBooleanConfig(context, BPART_SADAD_EXPIRED);
    boolean hangingTransactions = getBooleanConfig(context, HANGING_TRANSACTIONS);

    // bpartExpired transactions task
    if (bpartExpired) {
      try {
        bpartExpiredTask();
      } catch (INException e) {
        e.printStackTrace();
      } catch (ConnectorNoConnectionException e) {
        e.printStackTrace();
      }
    }

    // bpart sadad expired transactions task
    if (bpartSadadExpired) {
      bpartSadadExpiredTask();
    }

    // cleaning transactions in status < 300
    if (hangingTransactions) {
      hangingTransactionsTask();
    }
  }

  private void bpartExpiredTask() throws INException, ConnectorNoConnectionException {
    final String method = COMMON_LOGGER.start("bpartExpiredTask", new ParamList(COMMON_LOGGER));
    try {
      getCtsTransactionCleanerBean().rollbackBPartExpiredTransactions();
    } catch (TopUpException e) {
      COMMON_LOGGER.error(method, "error during bpartExpiredTask", e);
    }
    COMMON_LOGGER.end(method);
  }

  private void bpartSadadExpiredTask() {
    final String method = COMMON_LOGGER.start("bpartSadadExpiredTask", new ParamList(COMMON_LOGGER));
    try {
      getCtsTransactionCleanerBean().rollbackBPartSadadExpiredTransactions();
    } catch (TopUpException e) {
      COMMON_LOGGER.error(method, "error during bpartSadadExpiredTask", e);
    }
    COMMON_LOGGER.end(method);
  }

  private void hangingTransactionsTask() {
    final String method = COMMON_LOGGER.start("hangingTransactionsTask", new ParamList(COMMON_LOGGER));
    try {
      int stuckTimeDelay = (int) getConfig().getNumberConfigParam(PENDING_TX_STUCK_TIME_DELAY);
      getCtsTransactionCleanerBean().cleanOutdatedPendingTransactions(stuckTimeDelay);
    } catch (TopUpException e) {
      COMMON_LOGGER.error(method, "error during hangingTransactionsTask", e);
    }
    COMMON_LOGGER.end(method);
  }
}
