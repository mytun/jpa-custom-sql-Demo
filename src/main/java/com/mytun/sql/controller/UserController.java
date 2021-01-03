package com.mytun.sql.controller;


import com.mytun.sql.model.User;
import com.mytun.sql.service.UserService;
import com.mytun.sql.util.Pagination;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService us;

    /**
     * 搜索
     * @param sort 排序方法
     * @param query 搜索条件
     * @param page 页
     * @param row 页大小
     * @return 搜索后结果
     */
    @GetMapping("/search")
    public Pagination<User> search(
            @RequestParam(required = false,defaultValue = "")String sort,
            @RequestParam(required = false,defaultValue = "")String query,
            @RequestParam(required = false,defaultValue = "1") int page,
            @RequestParam(required = false,defaultValue = "10") int row){
        return us.getAll(sort,query,page,row);

    }

}
