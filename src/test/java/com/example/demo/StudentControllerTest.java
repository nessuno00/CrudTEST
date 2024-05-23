package com.example.demo;

import com.example.demo.Repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @SpringBootTest
    @AutoConfigureMockMvc
    public class StudentControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private StudentRepository studentRepository;

        @Autowired
        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() {
            studentRepository.deleteAll();
        }

        @Test
        void testCreateStudent() throws Exception {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);

            mockMvc.perform(post("/students")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(student)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("John"))
                    .andExpect(jsonPath("$.surname").value("Doe"))
                    .andExpect(jsonPath("$.working").value(true));
        }

        @Test
        void testGetAllStudents() throws Exception {
            Student student1 = new Student();
            student1.setName("John");
            student1.setSurname("Doe");
            student1.setWorking(true);

            Student student2 = new Student();
            student2.setName("Jane");
            student2.setSurname("Doe");
            student2.setWorking(false);

            studentRepository.save(student1);
            studentRepository.save(student2);

            mockMvc.perform(get("/students"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(2))
                    .andExpect(jsonPath("$[0].name").value("John"))
                    .andExpect(jsonPath("$[1].name").value("Jane"));
        }

        @Test
        void testGetStudentById() throws Exception {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentRepository.save(student);

            mockMvc.perform(get("/students/" + savedStudent.getId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("John"))
                    .andExpect(jsonPath("$.surname").value("Doe"));
        }

        @Test
        void testUpdateStudent() throws Exception {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentRepository.save(student);

            savedStudent.setName("Johnny");
            savedStudent.setSurname("Smith");

            mockMvc.perform(put("/students/" + savedStudent.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(savedStudent)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Johnny"))
                    .andExpect(jsonPath("$.surname").value("Smith"));
        }

        @Test
        void testUpdateWorkingStatus() throws Exception {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentRepository.save(student);

            mockMvc.perform(patch("/students/" + savedStudent.getId() + "/working?working=false"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.working").value(false));
        }

        @Test
        void testDeleteStudent() throws Exception {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentRepository.save(student);

            mockMvc.perform(delete("/students/" + savedStudent.getId()))
                    .andExpect(status().isOk());

            mockMvc.perform(get("/students/" + savedStudent.getId()))
                    .andExpect(status().isNotFound());
        }
    }


