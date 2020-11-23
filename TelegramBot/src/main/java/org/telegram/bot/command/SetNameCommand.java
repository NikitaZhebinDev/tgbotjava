package org.telegram.bot.command;

import org.apache.log4j.Level;
import org.telegram.bot.service.AnonymousService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * /set_name — задаст пользователю имя, от которого будут отправляться анонимные сообщения;
 */
public final class SetNameCommand extends AnonymizerCommand {

  private final AnonymousService tgAnonymouses;

  public SetNameCommand(AnonymousService anonymouses) {
    super("set_name", "set or change name that will be displayed with your messages\n");
    tgAnonymouses = anonymouses;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    log.info(user.getId() + " " + getCommandIdentifier());

    SendMessage message = new SendMessage();
    message.setChatId(chat.getId().toString());

    if (!tgAnonymouses.hasAnonymous(user)) {
      log.log(Level.DEBUG, "User " + user.getId() + " is trying to execute '{}' without starting the bot!");
      message.setText("Firstly you should start the bot! Execute '/start' command!");
      execute(absSender, message, user);
      return;
    }

    String displayedName = getName(strings);

    if (displayedName == null) {
      log.log(Level.DEBUG, "User " + user.getId() + " is trying to set empty name.");
      message.setText("You should use non-empty name!");
      execute(absSender, message, user);
      return;
    }

    StringBuilder sb = new StringBuilder();

    if (tgAnonymouses.setUserDisplayedName(user, displayedName)) {

      if (tgAnonymouses.getDisplayedName(user) == null) {
        log.info("User " + user.getId() + " set a name '" + displayedName + "'");
        sb.append("Your displayed name: '").append(displayedName)
            .append("'. Now you can send messages to bot!");
      } else {
        log.info("User " + user.getId() + " has changed name to '" + displayedName + "'");
        sb.append("Your new displayed name: '").append(displayedName).append("'.");
      }
    } else {
      log.log(Level.DEBUG, "User " + user.getId() + " is trying to set taken name '" + displayedName + "'");
      sb.append("Name ").append(displayedName).append(" is already in use! Choose another name!");
    }

    message.setText(sb.toString());
    execute(absSender, message, user);
  }

  private String getName(String[] strings) {

    if (strings == null || strings.length == 0) {
      return null;
    }

    String name = String.join(" ", strings);
    return name.replaceAll(" ", "").isEmpty() ? null : name;
  }

}
