package com.clocked.worktimecalculator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesRegex;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
  @Autowired private MockMvc mockMvc;

  @Autowired ObjectMapper objectMapper;

  @Test
  void shouldReturnDefaultMessage() throws Exception {
    this.mockMvc
        .perform(get("/"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Work Time Calculator")))
        .andExpect(
            jsonPath("$.version", matchesRegex(Pattern.compile("v(\\d+\\.)(\\d+\\.)(\\*|\\d+)"))));
  }

  @Test
  void shouldCalculateAEmptyDay() throws Exception {
    String content = "{\"date\": \"2023-01-01\", \"registeredRecords\": [], \"shiftRecords\": []}";
    this.mockMvc
        .perform(post("/calculations/day").contentType(MediaType.APPLICATION_JSON).content(content))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].type", is("WORK")))
        .andExpect(jsonPath("$[0].value", is(0.0)))
        .andExpect(jsonPath("$[1].type", is("ABSENT")))
        .andExpect(jsonPath("$[1].value", is(0.0)));
  }

  @Test
  void shouldCalculateADay() throws Exception {
    String content =
        "{\"date\": \"2023-01-01\", \"registeredRecords\": [\"2023-01-01 09:00\", \"2023-01-01 17:00\"], \"shiftRecords\": [\"2023-01-01 09:00\", \"2023-01-01 18:00\"]}";
    this.mockMvc
        .perform(post("/calculations/day").contentType(MediaType.APPLICATION_JSON).content(content))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].type", is("WORK")))
        .andExpect(jsonPath("$[0].value", is(8.0)))
        .andExpect(jsonPath("$[1].type", is("ABSENT")))
        .andExpect(jsonPath("$[1].value", is(1.0)));
  }

  @Test
  void shouldReturnBadRequestCalculateADay() throws Exception {
    String content =
        "{\"date\": \"2023-01-01\", \"registeredRecords\": [\"2023-01-01 09:00a\"], \"shiftRecords\": []}";
    this.mockMvc
        .perform(post("/calculations/day").contentType(MediaType.APPLICATION_JSON).content(content))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenMissingParamsCalculateADay() throws Exception {
    String content = "{}";
    this.mockMvc
        .perform(post("/calculations/day").contentType(MediaType.APPLICATION_JSON).content(content))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.date", is("Date must be informed")))
        .andExpect(jsonPath("$.registeredRecords", is("Registered records must be informed")))
        .andExpect(jsonPath("$.shiftRecords", is("Shift records must be informed")));
  }

  @Test
  void shouldPermitAllowedOrigins() throws Exception {
    this.mockMvc
        .perform(
            options("/")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://localhost:8080"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  void shouldBlockNotAllowedOrigins() throws Exception {
    this.mockMvc
        .perform(
            options("/")
                .header("Access-Control-Request-Method", "GET")
                .header("Origin", "http://www.someurl.com"))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
