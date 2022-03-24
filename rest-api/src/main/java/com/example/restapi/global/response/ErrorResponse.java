package com.example.restapi.global.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "오류 Response")
@Getter
@Setter
public class ErrorResponse {
    @Schema(description = "응답 코드", defaultValue = "")
    private Integer httpStatusCode;

    @Schema(description = "오류 코드", defaultValue = "")
    private String errorCode;

    @Schema(description = "오류 메시지", defaultValue = "")
    private String errorMessage;

    @Builder
    public ErrorResponse(Integer httpStatusCode, String errorMessage, String errorCode) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
