package com.github.javaruncommunity.jrtb.service;

import com.github.javaruncommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javaruncommunity.jrtb.repository.entity.GroupSub;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo, Integer lastArticle);

    Optional<GroupSub> findById(Integer groupId);

    List<GroupSub> findAll();

    GroupSub save(GroupSub toSave);
}
