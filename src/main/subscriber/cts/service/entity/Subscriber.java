package com.redknee.subscriber.cts.service.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NamedQueries({
    @NamedQuery(name = Subscriber.FIND_ALL, query = "SELECT s FROM Subscriber s"),
    @NamedQuery(name = Subscriber.FIND_BY_SID, query = "SELECT s FROM Subscriber s "
        + "WHERE s.sid = :sid"),
    @NamedQuery(name = Subscriber.FIND_BY_MSISDN, query = "SELECT s FROM Subscriber s "
        + "WHERE s.msisdn = :msisdn"),
    @NamedQuery(name = Subscriber.FIND_BY_NICKNAME, query = "SELECT s FROM Subscriber s "
        + "WHERE s.nickName = :nickName")})
@Table(name = "SUBSCRIBER")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Subscriber implements Serializable {
  private static final long serialVersionUID = 8999172491780358453L;

  public static final String FIND_ALL = "Subscriber.findAll";
  public static final String FIND_BY_SID = "Subscriber.findBySid";
  public static final String FIND_BY_MSISDN = "Subscriber.findByMsisdn";
  public static final String FIND_BY_NICKNAME = "Subscriber.findByNickName";

  @Id
  @NotNull(message = "{cts.subscriber.sid.notNull}")
  @Column(name = "SID", nullable = false)
  private String sid;

  @NotNull(message = "{cts.subscriber.msisdn.notNull}")
  @Column(name = "MSISDN", nullable = false, length = 32, unique = true)
  @Size(max = 32, message = "{cts.subscriber.msisdn.max}")
  private String msisdn;

  @Column(name = "NICKNAME", length = 50, unique = true)
  @Size(max = 50, message = "{cts.subscriber.nickName.max}")
  private String nickName;

  @Column(name = "PIN")
  private String pin;

  @NotNull(message = "{cts.subscriber.tenantID.notNull}")
  @Column(name = "TENANTID", nullable = false)
  private String tenantID;

  @NotNull(message = "{cts.subscriber.status.notNull}")
  @Column(name = "STATUS", nullable = false)
  private Boolean status;

  @Column(name = "LASTACTIVITYTIME")
  private Date lastActivityTime;

  @NotNull(message = "{cts.subscriber.noFeeAttempts.notNull}")
  @Column(name = "NOFEEATTEMPS", nullable = false)
  private Integer noFeeAttempts;

  @Column(name = "LOGINGATTEMPTSLIMIT")
  private Integer logingAttemptsLimit;

  @Column(name = "LANGUAGECODE", length = 2)
  @Size(max = 2, message = "{cts.subscriber.languageCode.max}")
  private String languageCode;

  @Column(name = "NOTE")
  private String note;
}
