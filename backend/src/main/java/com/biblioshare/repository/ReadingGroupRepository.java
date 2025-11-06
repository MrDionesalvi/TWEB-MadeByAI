package com.biblioshare.repository;

import com.biblioshare.entity.ReadingGroup;
import com.biblioshare.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReadingGroupRepository extends JpaRepository<ReadingGroup, Long> {
    List<ReadingGroup> findByCreator(User creator);
    List<ReadingGroup> findByMembersContaining(User user);
}
