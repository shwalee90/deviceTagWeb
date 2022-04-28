package com.auxil.pump.repository;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.RefreshToken;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.Query;

public interface authRepositoryCustom {
    @Query(value = "select refresh_token from refresh_token where idx = :idx " ,  nativeQuery = true)
    String findRefreshTokenByIdx(long idx);

    @Query(value = "INSERT INTO refresh_token(idx, access_token, refresh_token, refresh_token_expiration_at, user_name) " +
            "VALUES (:accessToken, :refreshToken ,:refreshTokenExpirationAt ," +
            ":userName) ON DUPLICATE KEY UPDATE idx = (SELECT idx from (select (idx +1 ) as idx from refresh_token b order by desc limit 1) as A),"+
            "access_token = :accessToken , refresh_token = :refreshToken ,  refresh_token_expiration_at = :refreshTokenExpirationAt , user_name = :userName)",  nativeQuery = true)
    RefreshToken saveRefreshToken(RefreshToken rft);

}

