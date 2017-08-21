package org.firas.common.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.firas.common.model.LogModel;

@NoRepositoryBean
public interface LogDao<T extends LogModel>
        extends JpaRepository<T, Integer> {

    public Page<T> findByStatusOrderByIdDesc(
            Byte status, Pageable pageable);
    public Iterable<T> findByStatusOrderByIdDesc(
            Byte status, Sort sort);

    public Page<T> findByModelIdAndStatusOrderByIdDesc(
            Integer modelId, Byte status, Pageable pageable);
    public Iterable<T> findByModelIdAndStatusOrderByIdDesc(
            Integer modelId, Byte status, Sort sort);
}

