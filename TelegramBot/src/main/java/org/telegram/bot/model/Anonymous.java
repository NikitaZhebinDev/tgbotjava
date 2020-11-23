package org.telegram.bot.model;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

public final class Anonymous {

  private static final Logger LOG = LogManager.getLogger(Anonymous.class);
  private static final String USER_CHAT_CANNOT_BE_NULL = "User or chat cannot be null!";

  private final User tgUser;
  private final Chat tgChat;
  private String tgDisplayedName;

  public Anonymous(User user, Chat chat) {
    if (user == null || chat == null) {
      LOG.error(USER_CHAT_CANNOT_BE_NULL);
      throw new IllegalStateException(USER_CHAT_CANNOT_BE_NULL);
    }
    tgUser = user;
    tgChat = chat;
  }

  @Override
  public int hashCode() {
    return tgUser.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof Anonymous && ((Anonymous) obj).getUser().equals(tgUser);
  }

  public User getUser() {
    return tgUser;
  }

  public Chat getChat() {
    return tgChat;
  }

  public String getDisplayedName() {
    return tgDisplayedName;
  }

  public void setDisplayedName(String displayedName) {
    tgDisplayedName = displayedName;
  }
}
