package org.telegram.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {

  public static void main(String[] args) {
    try {
      DefaultBotOptions botOptions = new DefaultBotOptions();
      TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
      telegramBotsApi.registerBot(new AnonymizerBot(botOptions));
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }

}
