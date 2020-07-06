package com.redknee.topup.cts.common;

import com.redknee.topup.legacy.cts.scap.at.siemens.diameter.msg.DiameterMessage;
import lombok.Getter;

/**
 *  Central location for the definition of constants and enums used in Cts.
 *  Adapted from legacy at.siemens.nexus.p2p.common.Const
 *  Kindly note that the rest of class method and properties should be
 *  completely implemented in OPE-106, OPE-107, Epic OPE-105
 */
@SuppressWarnings({"checkstyle:RedundantModifier", "checkstyle:SummaryJavadoc",
    "checkstyle:AbbreviationAsWordInName", "checkstyle:MethodParamPad", "checkstyle:SingleSpaceSeparator",
    "PMD:UnnecessaryModifier", "checkstyle:EmptyLineSeparator"})
public final class Const { //NOPMD    // suppress PMD warning because this class is from legacy code
  /** sms-connector app name */
  public static final String SMS_CONNECTOR_APP_NAME = "P2P";
  //  General interface for all states with code

  /**
   * PartKey constants for p2p_recharge_evidences db table.
   */
  public enum RePartKey {
    ACTIVE  (0),
    ARCHIVE (10);

    private int code;
    private RePartKey(int code) {
      this.code = code;
    }

    public int intValue() {
      return code;
    }
    public Integer integerValue() {
      return Integer.valueOf(code);
    }
  }

  public enum Channel {
    T("STK"),
    W("Web"),
    I("IFS"),
    U("USSD"),
    S("SMS"),
    E("EAI"),
    B("SADAD");

    @Getter
    private String name;

    Channel(String name) {
      this.name = name;
    }

    public boolean isConnected() {
      return true;
    }
  }

  /**
   * General interface for all states with code
   */
  public interface StatusCode {
    /** Returns code */
    public int intValue();
    /** Returns code as Integer */
    public Integer integerValue();
  }

  /**
   * General interface for all states with code
   */
  public interface SMSSendable {
    /** Returns code */
    public int intValue();
    /** Returns configuration id (beginning of file configuration key) */
    public String getCfgId();
  }

  // Common interface for all states per sbt phase
  public interface ReStatusPerPhase extends StatusCode {
  }

  // Transaction (recharge evidence) states of process phase.
  public enum ReStatusProcess implements ReStatusPerPhase, SMSSendable {

    INITIAL(0),
    SUCCESS(1),
    IN_PROGRESS(2),

    ERR_REC_POSTPAID(11), // Recipient is Postpaid
    // Donor Balance after the transaction will be less than the service class
    // or dedicated or global minimum balance configured
    ERR_DON_BAL_BELOW_MIN(18),
    ERR_DON_NOT_ACTIVE(20), // Donor is not in Active state in network
    // Donor has not consumed the dedicated account balance to the configured extent,
    // and is making Main account transaction
    ERR_DA_BAL_RULE_VIOLATED(23),
    ERR_DON_BLOCKED_TEMP(24), // Donor is temporarily blocked in the network
    ERR_DON_BLOCKED_FULL(25), // Donor is full blocked in the network
    ERR_DON_BLOCKED_OG(26), // Donor is OG blocked in the network
    ERR_DON_LINE_EXPIRED(27), // Donor line is expired
    ERR_REC_NOT_ACTIVE(40), // Recipient is not in Active state in network
    ERR_REC_BLOCKED_TEMP(42), // Recipient is temporarily blocked in the network
    ERR_REC_BLOCKED_FULL(43), // Recipient is full blocked in the network
    ERR_REC_LINE_EXPIRED(44), // Recipient line is expired
    ERR_RETRIEVAL_DON(50), // CT fails to query Donor details
    ERR_RESERVE_DONOR_MA(52), // CT fails to reserve units on donor main account
    // CT fails to reserve fee units on donor/recipient main account (main, dedicated account transaction)
    ERR_RESERVE_FEE(53),
    ERR_RESERVE_DONOR_DA(54), // CT fails to reserve units on donor dedicated account
    ERR_TRANSFER_CREDIT_MA(55), // CT fails to transfer credit to recipient (main account transaction)
    // CT fails to transfer credit to recipient (dedicated account transaction)
    ERR_TRANSFER_CREDIT_DA(56),
    // CT fails to commit units on donor main account (main account transaction)
    ERR_COMMIT_DONOR_MA(57),
    // CT fails to commit fee units on donor/recipient main account (main, dedicated account transaction)
    ERR_COMMIT_FEE(58),
    ERR_COMMIT_DONOR_DA(59), // CT fails to commit units on donor dedicated account
    ERR_ROLLBACK_RECIPIENT_MA(60), // CT rollback failure for recipient main account
    ERR_ROLLBACK_RECIPIENT_DA(61), // CT rollback failure for recipient dedicated account
    // CT rollback failure on donor main account (refund amount units for main or dedicated account)
    ERR_ROLLBACK_DONOR(62),
    ERR_ROLLBACK_DONOR_RECIPIENT(63), // CT rollback failure in both recipient and donor
    ERR_SADAD_VOMS_FAIL(70), // SADAD channel: ePin could not be generated / VoMS failure
    // SADAD channel: ePin could not be generated / VoMS failure: Illegal argument
    // (rechargeValue < 0 or rechargeValue > max)
    ERR_SADAD_VOMS_EXC(71),
    ERR_INVALID_PIN(203),
    ERR_AMOUNT_BELOW_MIN(
        210), // Check failure because  transaction amount is lower than subscriber's minimum transaction limit
    ERR_AMOUNT_ABOVE_MAX(
        211), // Check failure because transaction amount is greater than subscriber's maximum transaction limit
    ERR_SAME_DON_REC(221),
    ERR_AMOUNT_STEP(305), // Amount is not a multiple of configured step
    ERR_DON_NAT_ID_INVALID  (311), // Donor Customer (National / Iqama) ID is incorrect
    ERR_REC_NAT_ID_INVALID  (312), // Recipient Customer (National / Iqama) ID is incorrect

