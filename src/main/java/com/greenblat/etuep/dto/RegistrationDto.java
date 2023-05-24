package com.greenblat.etuep.dto;

import com.greenblat.etuep.validation.UniqueUsername;
import com.greenblat.etuep.validation.group.RegisterAction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @NotEmpty(message = "Name should not be empty")
    @UniqueUsername(groups = RegisterAction.class)
    String username;

    @NotEmpty(message = "Password should not be empty")
    @Size(min = 8, max = 30, message = "Password's length should be greater than 8")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Пароль должен содержать минимум восемь символов, минимум одна заглавная буква, одна строчная " +
                    "буква и одна цифра:"
    )
    String password;
}
