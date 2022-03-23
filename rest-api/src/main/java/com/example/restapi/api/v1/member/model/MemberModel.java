package com.example.restapi.api.v1.member.model;

import com.example.restapi.global.response.LinkFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

/**
 * 회원 Dto
 * @author kdh
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@Schema(description = "회원")
public class MemberModel extends RepresentationModel<MemberModel> {
    @Schema(description = "회원번호", example = "2")
    private Long seq;

    @Schema(description = "이름", example = "홍길동", maxLength = 30)
    private String name;

    @Schema(description = "생년월일", example = "901023", maxLength = 6)
    private String birthDay;

    @Schema(description = "이메일", example = "example@gmail.com", maxLength = 50)
    private String email;

    @Schema(description = "주소", nullable = true, example = "서울시 서대문구")
    private String address;

    @Override
    @JsonProperty("_links")
    @Schema(description = "links", nullable = true, implementation = LinkFormat.class)
    public Links getLinks() {
        return super.getLinks();
    }
}
