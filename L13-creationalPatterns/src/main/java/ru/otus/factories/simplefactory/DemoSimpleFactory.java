package ru.otus.factories.simplefactory;

/**
 * @author sergey
 * created on 19.09.18.
 * @author spv
 * edited 28.08.20.
 */
public class DemoSimpleFactory {
    public static void main(String[] args) {
        // Пример:
        // У нас есть какая-то конфигурация
        // и мы читаем ее из файла - ConfigurationFile
        ConfigurationFile configFile = new ConfigurationFile();
        readDataBad(configFile);

        // а потом появляется необходимость читать из БД
        ConfigurationDB configDb = new ConfigurationDB();
        readDataBad(configDb);

        // в readDataBad() дублирование кода -
        // выделяем интерфейс и получаем readData()
        Configuration config1 = new ConfigurationFile();
        Configuration config2 = new ConfigurationDB();
        readData(config1);
        readData(config2);

        // но код создания придется дублировать везде - создаем фабрику
        // инкапсулируем логику создания в одном месте

        // Простая фабрика (simple factory) или статический фабричный метод (static factory method)
        // (не совсем фабричный метод из GoF)

        // из файла
        Configuration config3 = ConfigurationFactory.getConfiguration("file");
        readData(config3);

        // или из БД
        Configuration config4 = ConfigurationFactory.getConfiguration("db");
        readData(config4);

        // если надо добавить чтение конфигурации
        // еще откуда-то, то это надо будет сделать в одном месте
        // в ConfigurationFactory.getConfiguration()
    }

    private static void readDataBad(ConfigurationFile config) {
        System.out.println(config.params());
    }

    private static void readDataBad(ConfigurationDB config) {
        System.out.println(config.params());
    }

    private static void readData(Configuration config) {
        System.out.println(config.params());
    }

}
