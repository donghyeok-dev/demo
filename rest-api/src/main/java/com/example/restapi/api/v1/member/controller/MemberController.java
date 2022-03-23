package com.example.restapi.api.v1.member.controller;

import com.example.restapi.api.v1.member.model.MemberDto;
import com.example.restapi.api.v1.member.model.MemberModel;
import com.example.restapi.api.v1.member.service.MemberService;
import com.example.restapi.global.error.exception.InvalidParameterException;
import com.example.restapi.global.response.ErrorResponse;
import com.example.restapi.global.response.LinkFormat;
import com.example.restapi.global.util.CommonApiCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.MediaTypes;

import javax.validation.Valid;

import static com.example.restapi.global.util.CommonApiCode.*;

/**
 * 회원 Restful API
 *
 * produces : 서버에서 클라이언트로 보내는 타입 지정
 * consumes : 클라이언트에서 서버로 보내는 타입 지정
 * @author kdh
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "회원", description = "회원 API")
@ApiResponse(responseCode = ResponseCode.ERROR_400, description = "올바르지 않는 요청", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
@ApiResponse(responseCode = ResponseCode.ERROR_500, description = "서버 내 오류 발생", content = @Content(schema = @Schema(hidden = true)))
@ApiResponse(responseCode = ResponseCode.ERROR_503, description = "네트워크 문제 또는 서버 이용 불가", content = @Content(schema = @Schema(hidden = true)))
@RequestMapping(value = "/api/v1/member", produces = MediaTypes.HAL_JSON_VALUE)
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "회원 목록 조회", description = "모든 회원 정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200,
                    description = "조회 성공",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = MemberModel.class))),
                    links = {
                            @Link(name = ResponseLinkName.SELF, description = "현재", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.CREATE, description = "등록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.POST)
                            })
                    })
    })
    @GetMapping
    public ResponseEntity<CollectionModel<MemberModel>> getAllMember() {
        return ResponseEntity.ok()
                .body(this.memberService.findAllMember());
    }

    @Operation(summary = "회원 조회", description = "회원 id로 회원 정보를 조회합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200, description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = MemberModel.class)),
                    links =  {
                            @Link(name = ResponseLinkName.SELF, description = "현재", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.UPDATE, description = "수정", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.PUT)
                            }),
                            @Link(name = ResponseLinkName.DELETE, description = "삭제", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.DELETE)
                            }),
                            @Link(name = ResponseLinkName.LIST, description = "목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
            @ApiResponse(responseCode = ResponseCode.NO_CONTENT_204, description = "조회된 정보가 없음", content = @Content(schema = @Schema(hidden = true)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MemberModel> getMember(@Parameter(description = "회원 ID") @PathVariable Long id) {
        return ResponseEntity.ok()
                .body(this.memberService.findMember(id));
    }

    @Operation(summary = "회원 등록", description = "신규 회원을 등록합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_201, description = "등록 성공",
                    content = @Content(schema = @Schema(implementation = MemberModel.class)),
                    links =  {
                            @Link(name = ResponseLinkName.SELF, description = "회원 조회", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.LIST, description = "회원 목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
            @ApiResponse(responseCode = ResponseCode.ERROR_409, description = "이미 등록된 회원", content = @Content(schema = @Schema(hidden = true)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MemberModel> createMember(@RequestBody @Valid MemberDto memberDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidParameterException("입력 값이 올바르지 않습니다.", errors);
        }

        MemberModel createdMember = this.memberService.saveMember(memberDto);
        // 201 Created 응답 및 생성된 자원의 주소를 Location 헤더에 등록하며 프론트에서 리다이렉트 목적으로 사용될 수 있습니다.
        return ResponseEntity.created(createdMember.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(createdMember);
    }

    @Operation(summary = "회원 수정", description = "회원 회원 정보를 수정합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200, description = "수정 성공",
                    content = @Content(schema = @Schema(implementation = MemberModel.class)),
                    links =  {
                            @Link(name = ResponseLinkName.SELF, description = "회원 조회", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri/{id}"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            }),
                            @Link(name = ResponseLinkName.LIST, description = "회원 목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
    })
    @PutMapping("/{id}")
    public ResponseEntity<MemberModel> updateMember(@Parameter(description = "회원 ID") @PathVariable Long id,
                                                    @RequestBody @Valid MemberDto memberDto, Errors errors) {
        if (errors.hasErrors()) {
            throw new InvalidParameterException("입력 값이 올바르지 않습니다.", errors);
        }

        return ResponseEntity.ok()
                .body(this.memberService.updateMember(id, memberDto));
    }

    @Operation(summary = "회원 삭제", description = "회원 회원 정보를 삭제합니다.", responses = {
            @ApiResponse(responseCode = ResponseCode.SUCCESS_200, description = "삭제 성공",
                    content = @Content(schema = @Schema(implementation = LinkFormat.class)),
                    links =  {
                            @Link(name = ResponseLinkName.LIST, description = "회원 목록", parameters = {
                                    @LinkParameter(name = ResponseLinkParameter.HREF, expression = "$api.uri"),
                                    @LinkParameter(name = ResponseLinkParameter.TYPE, expression = HttpMethod.GET)
                            })
                    }),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<org.springframework.hateoas.Link> deleteMember(@Parameter(description = "회원 ID") @PathVariable Long id) {
        return ResponseEntity.ok()
                .body(this.memberService.deleteMember(id));
    }
}
