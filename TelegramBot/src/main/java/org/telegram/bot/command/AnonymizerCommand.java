package org.telegram.bot.command;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AnonymizerCommand extends BotCommand {

  final Logger log = LogManager.getLogger(getClass());

  AnonymizerCommand(String commandIdentifier, String description) {
    super(commandIdentifier, description);
  }

  void execute(AbsSender sender, SendMessage message, User user) {
    try {
      sender.execute(message);
      log.log(Level.DEBUG, user.getId());
    } catch (TelegramApiException e) {
      log.error(Level.ERROR, e);
    }
  }

}
