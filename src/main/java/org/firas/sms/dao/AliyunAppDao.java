package org.firas.sms.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.sms.model.AliyunApp;
import org.firas.sms.model.App;

public interface AliyunAppDao extends PagingAndSortingRepository<AliyunApp, Integer> {

	public AliyunApp findFirstById(Integer id);
	public AliyunApp findFirstByIdAndStatus(Integer id, Integer status);
}
