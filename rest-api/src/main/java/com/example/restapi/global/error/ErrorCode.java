package com.example.restapi.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOT_FOUND_DATA(204, "ERROR-001", "조건에 맞는 데이터를 찾을 수 없습니다."),
    ACCESS_DENIED(401,"ERROR-002", "사용자가 인증되지 않았습니다."),
    INVALID_TOKEN(400, "ERROR-003", "토큰이 올바르지 않습니다."),
    EXPIRED_TOKEN(400,"ERROR-004", "토큰이 만료되었습니다."),
    HTTP_METHOD_NOT_SUPPORTED(405, "ERROR-005", "지원하지 않는 메서드를 호출하였습니다."),
    NOT_ACCEPTABLE(406, "ERROR-006", "지원하지 않는 미디어 타입의 Accept 요청입니다."),
    CONFLICT_REQUEST(409, "ERROR-007", "중복 요청이 발생하였습니다."),
    TOO_MANY_REQUEST(429, "ERROR-008", "비정상적으로 많은 요청이 발생하였습니다."),
    SERVER_INTERNAL_ERROR(500, "ERROR-009", "내부 오류가 발생하였습니다."),
    INVALID_PARAMETER(400, "ERROR-010", "입력 값이 올바르지 않습니다.");

    private final Integer httpStatusCode;
    private final String code;
    private final String message;
}
