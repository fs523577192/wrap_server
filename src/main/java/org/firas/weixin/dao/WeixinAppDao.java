package org.firas.weixin.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.weixin.model.WeixinApp;

public interface WeixinAppDao
        extends PagingAndSortingRepository<WeixinApp, Integer> {

    public WeixinApp findFirstById(Integer appId);
    public WeixinApp findFirstByIdAndStatus(Integer appId, Byte status);

    public WeixinApp findFirstByName(String Name);
    public WeixinApp findFirstByNameAndStatus(String Name, Byte status);

    public Page<WeixinApp> findByStatus(Byte status, Pageable pageable);
    public Iterable<WeixinApp> findByStatus(Byte status, Sort sort);
}
