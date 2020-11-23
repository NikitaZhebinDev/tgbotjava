package org.telegram.bot.command;

import org.apache.log4j.Level;
import org.telegram.bot.service.AnonymousService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * /my_name — отобразит текущее имя пользователя;
 */
public final class MyNameCommand extends AnonymizerCommand {

  private final AnonymousService tgAnonymouses;

  public MyNameCommand(AnonymousService anonymouses) {
    super("my_name", "show your current name that will be displayed with your messages\n");
    tgAnonymouses = anonymouses;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    log.info(user.getId() + " " + getCommandIdentifier());

    StringBuilder sb = new StringBuilder();

    SendMessage message = new SendMessage();
    message.setChatId(chat.getId().toString());

    if (!tgAnonymouses.hasAnonymous(user)) {

      sb.append("You are not in bot users' list! Send /start command!");
      log.log(Level.DEBUG, "User " + user.getId() + " is trying to execute '" + getCommandIdentifier()
          + "' without starting the bot.");

    } else if (tgAnonymouses.getDisplayedName(user) == null) {

      sb.append("Currently you don't have a name.\nSet it using command:\n'/set_name &lt;displayed_name&gt;'");
      log.log(Level.DEBUG, "User " + user.getId() + " is trying to execute '" + getCommandIdentifier()
          + "' without having a name.");

    } else {

      log.info("User " + user.getId() + " is executing '" + getCommandIdentifier()
          + "'. Name is '" + tgAnonymouses.getDisplayedName(user) + "'.");
      sb.append("Your current name: ").append(tgAnonymouses.getDisplayedName(user));
    }

    message.setText(sb.toString());
    execute(absSender, message, user);
  }

}
