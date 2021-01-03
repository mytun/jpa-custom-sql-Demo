package com.mytun.sql;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.MOCK,classes = SqlApplication.class)
@AutoConfigureMockMvc
class SqlApplicationTests {

    @Resource
    private MockMvc mockMvc;

    /**
     * 查询
     * @throws Exception
     */
    @Test
    void getTestSql() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员")
                .param("sort","name$asc")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    /**
     * 普通查询
     * @throws Exception
     */
    @Test
    void getTestSimple() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    /**
     * 多表查询
     * @throws Exception
     */
    @Test
    void getTestMoreTable() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","role.id$=$'111'")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }
    /**
     * 多条件 and
     * @throws Exception
     */
    @Test
    void getTestManyConditionsAnd() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员,role.id$=$'111',+")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    /**
     * 多条件 or
     * @throws Exception
     */
    @Test
    void getTestManyConditionsOr() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员,role.id$=$'111',-")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    /**
     * 多条件 and 和 or
     * @throws Exception
     */
    @Test
    void getTestManyConditionsAndOr() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("query","name$=$测试人员,role.id$=$'111',-,name$=$测试人员,role.id$=$'111',-,+")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }

    /**
     * 排序
     * @throws Exception
     */
    @Test
    void getTestSort() throws Exception {
        MvcResult mvcResult =  mockMvc.perform(MockMvcRequestBuilders.get("/user/search")
                .param("sort","name$asc,role.name$desc")).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8));
    }



}
