package com.mike.healthmadeeasy.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.mike.healthmadeeasy.HealthMadeEasyApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Testcontainers
@SpringBootTest(classes = HealthMadeEasyApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {
        "spring.flyway.enabled=true",
        "spring.jpa.hibernate.ddl-auto=validate",
        "spring.jpa.open-in-view=false"
})
@AutoConfigureMockMvc
@ActiveProfiles("postgres")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FoodApiPostgresIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName
            .parse("postgres:16-alpine"))
            .withDatabaseName("healthmadeeasy_test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonMapper objectMapper;

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    ApplicationContext context;

    @BeforeEach
    void cleanDb() {
        jdbc.execute("TRUNCATE TABLE foods");
    }

    @Test
    void createFood_thenGetById_andRowIsInDatabase() throws Exception {

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("name", "Chicken Breast");
        request.put("referenceGrams", 100);
        request.put("calories", 165.0);
        request.put("protein", 31.0);
        request.put("carbs", 0.0);
        request.put("fat", 3.6);

        String json = objectMapper.writeValueAsString(request);

        String body = mockMvc.perform(post("/api/foods").contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/api/foods/")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("Chicken Breast"))
                .andReturn().getResponse().getContentAsString();

        String id = JsonPath.read(body, "$.id");

        mockMvc.perform(get("/api/foods/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Chicken Breast"));

        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM foods", Integer.class);
        assertThat(count).isEqualTo(1);
    }


}
