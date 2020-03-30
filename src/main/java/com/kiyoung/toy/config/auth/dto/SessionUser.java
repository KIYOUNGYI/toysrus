package com.kiyoung.toy.config.auth.dto;

import com.kiyoung.toy.domain.User;
import lombok.Getter;

import java.io.Serializable;


@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser()
    {
        System.out.println("What the fuck???");
    }

    public SessionUser(User user) {

        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
