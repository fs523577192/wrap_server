package org.firas.sms.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.sms.model.SmsApp;

public interface SmsAppDao extends PagingAndSortingRepository<SmsApp, Integer> {

    public SmsApp findFirstByIdAndStatus(Integer appId, Byte status);

    public SmsApp findFirstByName(String Name);
    public SmsApp findFirstByNameAndStatus(String Name, Byte status);

    public Page<SmsApp> findByStatus(Byte status, Pageable pageable);
    public Iterable<SmsApp> findByStatus(Byte status, Sort sort);
}
