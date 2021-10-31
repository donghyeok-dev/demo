package com.example.oauthjwt.common.converter;

import javax.persistence.AttributeConverter;

public class BooleanTypeConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute != null && attribute ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
