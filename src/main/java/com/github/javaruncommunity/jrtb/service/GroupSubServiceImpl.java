package com.github.javaruncommunity.jrtb.service;

import com.github.javaruncommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javaruncommunity.jrtb.repository.GroupSubRepository;
import com.github.javaruncommunity.jrtb.repository.entity.GroupSub;
import com.github.javaruncommunity.jrtb.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class GroupSubServiceImpl implements GroupSubService {

    private final GroupSubRepository groupSubRepository;
    private final TelegramUserService telegramUserService;

    @Autowired
    public GroupSubServiceImpl(GroupSubRepository groupSubRepository, TelegramUserService telegramUserService) {
        this.groupSubRepository = groupSubRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo, Integer lastPost) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //TODO add exception handling
        GroupSub groupSub;
        Optional<GroupSub> groupSubFromDB = groupSubRepository.findById(groupDiscussionInfo.getId());
        if(groupSubFromDB.isPresent()) {
            groupSub = groupSubFromDB.get();
            Optional<TelegramUser> first = groupSub.getUsers().stream()
                    .filter(it -> it.getChatId().equals(chatId))
                    .findFirst();
            if(first.isEmpty()) {
                groupSub.addUser(telegramUser);
            }
        } else {
            groupSub = new GroupSub();
            groupSub.addUser(telegramUser);
            groupSub.setId(groupDiscussionInfo.getId());
            groupSub.setTitle(groupDiscussionInfo.getTitle());
            groupSub.setLastPostId(lastPost);
        }
        return groupSubRepository.save(groupSub);
    }

    @Override
    public List<GroupSub> findAll() {
        return groupSubRepository.findAll();
    }

    @Override
    public GroupSub save(GroupSub toSave) {
        return groupSubRepository.save(toSave);
    }

    @Override
    public Optional<GroupSub> findById(Integer groupId) {
        return groupSubRepository.findById(groupId);
    }
}
