# 基于JPA框架根据url数据自动查询数据库数据

## 可以根据URL传递搜索条件自动查询数据库数据

``` java
    @Test
    void getTestSql() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员")
                .param("sort","name$asc")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
```

### 普通查询

``` java
    @Test
    void getTestSimple() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
```

### 多表查询

``` java
    @Test
    void getTestMoreTable() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","role.id$=$'111'")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
```

### 多条件 and

``` java
    @Test
    void getTestManyConditionsAnd() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员,role.id$=$'111',+")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
```

### 多条件 or

``` java
    @Test
    void getTestManyConditionsOr() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员,role.id$=$'111',-")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
```

### 多条件 and 和 or

#### 多条件查询按照后缀表达式生成查询语句
---
  例如： (id='123222' and  (name='xasd' or phone='123456' )) or (name='123' and phone='2222')
  语句则：id$=$'123222',name$=$'xasd',phone$=$'123456',-,+,name$=$'123',phone$=$'2222',+,-

``` java
    @Test
    void getTestManyConditionsAndOr() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员,role.id$=$'111',-,name$=$测试人员,role.id$=$'111',-,+")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
```

### 排序

``` java
    @Test
    void getTestSort() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("sort","name$asc,role.name$desc")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
```
