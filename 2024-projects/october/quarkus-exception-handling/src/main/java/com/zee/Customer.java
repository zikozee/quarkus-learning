package com.zee;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * @author : Ezekiel Eromosei
 * @code @created : 30 Oct, 2024
 */

@Getter
@Setter
public class Customer {
    @NotBlank(message = "First Name is required")
    private String firstName;
    @Size(min= 7, message = "Last Name must be greater than 7 chars")
    private String lastName;
    @Min(value = 18L, message = "Age cannot be lesser than 18")
    private int age;
    @Past(message = "birthDate must be in the past")
    private LocalDate birthDate;
    @Email(message = "Email is not valid")
    private String email;
}
