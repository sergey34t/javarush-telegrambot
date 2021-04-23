package com.github.javaruncommunity.jrtb.repository;

import com.github.javaruncommunity.jrtb.repository.entity.GroupSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupSubRepository extends JpaRepository<GroupSub, Integer> {
}
