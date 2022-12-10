package ru.otus.factories.factorymethod;

import ru.otus.factories.simplefactory.Configuration;
import ru.otus.factories.simplefactory.ConfigurationDB;

class ConfigurationFactoryDB extends ConfigurationFactory {
  @Override
  Configuration buildConfiguration() {
    return new ConfigurationDB();
  }
}
