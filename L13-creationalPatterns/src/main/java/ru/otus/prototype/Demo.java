package ru.otus.prototype;

public class Demo {
  public static void main(String[] args) throws CloneNotSupportedException {
    copyExample();
    cloneExample();
  }

  /**
   * Custom copy()
   */
  private static void copyExample() {
    System.out.println("======== copyExample ========");
    CopyableSheep original = new CopyableSheep("unknown");

    CopyableSheep copied = original.copy();
    copied.setName("Dolly");

    System.out.println("original = " + original);
    System.out.println("copied = " + copied);
    System.out.println("original.getName() = " + original.getName());
    System.out.println("copied.getName() = " + copied.getName());
    System.out.println();
  }

  /**
   * Стандартный clone()
   * @throws CloneNotSupportedException
   */
  private static void cloneExample() throws CloneNotSupportedException {
    System.out.println("======== cloneExample ========");

    ClonableSheep original = new ClonableSheep("unknown");

    ClonableSheep cloned = original.clone();
    cloned.setName("Dolly");

    System.out.println("original = " + original);
    System.out.println("cloned = " + cloned);
    System.out.println("original.getName() = " + original.getName());
    System.out.println("cloned.getName() = " + cloned.getName());
    System.out.println();
  }


}
