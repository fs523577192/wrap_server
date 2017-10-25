package org.firas.sms.dao;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.sms.model.SmsLimit;
import org.firas.sms.model.Template;

public interface SmsLimitDao
        extends PagingAndSortingRepository<SmsLimit, Integer> {

    public SmsLimit findFirstByTemplateAndPeriod(
            Template template, Integer period);

    public List<SmsLimit> findByTemplateOrderByPeriodDesc(
            Template template);

}
