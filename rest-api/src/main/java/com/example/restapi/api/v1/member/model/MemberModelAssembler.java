package com.example.restapi.api.v1.member.model;

import com.example.restapi.api.v1.member.controller.MemberController;
import com.example.restapi.api.v1.member.entity.Member;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MemberModelAssembler implements RepresentationModelAssembler<Member, MemberModel> {

    @Override
    public MemberModel toModel(Member member) {
        return member.toModel()
                .add(linkTo(methodOn(MemberController.class).getMember(member.getSeq())).withSelfRel().withType(HttpMethod.GET.name()));
    }

    public MemberModel toModelDetail(Member member) {
        return member.toModel()
                .add(linkTo(methodOn(MemberController.class).getMember(member.getSeq())).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(MemberController.class).slash(member.getSeq()).withRel("update").withType(HttpMethod.PUT.name()))
                .add(linkTo(MemberController.class).slash(member.getSeq()).withRel("delete").withType(HttpMethod.DELETE.name()))
                .add(linkTo(MemberController.class).withRel("list").withType(HttpMethod.GET.name()));
    }

    public MemberModel toModelUpdate(Member member) {
        return member.toModel()
                .add(linkTo(methodOn(MemberController.class).getMember(member.getSeq())).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(MemberController.class).withRel("list").withType(HttpMethod.GET.name()));
    }

    public Link toModelDelete() {
        return linkTo(MemberController.class).withRel("list").withType(HttpMethod.GET.name());
    }

    @Override
    public CollectionModel<MemberModel> toCollectionModel(Iterable<? extends Member> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities)
                .add(linkTo(methodOn(MemberController.class).getAllMember()).withSelfRel().withType(HttpMethod.GET.name()))
                .add(linkTo(MemberController.class).withRel("create").withType(HttpMethod.POST.name()));
    }
}
