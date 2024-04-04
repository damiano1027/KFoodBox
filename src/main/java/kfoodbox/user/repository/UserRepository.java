package kfoodbox.user.repository;

import kfoodbox.user.entity.Language;
import kfoodbox.user.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserRepository {
    void saveUser(@Param("user") User user);
    User findUserByEmail(@Param("email") String email);
    User findUserByNickname(@Param("nickname") String nickname);
    Language findLanguageById(@Param("id") Long id);
}
