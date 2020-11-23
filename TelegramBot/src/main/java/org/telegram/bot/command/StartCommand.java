package org.telegram.bot.command;

import org.apache.log4j.Level;
import org.telegram.bot.model.Anonymous;
import org.telegram.bot.service.AnonymousService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * /start — создаст нового Anonymous без имени и добавит его в коллекцию Anonymouses;
 */
public final class StartCommand extends AnonymizerCommand {

  private final AnonymousService tgAnonymouses;

  // обязательно нужно вызвать конструктор суперкласса,
  // передав в него имя и описание команды
  public StartCommand(AnonymousService anonymouses) {
    super("start", "start using bot\n");
    tgAnonymouses = anonymouses;
  }

  /**
   * реализованный метод класса BotCommand, в котором обрабатывается команда, введенная пользователем
   *
   * @param absSender - отправляет ответ пользователю
   * @param user      - пользователь, который выполнил команду
   * @param chat      - чат бота и пользователя
   * @param strings   - аргументы, переданные с командой
   */
  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    log.info(user.getId() + " " + getCommandIdentifier());

    StringBuilder sb = new StringBuilder();

    SendMessage message = new SendMessage();
    message.setChatId(chat.getId().toString());

    if (tgAnonymouses.addAnonymous(new Anonymous(user, chat))) {
      log.info("User \" + user.getId() + \" is trying to execute '{}' the first time. Added to users' list.");
      log.log(Level.DEBUG, "User {} is trying to execute '{}' the first time. Added to users' list.");
      sb.append("Hi, ").append(user.getUserName()).append("! You've been added to bot users' list!\n")
          .append("Please execute command:\n'/set_name <displayed_name>'\nwhere &lt;displayed_name&gt; is the name you want to use to hide your real name.");
    } else {
      log.log(Level.DEBUG, "User " + user.getId() + " has already executed '{}'. Is he trying to do it one more time?");
      sb.append("You've already started bot! You can send messages if you set your name (/set_name).");
    }

    message.setText(sb.toString());
    execute(absSender, message, user);
  }
}
