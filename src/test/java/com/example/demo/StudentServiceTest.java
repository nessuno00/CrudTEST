package com.example.demo;

import com.example.demo.Repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

    @SpringBootTest
    public class StudentServiceTest {

        @Autowired
        private StudentService studentService;

        @Autowired
        private StudentRepository studentRepository;

        @BeforeEach
        void setUp() {
            studentRepository.deleteAll();
        }

        @Test
        void testSaveStudent() {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);

            Student savedStudent = studentService.saveStudent(student);

            assertNotNull(savedStudent.getId());
            assertEquals("John", savedStudent.getName());
            assertEquals("Doe", savedStudent.getSurname());
            assertTrue(savedStudent.isWorking());
        }

        @Test
        void testGetAllStudents() {
            Student student1 = new Student();
            student1.setName("John");
            student1.setSurname("Doe");
            student1.setWorking(true);

            Student student2 = new Student();
            student2.setName("Jane");
            student2.setSurname("Doe");
            student2.setWorking(false);

            studentService.saveStudent(student1);
            studentService.saveStudent(student2);

            assertEquals(2, studentService.getAllStudents().size());
        }

        @Test
        void testGetStudentById() {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentService.saveStudent(student);

            Optional<Student> foundStudent = studentService.getStudentById(savedStudent.getId());

            assertTrue(foundStudent.isPresent());
            assertEquals("John", foundStudent.get().getName());
            assertEquals("Doe", foundStudent.get().getSurname());
        }

        @Test
        void testUpdateStudent() {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentService.saveStudent(student);

            savedStudent.setName("Johnny");
            savedStudent.setSurname("Smith");
            Student updatedStudent = studentService.updateStudent(savedStudent.getId(), savedStudent);

            assertEquals("Johnny", updatedStudent.getName());
            assertEquals("Smith", updatedStudent.getSurname());
        }

        @Test
        void testUpdateWorkingStatus() {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentService.saveStudent(student);

            Student updatedStudent = studentService.updateWorkingStatus(savedStudent.getId(), false);

            assertFalse(updatedStudent.isWorking());
        }

        @Test
        void testDeleteStudent() {
            Student student = new Student();
            student.setName("John");
            student.setSurname("Doe");
            student.setWorking(true);
            Student savedStudent = studentService.saveStudent(student);

            studentService.deleteStudent(savedStudent.getId());

            Optional<Student> foundStudent = studentService.getStudentById(savedStudent.getId());

            assertFalse(foundStudent.isPresent());
        }
    }


