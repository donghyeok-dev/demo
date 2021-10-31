package com.example.oauthjwt.config.oauth2.converter;

import com.example.oauthjwt.common.converter.AbstractTypeConverter;
import com.example.oauthjwt.config.oauth2.enums.RoleType;

public class RoleTypeConverter extends AbstractTypeConverter<RoleType> {
    public RoleTypeConverter() {
        super(RoleType.class, false);
    }
}
