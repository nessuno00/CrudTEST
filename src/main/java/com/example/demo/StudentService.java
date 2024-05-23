package com.example.demo;

import java.util.List;
import java.util.Optional;

    public interface StudentService {
        Student saveStudent(Student student);
        List<Student> getAllStudents();
        Optional<Student> getStudentById(Long id);
        Student updateStudent(Long id, Student student);
        Student updateWorkingStatus(Long id, boolean isWorking);
        void deleteStudent(Long id);
    }

