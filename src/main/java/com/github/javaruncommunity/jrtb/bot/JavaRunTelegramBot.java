package com.github.javaruncommunity.jrtb.bot;

import com.github.javaruncommunity.jrtb.command.CommandContainer;
import com.github.javaruncommunity.jrtb.javarushclient.JavaRushGroupClient;
import com.github.javaruncommunity.jrtb.javarushclient.JavaRushPostClient;
import com.github.javaruncommunity.jrtb.service.GroupSubService;
import com.github.javaruncommunity.jrtb.service.SendBotMessageServiceImpl;
import com.github.javaruncommunity.jrtb.service.StatisticsService;
import com.github.javaruncommunity.jrtb.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.List;

import static com.github.javaruncommunity.jrtb.command.CommandName.NO;

@Component
public class JavaRunTelegramBot  extends TelegramLongPollingBot {
    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;


    private final String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;

    @Autowired
    public JavaRunTelegramBot(TelegramUserService telegramUserService,JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService, JavaRushPostClient javaRushPostClient, @Value("#{'${bot.admins}'.split(',')}") List<String> admins,StatisticsService statisticsService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
                telegramUserService,
                javaRushGroupClient,
                groupSubService,
                javaRushPostClient,
                admins,
                statisticsService);
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, update.getMessage().getFrom().getUserName()).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), update.getMessage().getFrom().getUserName()).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }
}
