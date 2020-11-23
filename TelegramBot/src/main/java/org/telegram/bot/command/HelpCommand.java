package org.telegram.bot.command;

import org.apache.log4j.Level;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/**
 * /help — выведет пользователю информацию обо всех доступных командах
 * (конструктор отличается от других тем, что в него необходимо передать ICommandRegistry,
 * который содержит все кастомные команды);
 */
public final class HelpCommand extends AnonymizerCommand {

  private final ICommandRegistry tgCommandRegistry;

  public HelpCommand(ICommandRegistry commandRegistry) {
    super("help", "list all known commands\n");
    tgCommandRegistry = commandRegistry;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    log.info(user.getId() + " " + getCommandIdentifier());

    StringBuilder helpMessageBuilder = new StringBuilder("<b>Available commands:</b>");

    tgCommandRegistry.getRegisteredCommands().forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

    SendMessage helpMessage = new SendMessage();
    helpMessage.setChatId(chat.getId().toString());
    helpMessage.enableHtml(true);
    helpMessage.setText(helpMessageBuilder.toString());

    execute(absSender, helpMessage, user);
  }

}
