package com.example.restapi.global.util;

import lombok.Getter;

@Getter
public class CommonApiCode {
    private CommonApiCode() {}

    public static class HttpMethod {
        private HttpMethod() {}
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
        public static final String PATCH  = "PATCH ";
    }

    public static class ResponseCode {
        private ResponseCode() {}
        public static final String SUCCESS_200 = "200";
        public static final String SUCCESS_201 = "201";
        public static final String NO_CONTENT_204 = "204";
        public static final String ERROR_400 = "400";
        public static final String ERROR_409 = "409";
        public static final String ERROR_500 = "500";
        public static final String ERROR_503 = "503";
    }

    public static class ResponseLinkName {
        private ResponseLinkName() {}
        public static final String SELF = "self";
        public static final String LIST = "list";
        public static final String CREATE = "create";
        public static final String UPDATE = "update";
        public static final String DELETE = "delete";
    }

    public static class ResponseLinkParameter {
        private ResponseLinkParameter() {}
        public static final String HREF = "href";
        public static final String TYPE = "type";
    }
}
