package ru.otus.factories.factorymethod;

import ru.otus.factories.simplefactory.Configuration;

public class DemoFactoryMethod {
  public static void main(String[] args) {
    // Пример:
    // У нас есть какая-то конфигурация
    // и мы хотим читать ее их разных мест (БД, файл и тд)

   // из файла
    //   каким-то образом получаем фабрику (как в предыдущем примере - простая фабрика в виде статического метода)
    ConfigurationFactory factory1 = ConfigurationFactory.getConfigurationFactory("file");
    // получаем конфигурацию
    Configuration config1 = factory1.buildConfiguration();
    readData(config1);

    // из БД
    ConfigurationFactory factory2 = ConfigurationFactory.getConfigurationFactory("db");
    Configuration config2 = factory2.buildConfiguration();
    readData(config2);

    // или еще откуда-то ...
  }

  private static void readData(Configuration config) {
    System.out.println(config.params());
  }
}
