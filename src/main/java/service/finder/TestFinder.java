package service.finder;

public class TestFinder<T> {

  public void printSearchObjData(IFind<T> finder) {
    T obj = finder.search();

    System.out.println(obj.toString());
  }

}
