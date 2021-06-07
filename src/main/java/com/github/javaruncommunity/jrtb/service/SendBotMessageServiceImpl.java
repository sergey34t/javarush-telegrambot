package com.github.javaruncommunity.jrtb.service;

import com.github.javaruncommunity.jrtb.bot.JavaRunTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService{

    private final JavaRunTelegramBot javaRunTelegramBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaRunTelegramBot javaRunTelegramBot) {
        this.javaRunTelegramBot = javaRunTelegramBot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            javaRunTelegramBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            //todo add logging to the project.
            e.printStackTrace();
        }
    }

}
