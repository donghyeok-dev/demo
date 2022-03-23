package com.example.restapi.global.response;

public class LinkFormat {
    public CustomLink profile;
    public CustomDetailLink self;
    public CustomDetailLink list;
    public CustomDetailLink update;
    public CustomDetailLink create;
    public CustomDetailLink delete;

    class CustomLink {
        public String href;
    }

    class CustomDetailLink extends CustomLink {
        public String type;
    }
}
