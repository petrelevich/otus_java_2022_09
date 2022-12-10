package ru.otus.factories.factorymethod;

import ru.otus.factories.simplefactory.Configuration;
import ru.otus.factories.simplefactory.ConfigurationFile;

class ConfigurationFactoryFile extends ConfigurationFactory  {
  @Override
  Configuration buildConfiguration() {
    return new ConfigurationFile();
  }
}
