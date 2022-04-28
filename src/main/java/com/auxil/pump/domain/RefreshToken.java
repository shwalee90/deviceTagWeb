package com.auxil.pump.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


//"select refresh_token from refresh_token where idx = :idx "

@Getter
@Builder
@Entity
@Table(name = "refresh_token")
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idx;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "access_token")
    private String accessToken;
    @Column(name = "refresh_token")
    private String refreshToken;
    @Column(name = "refresh_token_expiration_at ")
    private Date refreshTokenExpirationAt;

}
