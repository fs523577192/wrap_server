package org.firas.sms.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.sms.model.App;

public interface AppDao extends PagingAndSortingRepository<App, Integer> {

    public App findFirstByIdAndStatus(Integer appId, Byte status);

    public App findFirstByName(String Name);
    public App findFirstByNameAndStatus(String Name, Byte status);

    public Page<App> findByStatus(Byte status, Pageable pageable);
    public Iterable<App> findByStatus(Byte status, Sort sort);
}
