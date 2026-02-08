package com.mike.healthmadeeasy.integration;

import com.jayway.jsonpath.JsonPath;
import com.mike.healthmadeeasy.HealthMadeEasyApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;
import tools.jackson.databind.json.JsonMapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
public class MealApiPostgresIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer(DockerImageName.parse(
            "postgres:16-alpine"))
            .withDatabaseName("healthmadeeasy_test")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    JsonMapper objectMapper;

    @Autowired
    JdbcTemplate jdbc;

    @BeforeEach
    void cleanDb() {
        jdbc.execute("TRUNCATE TABLE meal_food, meals, foods CASCADE ");
    }

    @Test
    void createMeal_thenGetById_andRowsAreInDatabase() throws Exception {

        String appleId = createFood("Apple", 52.0, 0.3, 14.0, 0.2);
        String bananaId = createFood("Banana", 89.0, 1.1, 23.0, 0.3);

        Map<String, Object> mealRequest = new LinkedHashMap<>();
        mealRequest.put("name", "Breakfast");

        mealRequest.put("foods", List.of(
                Map.of("foodId", appleId),
                Map.of("foodId", bananaId)
        ));

        String mealJson = objectMapper.writeValueAsString(mealRequest);

        String createdMealBody = mockMvc.perform(post("/api/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mealJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/meals")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("breakfast"))
                .andExpect(jsonPath("$.foods").isArray())
                .andExpect(jsonPath("$.foods[*].foodId", containsInAnyOrder(appleId, bananaId)))
                .andReturn().getResponse().getContentAsString();

        String mealId = JsonPath.read(createdMealBody, "$.id");

        mockMvc.perform(get("/api/meals/{id}", mealId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mealId))
                .andExpect(jsonPath("$.name").value("breakfast"))
                .andExpect(jsonPath("$.foods[*].foodId", containsInAnyOrder(appleId, bananaId)));

        Integer mealsCount = jdbc.queryForObject("SELECT COUNT(*) FROM meals", Integer.class);
        Integer linksCount = jdbc.queryForObject("SELECT COUNT(*) FROM meal_food", Integer.class);

        assertThat(mealsCount).isEqualTo(1);
        assertThat(linksCount).isEqualTo(2);

        UUID mealUuid = UUID.fromString(mealId);

        Integer appleLink = jdbc.queryForObject("SELECT COUNT(*) FROM meal_food WHERE meal_id = ? AND food_id = ?",
                Integer.class, mealUuid, UUID.fromString(appleId));

        Integer bananaLink = jdbc.queryForObject("SELECT COUNT(*) FROM meal_food WHERE meal_id = ? AND food_id = ?",
                Integer.class, mealUuid, UUID.fromString(bananaId));

        assertThat(appleLink).isEqualTo(1);
        assertThat(bananaLink).isEqualTo(1);
    }

    @Test
    void create_duplicateName_throws_duplicateNameException() throws Exception {

        String appleId = createFood("Apple", 52.0, 0.3, 14.0, 0.2);
        String bananaId = createFood("Banana", 89.0, 1.1, 23.0, 0.3);

        Map<String, Object> mealRequest1 = new LinkedHashMap<>();
        mealRequest1.put("name", "Breakfast");

        mealRequest1.put("foods", List.of(
                Map.of("foodId", appleId),
                Map.of("foodId", bananaId)
        ));

        String mealJson1 = objectMapper.writeValueAsString(mealRequest1);

        mockMvc.perform(post("/api/meals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mealJson1))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", containsString("/api/meals")))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("breakfast"))
                .andExpect(jsonPath("$.foods").isArray())
                .andExpect(jsonPath("$.foods[*].foodId", containsInAnyOrder(appleId, bananaId)))
                .andReturn().getResponse().getContentAsString();

        mealRequest1.put("name", "    BREAKFAST    ");
        String mealJson2 = objectMapper.writeValueAsString(mealRequest1);

        mockMvc.perform(post("/api/meals").contentType(MediaType.APPLICATION_JSON)
                .content(mealJson2))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.detail", containsString("breakfast")));

        mockMvc.perform(get("/api/meals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
        
    }


    private String createFood(String name, double calories, double protein, double carbs, double fat) throws Exception {

        Map<String, Object> request = new LinkedHashMap<>();
        request.put("name", name);
        request.put("referenceGrams", 100);
        request.put("calories", calories);
        request.put("protein", protein);
        request.put("carbs", carbs);
        request.put("fat", fat);

        String json = objectMapper.writeValueAsString(request);

        String body = mockMvc.perform(post("/api/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        return JsonPath.read(body, "$.id");
    }

}
