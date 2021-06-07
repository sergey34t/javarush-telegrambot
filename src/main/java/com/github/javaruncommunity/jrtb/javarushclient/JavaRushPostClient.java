package com.github.javaruncommunity.jrtb.javarushclient;

import com.github.javaruncommunity.jrtb.javarushclient.dto.PostInfo;

import java.util.List;

/**
 * Client for Javarush Open API corresponds to Posts.
 */
public interface JavaRushPostClient {

    /**
     * Find new posts since lastPostId in provided group.
     *
     * @param groupId provided group ID.
     * @param lastPostId provided last post ID.
     * @return the collection of the new {@link PostInfo}.
     */
    List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId);


    /**
     * Find all posts in provided group.
     *
     * @param groupId provided group ID.
     * @return the collection of the new {@link PostInfo}.
     */
    List<PostInfo> findAllPostsByGroupId(Integer groupId);
    
}
