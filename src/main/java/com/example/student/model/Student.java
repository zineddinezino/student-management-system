package com.example.student.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @NotBlank(message = "This field can not be empty")
    private String firstName;

    @NotBlank(message = "This field can not be empty")
    private String lastName;

    @Email
    @NotEmpty(message = "The email is required")
    private String email;

    @NotBlank(message = "This field can not be empty")
    private String password;

    private Date dateOfBirth;
}
