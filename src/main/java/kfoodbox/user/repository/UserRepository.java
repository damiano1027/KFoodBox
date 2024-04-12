package kfoodbox.user.repository;

import kfoodbox.user.entity.Language;
import kfoodbox.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRepository {
    void saveUser(@Param("user") User user);
    void updateUser(@Param("user") User user);
    void deleteUserById(@Param("id") Long id);
    User findUserById(@Param("id") Long id);
    User findUserByEmail(@Param("email") String email);
    User findUserByNickname(@Param("nickname") String nickname);
    Language findLanguageById(@Param("id") Long id);
    List<Language> findAllLanguages();
}
