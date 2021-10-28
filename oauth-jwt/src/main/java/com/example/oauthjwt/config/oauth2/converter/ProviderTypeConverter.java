package com.example.oauthjwt.config.oauth2.converter;

import com.example.oauthjwt.common.converter.AbstractTypeConverter;
import com.example.oauthjwt.config.oauth2.enums.ProviderType;

import javax.persistence.Converter;

@Converter
public class ProviderTypeConverter extends AbstractTypeConverter<ProviderType> {
    public ProviderTypeConverter() {
        super(ProviderType.class, true);
    }
}
