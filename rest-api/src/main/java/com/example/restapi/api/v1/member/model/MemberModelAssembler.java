package com.example.restapi.api.v1.member.model;

import com.example.restapi.api.v1.member.controller.MemberController;
import com.example.restapi.api.v1.member.entity.Member;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static com.example.restapi.global.util.CommonApiCode.HttpMethod;
import static com.example.restapi.global.util.CommonApiCode.ResponseLinkName;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * 회원 Model Assembler
 * @author kdh
 */
@Component
public class MemberModelAssembler implements RepresentationModelAssembler<Member, MemberModel> {
    private Errors errorsDummy;

    public MemberModelAssembler() {
        this.errorsDummy = new BeanPropertyBindingResult(new Object(), "dummy");
    }

    @Override
    public MemberModel toModel(Member member) {
        return member.toModel()
                .add(getSelfLink(member.getId()));
    }

    public MemberModel toModelDetail(Member member) {
        return member.toModel()
                .add(getSelfLink(member.getId()))
                .add(getUpdateLink(member.getId()))
                .add(getDeleteLink(member.getId()))
                .add(getListLink(false));
    }

    public MemberModel toModelUpdate(Member member) {
        return member.toModel()
                .add(getSelfLink(member.getId()))
                .add(getListLink(false));
    }

    public Link toModelDelete() {
        return getListLink(false);
    }

    @Override
    public CollectionModel<MemberModel> toCollectionModel(Iterable<? extends Member> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(getListLink(true))
                .add(getCreateLink());
    }

    private Link getSelfLink(Long id) {
        return linkTo(methodOn(MemberController.class)
                .findMember(id))
                .withSelfRel()
                .withType(HttpMethod.GET);
    }

    private Link getListLink(boolean self) {
        return self
                ? linkTo(methodOn(MemberController.class)
                    .getAllMember())
                    .withSelfRel()
                    .withType(HttpMethod.GET)
                : linkTo(methodOn(MemberController.class)
                    .getAllMember())
                    .withRel(ResponseLinkName.LIST)
                    .withType(HttpMethod.GET);
    }

    private Link getCreateLink() {
        return linkTo(methodOn(MemberController.class)
                .createMember(null, this.errorsDummy))
                .withRel(ResponseLinkName.CREATE)
                .withType(HttpMethod.POST);
    }

    private Link getUpdateLink(Long id) {
        return linkTo(methodOn(MemberController.class)
                .updateMember(id, null, this.errorsDummy))
                .withRel(ResponseLinkName.UPDATE)
                .withType(HttpMethod.PUT);
    }

    private Link getDeleteLink(Long id) {
        return linkTo(methodOn(MemberController.class)
                .deleteMember(id))
                .withRel(ResponseLinkName.DELETE)
                .withType(HttpMethod.DELETE);
    }
}
