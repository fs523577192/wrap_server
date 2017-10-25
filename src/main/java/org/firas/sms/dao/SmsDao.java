package org.firas.sms.dao;

import java.util.List;
import java.util.Date;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.sms.model.Sms;
import org.firas.sms.model.Template;

public interface SmsDao extends PagingAndSortingRepository<Sms, Integer> {

    public Page<Sms> findByTemplate_IdOrderByIdDesc(
            Integer templateId, Pageable pageable);
    public Iterable<Sms> findByTemplate_IdOrderByIdDesc(
            Integer templateId, Sort sort);

    public List<Sms> findByTemplateAndMobileAndCreateTimeGreaterThanOrderByIdDesc(
            Template template, String mobile, Date time);

}
