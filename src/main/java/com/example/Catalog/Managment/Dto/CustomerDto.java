package com.example.Catalog.Managment.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto
{
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    @Email(message = "please enter email address")
    private String email;

    @Pattern(regexp = "^[6-9]\\d{9}$", message = "enter valid 10 digit indian phone number")
    private String phonenumber;

}
