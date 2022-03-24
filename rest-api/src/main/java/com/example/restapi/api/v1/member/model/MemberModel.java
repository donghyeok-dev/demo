package com.example.restapi.api.v1.member.model;

import com.example.restapi.global.model.AbstractRepresentationModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 회원 Model
 * @author kdh
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Builder
@Schema(description = "회원")
public class MemberModel extends AbstractRepresentationModel<MemberModel> {
    @Schema(description = "회원번호", example = "2")
    private Long id;

    @Schema(description = "이름", example = "홍길동", maxLength = 30)
    private String name;

    @Schema(description = "생년월일", example = "901023", maxLength = 6)
    private String birthDay;

    @Schema(description = "이메일", example = "example@gmail.com", maxLength = 50)
    private String email;

    @Schema(description = "주소", nullable = true, example = "서울시 서대문구")
    private String address;
}
