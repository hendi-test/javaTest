package com.redknee.subscriber.cts.service;

import com.redknee.subscriber.cts.service.entity.Subscriber;
import java.io.Serializable;
import java.util.List;

/**
 * SubscriberDao interface defines methods for Subscriber entity DB operations.
 */
public interface SubscriberDao extends Serializable {

  /**
   * Persists a subscriber instance.
   *
   * @param subscriber property to be created
   */
  void create(Subscriber subscriber);

  void modify(Subscriber subscriber);

  Subscriber detach(Subscriber subscriber);

  void delete(Subscriber subscriber);

  /**
   * Get all subscribers stored in the DB.
   *
   * @return list of subscribers
   */
  List<Subscriber> findAll();

  /**
   * Get specific subscriber stored in the DB by its sid.
   *
   * @param sid subscriber sid
   * @return selected Subscriber
   */
  Subscriber findBySid(String sid);

  /**
   * Get specific subscriber stored in the DB by its sid.
   *
   * @param msisdn subscriber msisdn
   * @return selected Subscriber
   */
  Subscriber findByMsisdn(String msisdn);

  /**
   * Get specific subscriber stored in the DB by its sid.
   *
   * @param nickName subscriber nickName
   * @return selected Subscriber
   */
  Subscriber findByNickName(String nickName);
}
