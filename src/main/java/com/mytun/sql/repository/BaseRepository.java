package com.mytun.sql.repository;

import com.mytun.sql.repository.ext.MytunSpecification;
import com.mytun.sql.util.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T,ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    default Pagination<T> findAll(String sort, String query, int page, int pagesize){
        MytunSpecification<T> f = new MytunSpecification<>(sort,query);
        Pagination<T> pp =new Pagination<>(page,pagesize);
        Page<T> _pp = findAll(Specification.where(f), PageRequest.of(page-1,pagesize));
        pp.setRows(_pp.getContent());
        pp.setTotal(_pp.getTotalElements());
        return pp;
    }

    default Pagination<T> findAll(Specification<T> spec, int page, int pagesize){
        Pagination<T> pp =new Pagination<>(page,pagesize);
        Page<T> _pp = findAll(spec, PageRequest.of(page-1,pagesize));
        pp.setRows(_pp.getContent());
        pp.setTotal(_pp.getTotalElements());
        return pp;
    }

    default Pagination<T> findAll(int page,int pagesize){
        Pagination<T> pp =new Pagination<>(page,pagesize);
        Page<T> _pp = findAll(PageRequest.of(page-1,pagesize));
        pp.setRows(_pp.getContent());
        pp.setTotal(_pp.getTotalElements());
        return pp;
    }

    default List<T> findAll(String sort, String query){
        MytunSpecification<T> f = new MytunSpecification<>(sort,query);
        return findAll(Specification.where(f));
    }

}
