package com.example.restapi.global.model;

import com.example.restapi.global.response.LinkFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

public abstract class AbstractRepresentationModel<T extends RepresentationModel<? extends T>> extends RepresentationModel<T> {

    @Override
    @JsonProperty("_links")
    @Schema(description = "links", nullable = true, implementation = LinkFormat.class)
    public Links getLinks() {
        return super.getLinks();
    }
}
