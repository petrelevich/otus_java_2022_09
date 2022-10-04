package ru.otus.testing.exercise;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static ru.otus.testing.exercise.DefaultMessageTemplateProvider.DEFAULT_TEMPLATE;

@DisplayName("Класс MessageBuilderImpl")
class Solution5_MessageBuilderImplTest {

    public static final String DEFAULT_TEMPLATE_NAME = "defaultTemplate";
    public static final String DEFAULT_MESSAGE_TEXT = "defaultText";
    public static final String DEFAULT_SIGNATURE = "defaultSignature";

    private MessageTemplateProvider provider;
    private MessageBuilder messageBuilder;

    @BeforeEach
    void setUp() {
        provider = Mockito.mock(MessageTemplateProvider.class);
        messageBuilder = new MessageBuilderImpl(provider);
    }

    @DisplayName("должен создать верное сообщение для заданного шаблона, текста и подписи")
    @Test
    void shouldBuildCorrectMessageForGivenTemplateByTextAndSign() {
        BDDMockito.given(provider.getMessageTemplate(Mockito.any())).willReturn(DEFAULT_TEMPLATE);

        String expectedMessage = String.format(DEFAULT_TEMPLATE, DEFAULT_MESSAGE_TEXT,
                DEFAULT_SIGNATURE);

        String actualMessage = messageBuilder.buildMessage(null, DEFAULT_MESSAGE_TEXT,
                DEFAULT_SIGNATURE);

        Assertions.assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @DisplayName("должен единожды вызывать нужный метод зависимости при создании сообщения")
    @Test
    void shouldFireOnceExpectedDependencyMethodWhenBuildMessage() {
        BDDMockito.given(provider.getMessageTemplate(DEFAULT_TEMPLATE_NAME)).willReturn(" ");
        messageBuilder.buildMessage(DEFAULT_TEMPLATE_NAME, null, null);
        Mockito.verify(provider, Mockito.times(1)).getMessageTemplate(DEFAULT_TEMPLATE_NAME);
    }

    @DisplayName("должен кидать нужное исключение, когда зависимость возвращает null вместо шаблона")
    @Test
    void shouldThrowExpectedExceptionWhenDependencyReturnNullInsteadOfTemplate() {
        Assertions.assertThatThrownBy(() -> messageBuilder.buildMessage(null, null, null))
                .isInstanceOf(TemplateNotFoundException.class);
    }
}