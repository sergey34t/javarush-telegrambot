package com.github.javaruncommunity.jrtb.service;

import com.github.javaruncommunity.jrtb.bot.JavaRunTelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
public class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private JavaRunTelegramBot javaRunTelegramBot;

    @BeforeEach
    public void init() {
        javaRunTelegramBot = Mockito.mock(JavaRunTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(javaRunTelegramBot);
    }

    @Test
    public void shouldProperlySendMessage() throws TelegramApiException {
        //given
        Long chatId = 1L;
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(javaRunTelegramBot).execute(sendMessage);
    }
}