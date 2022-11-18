package service.api;

import java.util.function.Predicate;

public interface IApiService<T> {
  public boolean isItemFound(Predicate<T> predicate);
}
