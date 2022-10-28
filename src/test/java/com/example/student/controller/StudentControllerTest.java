package com.example.student.controller;

import com.example.student.dto.StudentDto;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {
    @InjectMocks
    private StudentController studentController;
    @Mock
    private StudentService studentService;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    @BeforeEach
    void setup() throws Exception{
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }
    @Test
    void should_return_student_when_get_student_by_id() throws Exception{
        var studentDto = buildStudentDto();
        Mockito.when(studentService.getStudentById(1L)).thenReturn(studentDto);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Zineddine")));
    }

    @Test
    void should_return_student_when_create_student() throws Exception {
        var student = buildStudent();
        var studentDto = buildStudentDto();
        var content = toJson(student);
        Mockito.when(studentService.createStudent(student)).thenReturn(studentDto);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentId", Matchers.is(1)));
    }

    private String toJson(Student student) throws JsonProcessingException {
        return objectMapper.writeValueAsString(student);
    }

    private Student buildStudent() {
        return Student.builder()
                .studentId(1L)
                .build();
    }

    private StudentDto buildStudentDto() {
        return StudentDto.builder()
                .studentId(1L)
                .firstName("Zineddine")
                .build();
    }
}