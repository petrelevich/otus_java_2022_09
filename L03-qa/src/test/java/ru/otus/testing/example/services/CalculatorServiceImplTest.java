package ru.otus.testing.example.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.time.Duration;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;

@DisplayName("Сервис CalculatorService должен ")
public class CalculatorServiceImplTest {

    private static final String FIRST_DIGIT = "53";
    private static final String SECOND_DIGIT = "61";
    private static final String OUTPUT_RESULT = "53 * 61 = 3233";

    private InOrder inOrder;
    private IOService ioService;
    private CalculatorService calculatorService;

    private static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of("Поступило предложение умножить два числа", 11, 8),
                Arguments.of("Умножение всему голова", 13, 54),
                Arguments.of("Умножать или не умножать? Вот в чем вопрос. Конечно же умножать!", 77, 32)
        );
    }

    @BeforeEach
    public void setUp() {
        ioService = mock(IOService.class);
        calculatorService = new CalculatorServiceImpl(ioService);
        inOrder = inOrder(ioService);
    }

    @DisplayName("прочитать два числа, перемножить их и вывести результат ")
    @Test
    void shouldReadTwoDigitsMultiplyAndOutputResultWithIOService() {
        given(ioService.readString()).willReturn(FIRST_DIGIT).willReturn(SECOND_DIGIT);
        calculatorService.readTwoDigitsAndMultiply();
        inOrder.verify(ioService, times(2)).readString();
        inOrder.verify(ioService, times(1)).out(OUTPUT_RESULT);
    }

    @DisplayName("вывести подсказку, прочитать два числа, перемножить их и вывести результат ")
    @ParameterizedTest(name = "Тестирование с подсказкой: \"{0}\"")
    @ValueSource(strings = {"Введите числа и мы их перемножим", "Мы перемножим числа, что вы введете"})
    void shouldDisplayPromptReadTwoDigitsMultiplyAndOutputResultWithIOService(String prompt) {
        given(ioService.readString()).willReturn(FIRST_DIGIT).willReturn(SECOND_DIGIT);
        calculatorService.readTwoDigitsAndMultiply(prompt);
        inOrder.verify(ioService, times(1)).out(prompt);
        inOrder.verify(ioService, times(2)).readString();
        inOrder.verify(ioService, times(1)).out(OUTPUT_RESULT);
    }

    @DisplayName("выкинуть NumberFormatException если вместо числа ввели строку")
    @Test
    void shouldThrowNumberFormatExceptionWhenEnteredIsNotANumber() {
        given(ioService.readString()).willReturn("");
        assertThrows(NumberFormatException.class, () -> calculatorService.readTwoDigitsAndMultiply());
    }

    @DisplayName("вывести подсказку, перемножить заданные два числа и вывести результат")
    @ParameterizedTest
    @MethodSource("generateData")
    void shouldDisplayPromptMultiplyTwoGivenDigitsAndOutputResultWithIOService(String prompt, int d1, int d2) {
        calculatorService.multiplyTwoDigits(prompt, d1, d2);
        inOrder.verify(ioService, times(1)).out(prompt);
        inOrder.verify(ioService, times(1)).out(String.format("%d * %d = %d", d1, d2, d1 * d2));
    }

    @DisplayName("вывести ответ на главный вопрос жизни, вселенной и всего такого не менее, чем за 5 сек")
    @Test
    void shouldDisplayAnswerForMainQuestion() {
        assertTimeout(Duration.ofSeconds(6), () -> calculatorService.longCalculations());
    }

}