    /* B Part */
    ERR_INCORRECT_TOKEN             (313), //  Token received in SMS is not matching the one in DB
    ERR_INCORRECT_NAT_ID_ATT_LEFT   (314), //  NationalId id wrong, more attempts left
    ERR_ROLLBACK_OK                 (315), //  NationalId wrong, no retries available, rollback OK, for SADAD voucher should be generated
    ERR_CLEANER_OK                  (316), //  Awaiting transaction cleaner: Transaction awaiting for B-part flow expired / transaction is rolled back
    ERR_ROLLBACK_FAILED             (319), //  Max wrong nationalId attempts reached, rollback activated,and rollback failed, pending for another rollback
    ERR_CLEANER_NOK                 (320), //  Awaiting transaction cleaner: Transaction awaiting for B-part flow expired / transaction rollback failed
    ERR_REFILL_ROLLBACK_OK          (321), //  Refill failed, rollback started -> rollback OK
    ERR_REFILL_ROLLBACK_FAILED      (322), //  Provided information from B party is Ok, refill should be performed, but refill failed for some reason, rollback activated, and rollback failed, pending for another rollback
    ERR_INVALID_CFG_REF_PROF_ID     (323), //  SADAD channel, B-party flow: No refillProfileID for amount configured


    // error codes not taken from eReffil (600 - 699)
    ERR_GENERAL(600); // General error

    private int code;

    ReStatusProcess(int code) {
      this.code = code;
    }

    @Override
    public int intValue() {
      return code;
    }

    @Override
    public String getCfgId() {
      return "smsMsgStp";
    }

    @Override
    public Integer integerValue() {
      return Integer.valueOf(code);
    }
  }

  /**
   * Transaction (recharge evidence) states of check phase.
   */
  public enum ReStatusCheck implements ReStatusPerPhase, SMSSendable {

    INITIAL(0),
    SUCCESS(1),
    IN_PROGRESS(2),

    PARTB_WRONG_ONE_TOKEN_ERR(11),
    PARTB_WRONG_TOKEN_ERR(12),
    PARTB_NO_PENDING_TR_ERR(13),
    PARTB_ROLLBACK_FAILED_ERR(14),
    PARTB_REFILL_FAILED_ERR(15),
    PARTB_WRONG_NAT_ID_ERR(16),
    PARTB_WRONG_NAT_ID_MAX_ATT_ERR(17),
    PARTB_NO_TOKEN_TR_ERR(18),
    PARTB_GENERAL_NX_ERR(19),
    PARTB_SADAD_REFILL_FAILED_ERR(20),

