package org.firas.sms.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.sms.model.AliyunTemplate;
import org.firas.sms.model.Template;

public interface AliyunTemplateDao extends PagingAndSortingRepository<AliyunTemplate, Integer> {

	public AliyunTemplate findFirstByTemplateId(Integer templateId);
	public AliyunTemplate findFirstByTemplateIdAndStatus(Integer templateId, Integer status);
}
