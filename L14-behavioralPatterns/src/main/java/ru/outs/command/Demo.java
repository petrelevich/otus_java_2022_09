package ru.outs.command;

public class Demo {
  public static void main(String[] args) {
    var object = new SomeObject("initVal");
    var executor = new Executor(object);

    // нужную операцию выделяем в отдельный класс
    executor.addCommand(new AdderABC());
    executor.addCommand(new Echo());
    executor.addCommand(new AdderABC());

    // при необходимости можно выполнить позднее
    executor.executeCommands();

    System.out.println("result object:" + object);
  }
}
