package com.imdb.demo.Registration.repository;


import com.imdb.demo.Registration.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Integer> {
    boolean existsByEmail(String email);

    boolean existsByOtp(String otp);

    @Modifying
    @Transactional
    @Query("delete from Registration f where f.email=:email")
    void removeByEmail(@Param("email") String email);

    @Modifying
    @Transactional
    @Query(value = "update Registration f set f.otp =:otp  where f.email =:email",nativeQuery = true)
    void updateOtp(@Param("email") String email,@Param("otp") String otp);
}
