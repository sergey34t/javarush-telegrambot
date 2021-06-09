package com.github.javaruncommunity.jrtb.command;

import com.github.javaruncommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javaruncommunity.jrtb.javarushclient.JavaRushPostClient;
import com.github.javaruncommunity.jrtb.javarushclient.dto.GroupDiscussionInfo;
import com.github.javaruncommunity.jrtb.javarushclient.dto.GroupInfo;
import com.github.javaruncommunity.jrtb.javarushclient.dto.GroupRequestArgs;
import com.github.javaruncommunity.jrtb.javarushclient.dto.PostInfo;
import com.github.javaruncommunity.jrtb.repository.entity.GroupSub;
import com.github.javaruncommunity.jrtb.service.GroupSubService;
import com.github.javaruncommunity.jrtb.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.javaruncommunity.jrtb.command.CommandName.ADD_GROUP_SUB;
import static com.github.javaruncommunity.jrtb.command.CommandUtils.getChatId;
import static com.github.javaruncommunity.jrtb.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Add Group subscription {@link Command}.
 */
public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService, JavaRushGroupClient javaRushGroupClient,
                              GroupSubService groupSubService,JavaRushPostClient javaRushPostClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(getChatId(update));
            return;
        }
        String groupId = getMessage(update).split(SPACE)[1];
        Long chatId = getChatId(update);
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            Integer lastPost = javaRushPostClient.findAllPostsByGroupId(Integer.parseInt(groupId)).stream().mapToInt(PostInfo::getId).max().getAsInt();
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById, lastPost);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }

    private void sendGroupNotFound(Long chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    private void sendGroupIdList(Long chatId) {
        String groupIds =  javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай комадну вместе с ID группы. \n" +
                "Например: /addGroupSub 16. \n\n" +
                "я подготовил список всех групп - выберай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }
}