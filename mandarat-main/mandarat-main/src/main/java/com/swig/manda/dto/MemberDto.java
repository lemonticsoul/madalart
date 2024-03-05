package com.swig.manda.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
public class MemberDto {


    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{6,12}", message = "아이디는 6~12자 영문, 숫자를 사용하세요.")
    //@NotBlank(message = "아이디를 입력하세요.1111111111111111.")
    private String username;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")
    private String password;
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~16자 영문, 숫자, 특수문자를 사용하세요.")

    private String repassword;
    private String role;

    private String name;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String email;

}
