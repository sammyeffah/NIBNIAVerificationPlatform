package com.nib.gh.nia.model;


import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoginDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    // @NotEmpty
    // private String role;

}
