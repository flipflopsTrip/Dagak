package com.ssafy.backend.friend.model.repository;

import com.ssafy.backend.friend.model.domain.Friend;
import com.ssafy.backend.friend.model.domain.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, UserId> {
    Optional<Friend> findByUserIdAndIsFriend(UserId userId, int isFriend);
}
