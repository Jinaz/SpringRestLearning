package com.jinaz.learning.rest.Model;

import com.jinaz.learning.rest.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<User, Integer> {

}
