package com.github.javaruncommunity.jrtb.command;

import com.github.javaruncommunity.jrtb.annotation.AdminCommand;
import com.github.javaruncommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javaruncommunity.jrtb.javarushclient.JavaRushPostClient;
import com.github.javaruncommunity.jrtb.service.GroupSubService;
import com.github.javaruncommunity.jrtb.service.SendBotMessageService;
import com.github.javaruncommunity.jrtb.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;


import java.util.List;

import static com.github.javaruncommunity.jrtb.command.CommandName.*;
import static java.util.Objects.nonNull;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;
    private final List<String> admins;

    public CommandContainer(SendBotMessageService sendBotMessageService,
                            TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient,
                            GroupSubService groupSubService,
                            JavaRushPostClient javaRushPostClient,
                            List<String> admins) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(sendBotMessageService, javaRushGroupClient, groupSubService, javaRushPostClient))
                .put(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService))
                .put(DELETE_GROUP_SUB.getCommandName(), new DeleteGroupSubCommand(sendBotMessageService, groupSubService, telegramUserService))
                .put(ADMIN_HELP.getCommandName(), new AdminHelpCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
        this.admins = admins;
    }

    public Command retrieveCommand(String commandIdentifier, String username) {
        Command orDefault = commandMap.getOrDefault(commandIdentifier, unknownCommand);
        if (isAdminCommand(orDefault)) {
            if (admins.contains(username)) {
                return orDefault;
            } else {
                return unknownCommand;
            }
        }
        return orDefault;
    }

    private boolean isAdminCommand(Command command) {
        return nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }
}
