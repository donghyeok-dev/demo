package com.example.oauthjwt.common.util;

public class CommonUtil {
    private CommonUtil() {
        throw new IllegalStateException("유틸클래스는 생성자를 사용할 수 없습니다.");
    }

    public static String nvl(String s) {
        return s == null ? "" : s;
    }
}
