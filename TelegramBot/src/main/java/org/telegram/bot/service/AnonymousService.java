package org.telegram.bot.service;

import org.telegram.bot.model.Anonymous;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public final class AnonymousService {

  private final Set<Anonymous> mAnonymouses;

  public AnonymousService() {
    mAnonymouses = new HashSet<>();
  }

  public boolean setUserDisplayedName(User user, String name) {

    if (!isDisplayedNameTaken(name)) {
      mAnonymouses.stream().filter(a -> a.getUser().equals(user)).forEach(a -> a.setDisplayedName(name));
      return true;
    }

    return false;
  }

  public boolean removeAnonymous(User user) {
    return mAnonymouses.removeIf(a -> a.getUser().equals(user));
  }

  public boolean addAnonymous(Anonymous anonymous) {
    return mAnonymouses.add(anonymous);
  }

  public boolean hasAnonymous(User user) {
    return mAnonymouses.stream().anyMatch(a -> a.getUser().equals(user));
  }

  public String getDisplayedName(User user) {

    Anonymous anonymous = mAnonymouses.stream().filter(a -> a.getUser().equals(user)).findFirst().orElse(null);

    if (anonymous == null) {
      return null;
    }
    return anonymous.getDisplayedName();
  }

  public Stream<Anonymous> anonymouses() {
    return mAnonymouses.stream();
  }

  private boolean isDisplayedNameTaken(String name) {
    return mAnonymouses.stream().anyMatch(a -> Objects.equals(a.getDisplayedName(), name));
  }
}
