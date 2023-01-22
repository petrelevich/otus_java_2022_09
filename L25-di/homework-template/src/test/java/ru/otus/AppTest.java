package ru.otus;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.config.AppConfig;
import ru.otus.services.*;

import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

class AppTest {

    @DisplayName("Из контекста тремя способами должен корректно доставаться компонент с проставленными полями")
    @ParameterizedTest(name = "Достаем по: {0}")
    @CsvSource(value = {"GameProcessor, ru.otus.services.GameProcessor",
            "GameProcessorImpl, ru.otus.services.GameProcessor",
            "gameProcessor, ru.otus.services.GameProcessor",

            "IOService, ru.otus.services.IOService",
            "IOServiceStreams, ru.otus.services.IOService",
            "ioService, ru.otus.services.IOService",

            "PlayerService, ru.otus.services.PlayerService",
            "PlayerServiceImpl, ru.otus.services.PlayerService",
            "playerService, ru.otus.services.PlayerService",

            "EquationPreparer, ru.otus.services.EquationPreparer",
            "EquationPreparerImpl, ru.otus.services.EquationPreparer",
            "equationPreparer, ru.otus.services.EquationPreparer"
    })
    public void shouldExtractFromContextCorrectComponentWithNotNullFields(String classNameOrBeanId, Class<?> rootClass) throws Exception {
        var ctx = new AppComponentsContainerImpl(AppConfig.class);

        assertThat(classNameOrBeanId).isNotEmpty();
        Object component;
        if (classNameOrBeanId.charAt(0) == classNameOrBeanId.toUpperCase().charAt(0)) {
            Class<?> gameProcessorClass = Class.forName("ru.otus.services." + classNameOrBeanId);
            assertThat(rootClass).isAssignableFrom(gameProcessorClass);

            component = ctx.getAppComponent(gameProcessorClass);
        } else {
            component = ctx.getAppComponent(classNameOrBeanId);
        }
        assertThat(component).isNotNull();
        assertThat(rootClass).isAssignableFrom(component.getClass());

        var fields = Arrays.stream(component.getClass().getDeclaredFields())
                .filter(f -> !Modifier.isStatic(f.getModifiers()))
                .peek(f -> f.setAccessible(true))
                .collect(Collectors.toList());

        for (var field: fields){
            var fieldValue = field.get(component);
            assertThat(fieldValue).isNotNull().isInstanceOfAny(IOService.class, PlayerService.class,
                    EquationPreparer.class, PrintStream.class, Scanner.class);
        }

    }

    @DisplayName("В контексте не должно быть компонентов с одинаковым именем")
    @Test
    public void shouldNotAllowTwoComponentsWithSameName() throws Exception {
        assertThatCode(()-> new AppComponentsContainerImpl(ConfigWithTwoComponentsWithSameName.class))
                .isInstanceOf(Exception.class);
    }

    @DisplayName("При попытке достать из контекста отсутствующий или дублирующийся компонент, должно выкидываться исключение")
    @Test
    public void shouldThrowExceptionWhenContainerContainsMoreThanOneOrNoneExpectedComponents() throws Exception {
        var ctx = new AppComponentsContainerImpl(ConfigWithTwoSameComponents.class);

        assertThatCode(()-> ctx.getAppComponent(EquationPreparer.class))
                .isInstanceOf(Exception.class);

        assertThatCode(()-> ctx.getAppComponent(PlayerService.class))
                .isInstanceOf(Exception.class);
    }

    @AppComponentsContainerConfig(order = 1)
    public static class ConfigWithTwoComponentsWithSameName {
        public ConfigWithTwoComponentsWithSameName() {
        }

        @AppComponent(order = 1, name = "equationPreparer")
        public EquationPreparer equationPreparer1() {
            return new EquationPreparerImpl();
        }

        @AppComponent(order = 1, name = "equationPreparer")
        public IOService ioService() {
            return new IOServiceStreams(System.out, System.in);
        }
    }

    @AppComponentsContainerConfig(order = 1)
    public static class ConfigWithTwoSameComponents{

        @AppComponent(order = 1, name = "equationPreparer1")
        public EquationPreparer equationPreparer1() {
            return new EquationPreparerImpl();
        }

        @AppComponent(order = 1, name = "equationPreparer2")
        public EquationPreparer equationPreparer2() {
            return new EquationPreparerImpl();
        }
    }
}