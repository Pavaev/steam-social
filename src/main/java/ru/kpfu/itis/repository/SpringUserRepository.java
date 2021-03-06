package ru.kpfu.itis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kpfu.itis.model.entity.Group;
import ru.kpfu.itis.model.entity.User;

import java.util.Optional;
import java.util.Set;

public interface SpringUserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u JOIN u.userOpenIdsSet o WHERE o.openidUrl = :openid")
    User findByOpenid(@Param("openid") String openid);

    User findByFullName(String fullName);
}
