package com.mytun.sql.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @Column(name = "id", unique = true, nullable = false, length = 45,updatable=false)
    @GeneratedValue(generator = "uuid")   //指定生成器名称
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")  //生成器名称，uuid生成类
    private String id;
    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role",fetch=FetchType.EAGER)
    private List<User> userList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