    ERR_MISSING_PIN(202), // Check failure because PIN is Required
    ERR_INVALID_PIN(203), // Check failure because of Invalid Pin
    ERR_DA_EXPIRED(206), // Check failure because service had expired
    ERR_LIMIT_DON_DAY_AMT(207), // Check failure because donors daily transaction limit is not
    // sufficient
    ERR_LIMIT_DON_WEEK_AMT(208), // Check failure because donors weekly transaction limit is not
    // sufficient
    ERR_LIMIT_DON_MONTH_AMT(209), // Check failure because donors monthly transaction limit is not
    // sufficient
    ERR_AMOUNT_BELOW_MIN(
        210), // Check failure because  transaction amount is lower than subscriber's minimum
    // transaction limit
    ERR_AMOUNT_ABOVE_MAX(
        211), // Check failure because transaction amount is greater than subscriber's maximum
    // transaction limit
    ERR_LIMIT_DON_DAY_QTY(
        212), // Check failure because number of transactions of donor will exceed maximum number of
    // transactions per day
    ERR_LIMIT_DON_WEEK_QTY(
        213), // Check failure because number of transactions of donor will exceed maximum number of
    // transactions per week
    ERR_LIMIT_DON_MONTH_QTY(
        214), // Check failure because number of transactions of donor will exceed maximum number of
    // transactions per month
    ERR_LIMIT_REC_DAY_AMT(
        215), // Check failure because recipient daily credit received limit is not sufficient
    ERR_LIMIT_REC_WEEK_AMT(
        216), // Check failure because recipient weekly credit received limit is not sufficient
    ERR_LIMIT_REC_MONTH_AMT(
        217), // Check failure because recipient monthly credit received limit is not sufficient
    ERR_LIMIT_REC_DAY_QTY(
        218), // Check failure because number of transactions of recipient will exceed maximum number of
    // credit receive transactions per day
    ERR_LIMIT_REC_WEEK_QTY(
        219), // Check failure because number of transactions of recipient will exceed maximum number of
    // credit receive transactions per week
    ERR_LIMIT_REC_MONTH_QTY(
        220), // Check failure because number of transactions of recipient will exceed maximum number of
    // credit receive transactions per month
    ERR_SAME_DON_REC(221), // Donor and Recipient are same
    ERR_BLACKLISTED_DON(222), // Donor is in blacklist
    ERR_BLACKLISTED_REC(223), // Recipient is in blacklist
    ERR_INVALID_EAI_REQUEST(224), // Invalid EAI Request
    ERR_DECIMAL_AMOUNT(225), // Amount is in decimal
    ERR_UNFINISHED_TX(228), // Transaction already exist and pending
    ERR_GENERAL(293), // General error
    ERR_NICKNAME_NOT_EXIST(302), // Nickname does not exist
    ERR_AMOUNT_STEP(305), // Amount is not a multiple of configured step
    ERR_DON_SC_BLACKLISTED(306), // Donor service class blacklisted
    ERR_REC_SC_BLACKLISTED(307), // Recipient service class blacklisted
    ERR_DON_GRACE_PERIOD_VIOLATED(308), // Donor grace period violated
    ERR_RETRIEVAL_REC(309), // Recipient Number not found --> moved from stP 51
    ERR_BUSINESS_PKG(310), // Transfer from business line package to non-business line package
    ERR_DON_NAT_ID_INVALID(311), // Donor Customer (National / Iqama) ID is incorrect
    ERR_REC_NAT_ID_INVALID(312), // Recipient Customer (National / Iqama) ID is incorrect

    /* B Part */
    ERR_INCORRECT_TOKEN(313), //  Token received in SMS is not matching the one in DB
    ERR_INCORRECT_NAT_ID_ATT_LEFT(314), //  NationalId id wrong, more attempts left
    ERR_ROLLBACK_OK(
        315), //  NationalId wrong, no retries available, rollback OK, for SADAD voucher should be
    // generated
    ERR_CLEANER_OK(
        316), //  Awaiting transaction cleaner: Transaction awaiting for B-part flow expired
    // transaction is rolled back
    ERR_ROLLBACK_FAILED(
        319), //  Max wrong nationalId attempts reached, rollback activated,and rollback failed, pending
    // for another rollback
    ERR_CLEANER_NOK(
        320), //  Awaiting transaction cleaner: Transaction awaiting for B-part flow expired
    // transaction rollback failed
    ERR_REFILL_ROLLBACK_OK(321), //  Refill failed, rollback started -> rollback OK
    ERR_REFILL_ROLLBACK_FAILED(
        322), //  Provided information from B party is Ok, refill should be performed, but refill
    // failed for some reason, rollback activated, and rollback failed, pending for another rollback
    ERR_INVALID_CFG_REF_PROF_ID(
        323), //  SADAD channel, B-party flow: No refillProfileID for amount configured

    // error codes not taken from eRefill, but saved in DB (500 - 569)
    ERR_INVALID_KW_MAPPING(500), //  Invalid keyword mapping
    ERR_DON_DA_NOT_EXIST(501), //  Donor dedicated account does not exist
    ERR_REC_DA_NOT_EXIST(502), //  Recipient dedicated account does not exist

    // error codes not taken from eRefill, but not saved in DB (570 - 599)
    ERR_TRANSACTION_NOT_FOUND(571); // Transaction not found for subscriber or invalid msisdn format

    private int code;

    ReStatusCheck(int code) {
      this.code = code;
    }

