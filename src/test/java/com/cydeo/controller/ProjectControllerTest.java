package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.RoleDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.Role;
import com.cydeo.enums.Gender;
import com.cydeo.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mvc;

    static ProjectDTO projectDTO;
    static UserDTO userDTO;
    static String token;

    @BeforeAll
    static void setUp() {
        token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJVM3BaeTFmdjV3cHdBd3IyS2FCQVdCelRpYmhZbFNvR2JYQnhsQ3luQ3RVIn0.eyJleHAiOjE3NDE1Mzk2OTUsImlhdCI6MTc0MTUzNzg5NSwianRpIjoiMDc4YTYwYTEtMWU2OS00NDNkLTkxMzItZmMwOTE2MzY0NjkyIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL3JlYWxtcy9jeWRlby1kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZTVjYzAyYjktMWU1ZS00MzAzLThkYmUtZTBjNmE3Y2JjNTczIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoidGlja2V0aW5nLWFwcCIsInNlc3Npb25fc3RhdGUiOiI1YzE3ZDA3Ni00MTU4LTRhMTItOTc1My0xODQwOWIwOGZkN2MiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHA6Ly9sb2NhbGhvc3Q6ODA4MSJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiIsImRlZmF1bHQtcm9sZXMtY3lkZW8tZGV2Il19LCJyZXNvdXJjZV9hY2Nlc3MiOnsidGlja2V0aW5nLWFwcCI6eyJyb2xlcyI6WyJNYW5hZ2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6Im9wZW5pZCBwcm9maWxlIGVtYWlsIiwic2lkIjoiNWMxN2QwNzYtNDE1OC00YTEyLTk3NTMtMTg0MDliMDhmZDdjIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsIm5hbWUiOiJNaWNoZWFsIEpvaG5zb24iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJtaWtlIiwiZ2l2ZW5fbmFtZSI6Ik1pY2hlYWwiLCJmYW1pbHlfbmFtZSI6IkpvaG5zb24iLCJlbWFpbCI6Im1pa2VAbWlrZW1mLmNvbSJ9.hMpiDCW2UTb7jzJH9Z0TQ_KNZWRzSPDTKRsNThzCMCy-0gmUuKs1uud1OSYHCzroSmJ5tzAxw5nbn8K21M9GVrULtU3mQmUzLlgfe29wGW3pdsrpVoLKGINenAdojaV5zy5Lz8iN992WwZMRsx24uZeSvf0OqnC7P-tWBZYXt8tLU0-wnWg4-3qvFGEmaVOuJMRwEuFTfDisG2bk2szHENNULZGe98egEe-3y1wZm8uSnhjCnyUuGACq_uhka6Q-kbXGgFQ0bIOLM-06lGl4HXI7FOJk0fdtxZjY92u9CNI9JcssHgCMrEYX6b2gwTodp1tqMmYHjf_1TpO1KPLU6Q";
        userDTO = UserDTO.builder()
                .id(2L)
                .firstName("ozzy")
                .lastName("ozzy")
                .userName("ozzy")
                .passWord("Abc1")
                .confirmPassWord("Abc1")
                .role(new RoleDTO(2L, "Manager"))
                .gender(Gender.MALE)
                .build();

        projectDTO = ProjectDTO.builder()
                .projectCode("API1")
                .projectName("API-OZZY")
                .assignedManager(userDTO)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(5))
                .projectDetail("API TESTING")
                .projectStatus(Status.OPEN)
                .build();
    }

    @Test
    public void givenNoToken_whenGetRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void givenToken_whenGetRequest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/project")
                        .header("Authorization", token)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].projectCode").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].assignedManager").exists());
    }

    @Test
    public void givenToken_createProject() throws Exception {

        mvc.perform(MockMvcRequestBuilders
                .post("/api/v1/project")
                .header("Authorization", token)
                .content(toJsonString(projectDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    public void givenToken_updateProject() throws Exception {
        projectDTO.setProjectName("NEW NAME");

        mvc.perform(MockMvcRequestBuilders
                .put("/api/v1/project")
                .header("Aithorization", token)
                .accept(MediaType.APPLICATION_JSON)
                .content(toJsonString(projectDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Project Successfully updated."));
    }


    private static String toJsonString(final Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}