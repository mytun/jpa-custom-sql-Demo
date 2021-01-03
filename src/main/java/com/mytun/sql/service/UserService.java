package com.mytun.sql.service;

import com.mytun.sql.model.User;
import com.mytun.sql.repository.UserRepository;
import com.mytun.sql.util.Pagination;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    UserRepository ur;

    /**
     *
     * @param sort 排序方法
     * @param query 搜索条件
     * @param page 页数
     * @param row 页大小
     * @return 查询后数据
     */
    public Pagination<User> getAll(String sort, String query, int page, int row) {
        return ur.findAll(sort,query,page,row);
    }
}
