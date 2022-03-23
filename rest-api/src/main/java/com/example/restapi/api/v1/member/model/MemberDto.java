package com.example.restapi.api.v1.member.model;

import com.example.restapi.api.v1.member.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 회원 Dto
 * @author kdh
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "회원 업데이트")
public class MemberDto {
    @Schema(description = "이름", example = "홍길동", maxLength = 30)
    @NotEmpty(message = "이름을 입력해주세요.")
    @Size(max = 30, message = "이름은 30자 이하로 입력해주세요.")
    private String name;

    @Schema(description = "생년월일", example = "901023", maxLength = 6)
    @NotEmpty(message = "생년월일을 입력해주세요.")
    @Pattern(regexp ="[0-9]{6}$", message = "생년월일을 6자리 숫자로 입력해주세요.")
    private String birthDay;

    @Schema(description = "이메일", example = "example@gmail.com", maxLength = 50)
    @Email(message = "이메일 형식이 아닙니다.")
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Size(max = 50, message = "이메일은 50자 이하로 입력해주세요.")
    private String email;

    @Schema(description = "주소", nullable = true, example = "서울시 서대문구")
    @Size(max = 100, message = "주소는 100자 이하로 입력해주세요.")
    private String address;


    public Member toCreateEntity() {
        return Member.builder()
                .name(this.name)
                .birthDay(this.birthDay)
                .email(this.email)
                .address(this.address)
                .build();
    }

    public Member toUpdateEntity(Long id) {
        return Member.builder()
                .seq(id)
                .name(this.name)
                .birthDay(this.birthDay)
                .email(this.email)
                .address(this.address)
                .build();
    }
}
