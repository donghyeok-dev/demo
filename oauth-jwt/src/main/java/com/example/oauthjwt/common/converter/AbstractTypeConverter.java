package com.example.oauthjwt.common.converter;

import com.example.oauthjwt.common.enums.ConverterEnum;
import com.example.oauthjwt.common.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import java.util.EnumSet;

/**
 * Entity의 Enum 필드값 {@code <->} DB에 저장되는 값 양방향 컨버터의 공통구현체 추상클래스
 * @param <E> 컨버트되는 Enum타입
 */
@RequiredArgsConstructor
public abstract class AbstractTypeConverter<E extends Enum<E> & ConverterEnum> implements AttributeConverter<E, String> {
    private final Class<E> targetEnumClass;
    private final boolean nullable;

    /**
     * Entity의 Enum 필드가 DB로 저장될때 호출되는 메서드
     * @param attribute Entity 객체의 Enum
     * @return Enum에서 DB에 저장할때 사용되는 코드 값
     */
    @Override
    public String convertToDatabaseColumn(E attribute) {
        if(!nullable && attribute == null) {
            throw new IllegalArgumentException("null 값으로 DB에 저장할 수 없습니다.");
        }

        return attribute.getCode();
    }

    /**
     * DB에서 Entity 객체의 Enum으로 컨버트될 때 호출되는 메서드
     * @param dbData DB에 저장된 코드 값
     * @return 코드값에 맞는 Enum
     */
    @Override
    public E convertToEntityAttribute(String dbData) {
        if(!nullable && !StringUtils.hasText(dbData)) {
            throw new IllegalArgumentException("DB에 null 또는 empty 값을  Enum으로 변환할 수 없습니다.");
        }

        return EnumSet.allOf(targetEnumClass).stream()
                .filter(e -> CommonUtil.nvl(e.getCode()).equals(CommonUtil.nvl(dbData)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("DB에서 읽은 %s 코드값이 %s enum에 존재하지 않습니다.",
                                dbData, targetEnumClass.getName())));
    }
}
