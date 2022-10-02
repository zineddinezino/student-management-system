package com.example.student.service;

import com.example.student.dto.StudentDto;
import com.example.student.dto.StudentMapper;
import com.example.student.exception.StudentMSException;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private StudentRepository studentRepository;
    private StudentMapper studentMapper;

    public StudentDto createStudent(Student student){
        studentRepository.save(student);
        return studentMapper.toDTO(student);
    }
    public List<StudentDto> getAllStudents(){
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    public StudentDto getStudentById(Long studentId){
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentMSException("Student not found"));
        return studentMapper.toDTO(student);
    }

    public StudentDto updateStudentInfo(Student studentToUpdate){
        // This method should be split into several methods based on the model
        Student student = studentRepository.findById(studentToUpdate.getStudentId()).orElseThrow(() -> new StudentMSException("Student not found"));
        student.setFirstName(studentToUpdate.getFirstName());
        student.setLastName(studentToUpdate.getLastName());
        studentRepository.save(student);
        return studentMapper.toDTO(student);
    }
    public void deleteStudent(Long studentId){
        studentRepository.deleteById(studentId);
    }
}
