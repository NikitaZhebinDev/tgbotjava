package org.telegram.bot.command;

import org.apache.log4j.Level;
import org.telegram.bot.service.AnonymousService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * /stop — удалит пользователя из коллекции анонимусов.
 */
public final class StopCommand extends AnonymizerCommand {

  private final AnonymousService tgAnonymouses;

  public StopCommand(AnonymousService anonymouses) {
    super("stop", "remove yourself from bot users' list\n");
    tgAnonymouses = anonymouses;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    log.info(user.getId() + " " + getCommandIdentifier());

    StringBuilder sb = new StringBuilder();

    SendMessage message = new SendMessage();
    message.setChatId(chat.getId().toString());

    if (tgAnonymouses.removeAnonymous(user)) {
      log.info("User " + user.getId() + " has been removed from users list!");
      sb.append("You've been removed from bot's users list! Bye!");
    } else {
      log.log(Level.DEBUG, "User " + user.getId() + " is trying to execute '" + getCommandIdentifier() + "' without having executed 'start' before!");
      sb.append("You were not in bot users' list. Bye!");
    }

    message.setText(sb.toString());
    execute(absSender, message, user);
  }
}
