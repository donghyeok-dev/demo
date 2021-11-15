package com.example.oauthjwt.api.member.entity;

import com.example.oauthjwt.common.converter.BooleanTypeConverter;
import com.example.oauthjwt.common.entity.BaseEntity;
import com.example.oauthjwt.config.oauth2.converter.ProviderTypeConverter;
import com.example.oauthjwt.config.oauth2.converter.RoleTypeConverter;
import com.example.oauthjwt.config.oauth2.enums.ProviderType;
import com.example.oauthjwt.config.oauth2.enums.RoleType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Getter
@Entity
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(length = 64, unique = true)
    @NotNull
    @Size(max = 64)
    private String userId;

    @Column(length = 256)
    @Size(max = 128)
    private String password;

    @Column(length = 100)
    @NotNull
    @Size(max = 100)
    private String userName;

    @Column(length = 512, unique = true)
    @NotNull
    @Size(max = 512)
    private String email;

    @Column(length = 1)
    @Convert(converter = BooleanTypeConverter.class)
    @NotNull
    private Boolean verifiedEmail;

    @Column(length = 512)
    private String pictureUrl;

    @Column(length = 1)
    @Convert(converter = ProviderTypeConverter.class)
    @NotNull
    private ProviderType providerType;

    @Column(length = 20)
    @Convert(converter = RoleTypeConverter.class)
    @NotNull
    private RoleType roleType;

    public void modifyProviderMember(String name, String pictureUrl) {
        this.userName = name;
        this.pictureUrl = pictureUrl;
    }

}
