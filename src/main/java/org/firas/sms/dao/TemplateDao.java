package org.firas.sms.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.sms.model.Template;

public interface TemplateDao extends PagingAndSortingRepository<Template, Integer> {

	public Template findFirstByIdAndStatus(Integer templateId, Integer status);

    public Page<Template> findByAppIdAndStatus(Byte status, Pageable pageable);
    public Iterable<Template> findByAppIdAndStatus(Byte status, Sort sort);

    public Template findFirstByAppIdAndName(Integer appId, String name);
    public Template findFirstByApp_NameAndName(String appName, String name);
}
