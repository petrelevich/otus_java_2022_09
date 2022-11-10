package ru.otus.singleton;

/**
 * @author sergey
 * created on 19.09.18.
 */
public class Singleton {
  private Singleton() {
    System.out.println("lazy creation");
  }

  static Singleton getInstance() {
    System.out.println("getInstance");
    return SingletonHolder.instance;
  }

  private static class SingletonHolder {
    static {
      System.out.println("init SingletonHolder");
    }

    static final Singleton instance = new Singleton();
  }
}

class SingletonDemo {
  public static void main(String[] args) {
    System.out.println("--- begin ---");
    System.out.println("- singleton 1");
    Singleton singleton1 = Singleton.getInstance();

    System.out.println();
    System.out.println("- singleton 2");
    Singleton singleton2 = Singleton.getInstance();

    System.out.println();
    System.out.printf("singleton1 == singleton2 => %b\n", singleton1 == singleton2);
    System.out.println(singleton1);
    System.out.println(singleton2);
    System.out.println("---end ---");
  }
}