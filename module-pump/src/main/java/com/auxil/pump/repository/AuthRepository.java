package com.auxil.pump.repository;

import com.auxil.pump.domain.Member;
import com.auxil.pump.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface AuthRepository extends JpaRepository<RefreshToken , Long> {

    @Query(value = "select refresh_token from refresh_token where idx = :idx " ,  nativeQuery = true)
    Optional<RefreshToken> findRefreshTokenByIdx(long idx);

    @Query(value = "select idx, access_token, refresh_token, user_name , refresh_token_expiration_at from refresh_token where user_name = :name " ,  nativeQuery = true )
    RefreshToken findByUser_name(@Param(value = "name") String name);



    RefreshToken save(RefreshToken member);

    @Query(value = "INSERT INTO refresh_token( access_token, refresh_token, refresh_token_expiration_at, user_name) " +
            "VALUES ( :#{#rt.accessToken}, :#{#rt.refreshToken} ,:#{#rt.refreshTokenExpirationAt} ," +
            ":#{#rt.userName}) " +
            "ON DUPLICATE KEY UPDATE idx = (SELECT idx from (select (idx +1 ) as idx from refresh_token b order by desc limit 1) as A),"+
           "access_token = :#{#rt.accessToken} , refresh_token = :#{#rt.refreshToken} ,  refresh_token_expiration_at =  :#{#rt.refreshTokenExpirationAt}  , user_name = :#{#rt.userName})"
            ,  nativeQuery = true)
    RefreshToken saveRefreshToken(@Param(value = "rt") RefreshToken rft);
}
//@Query(value = "INSERT INTO refresh_token(idx, access_token, refresh_token, refresh_token_expiration_at, user_name) " +
//        "VALUES (:rft.accessToken, :rft.refreshToken ,:rft.refreshTokenExpirationAt ," +
//        ":rft.userName) ON DUPLICATE KEY UPDATE idx = (SELECT idx from (select (idx +1 ) as idx from refresh_token b order by desc limit 1) as A),"+
//        "access_token = :rft.accessToken , refresh_token = :rft.refreshToken ,  refresh_token_expiration_at = :rft.refreshTokenExpirationAt , user_name = :rft.userName)",  nativeQuery = true)