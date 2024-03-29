package com.auxil.tagweb.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MethodRefCanBeReplacedWithLambda")
@Setter
@Getter
@Entity
@ToString
public class Member  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "uid" , example = "24235" , required = true)
    private long id;

    @Column(nullable = false, length = 20)
    @ApiModelProperty(value = "유저이름" , example = "shwalee" , required = true)
    private String name;

    @Column(nullable = false)
    @JsonIgnore
    @ApiModelProperty(value = "비밀번호" , example = "1234" , required = true)
    private String password;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="member_id")
    private List<MemberRole> roles = new ArrayList<>();


}
