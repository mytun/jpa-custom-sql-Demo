package com.mytun.sql.repository;


import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface StringBaseRepository<T> extends BaseRepository<T, String> {
}
