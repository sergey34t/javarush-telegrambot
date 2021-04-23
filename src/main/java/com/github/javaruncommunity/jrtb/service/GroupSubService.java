package com.github.javaruncommunity.jrtb.service;

import com.github.javaruncommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javaruncommunity.jrtb.repository.entity.GroupSub;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}
