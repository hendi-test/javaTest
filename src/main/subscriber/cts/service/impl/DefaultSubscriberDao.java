package com.redknee.subscriber.cts.service.impl;

import com.redknee.subscriber.cts.service.SubscriberDao;
import com.redknee.subscriber.cts.service.entity.Subscriber;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DefaultSubscriberDao implements SubscriberDao {
  private static final long serialVersionUID = 994570568951877084L;

  private static final String SID = "sid";
  private static final String MSISDN = "msisdn";
  private static final String NICKNAME = "nickName";

  @PersistenceContext(unitName = "topbase-pu")
  private transient EntityManager em;

  public void setEntityManager(EntityManager entityManager) {
    this.em = entityManager;
  }

  @Override
  @Transactional
  public void create(Subscriber subscriber) {
    em.persist(subscriber);
    em.flush();
  }

  /**
   * This only works if subscriber is already in a persistence context(in a transaction context).
   */
  @Override
  @Transactional
  public void modify(Subscriber subscriber) {
    em.merge(subscriber);
    em.flush();
  }

  @Override
  public Subscriber detach(Subscriber subscriber) {
    em.detach(subscriber);
    return subscriber;
  }

  @Override
  @Transactional
  public void delete(Subscriber subscriber) {
    Subscriber subsToBeDeleted = em.getReference(Subscriber.class, subscriber.getSid());
    em.remove(subsToBeDeleted);
  }

  @Override
  @Transactional
  public List<Subscriber> findAll() {
    return em.createNamedQuery(Subscriber.FIND_ALL, Subscriber.class).getResultList();
  }

  @Override
  @Transactional
  public Subscriber findBySid(String sid) {
    return Optional.ofNullable(em.createNamedQuery(Subscriber.FIND_BY_SID, Subscriber.class)
        .setParameter(SID, sid)
        .getSingleResult()).orElse(null);
  }

  @Override
  @Transactional
  public Subscriber findByMsisdn(String msisdn) {
    return Optional.ofNullable(em.createNamedQuery(Subscriber.FIND_BY_MSISDN, Subscriber.class)
        .setParameter(MSISDN, msisdn)
        .getSingleResult()).orElse(null);
  }

  @Override
  @Transactional
  public Subscriber findByNickName(String nickName) {
    return Optional.ofNullable(em.createNamedQuery(Subscriber.FIND_BY_NICKNAME, Subscriber.class)
        .setParameter(NICKNAME, nickName)
        .getSingleResult()).orElse(null);
  }
}
