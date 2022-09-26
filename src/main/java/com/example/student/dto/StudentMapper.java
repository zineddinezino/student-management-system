package com.example.student.dto;

import com.example.student.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDto toDTO(Student student);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    Student toModel(StudentDto studentDto);

}