    @Override
    public int intValue() {
      return code;
    }

    @Override
    public Integer integerValue() {
      return code;
    }

    @Override
    public String getCfgId() {
      return "smsMsgStc";
    }

  }

  public enum ReStatus implements StatusCode {
    // ACTIVE STATES - transaction is in processing
    INITIAL(0),
    IN_PROGRESS(100),

    // PENDING STATES - transaction is 'sleeping'
    PENDING(300),
    PENDING_B_PART_WORKFLOW(310),
    PENDING_ROLLBACK(330),

    // FINISHED STATES - transaction is ready for archiving and exporting (CAUTION: all transactions with status >= 700 will be archived&exported)
    FINISHED_SUCCESS(700),
    FINISHED_ERROR(999);


    private int code;

    private ReStatus(int code) {
      this.code = code;
    }

    @Override
    public int intValue() {
      return code;
    }

    @Override
    public Integer integerValue() {
      return Integer.valueOf(code);
    }
  }

  public enum PerfCounterName {
    PERF_SBT_BPART("cts.perf.sbt.bpart"),             // transactions ended up in B-part flow
    PERF_SBT_SADAD("cts.perf.sbt.sadad"),             // will be covered with transactions per channel
    PERF_SBT_CHANNEL_basename("cts.perf.sbt.channel"),           // transactions per channel
    PERF_SBT_UNSUCCESSFUL("cts.perf.sbt.unsuccessful"),      // total number of unsuccessful transactions
    PERF_SBT_BLACKLIST(
        "cts.perf.sbt.blacklist"),         // transactions ended up because of blacklist (don, rec, sc)

    PERF_UCIP_TOTAL("cts.perf.ucip.total"),            // UCIP total and UCIP per action below
    PERF_UCIP_REFILL("cts.perf.ucip.refill"),
    PERF_UCIP_UPDATE("cts.perf.ucip.update"),
    PERF_UCIP_CANCEL("cts.perf.ucip.cancel"),
    PERF_UCIP_GETBALANCEDATE("cts.perf.ucip.getBalanceDate"),
    PERF_UCIP_GETACCDETAILS("cts.perf.ucip.getAccountDetails"),

    PERF_SCAP_TOTAL("cts.perf.scap.total"),            // SCAP total and SCAP per action below
    PERF_SCAP_DEBIT("cts.perf.scap.debit"),
    PERF_SCAP_REFUND("cts.perf.scap.refund"),
    PERF_SCAP_RESERVE("cts.perf.scap.reserve"),
    PERF_SCAP_COMMIT("cts.perf.scap.commit"),

    PERF_SOAP_EPIN("cts.perf.soap.epin"),
    PERF_SOAP_TRANSFER("cts.perf.soap.creditTransfer"),
    PERF_SOAP_ADMIN("cts.perf.soap.subscriberAdmin"),

    PERF_SUBS_PROVISION("cts.perf.subs.provision"),

    PERF_VOMS_EVOUCHER("cts.perf.voms.evoucher"),

    PERF_SMS_OUT("cts.perf.sms.out"),
    PERF_SMS_OUT_APARTY("cts.perf.sms.outToAparty"),
    PERF_SMS_OUT_BPARTY("cts.perf.sms.outToBparty"),

    PERF_SMS_IN("cts.perf.sms.in");

    private String counterName;

    private PerfCounterName(String counterName) {
      this.counterName = counterName;
    }

    public String getName() {
      return counterName;
    }
  }

  /**
   * Transaction (recharge evidence) states of notify phase.
   */
  public enum ReStatusNotify implements ReStatusPerPhase {

    INITIAL     (0),
    SUCCESS     (1),
    IN_PROGRESS (2),
    ERROR       (3);


    private int code;
    private ReStatusNotify(int code) {
      this.code = code;
    }

    @Override
    public int intValue() {
      return code;
    }
    @Override
    public Integer integerValue() {
      return Integer.valueOf(code);
    }
  }

  /**
   * Transaction states of Epin resend special CDRs (saved to DB -> logging table -> logData field).
   */
  public enum StatusEpinResend implements StatusCode, SMSSendable {

    SUCCESS               (1),
    NOTIF_FAIL            (3),
    PARTIAL_NOTIF_FAIL    (4),
    ERR_REQUEST           (5),
    ERR_TID               (6),
    ERR_EPIN              (7);


    private int code;
    private StatusEpinResend (int code) {
      this.code = code;
    }

    @Override
    public int intValue() {
      return code;
    }
    @Override
    public Integer integerValue() {
      return code;
    }
    @Override
    public String getCfgId() {
      return "smsMsgResendEpin";
    }
  }

  // Default hidden constructor
  private Const() {
  }
}
