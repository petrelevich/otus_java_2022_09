package ru.otus.testing.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static ru.otus.testing.exercise.DefaultMessageTemplateProvider.DEFAULT_TEMPLATE;

class Solution3_MessageBuilderImplTest {

    public static final String DEFAULT_TEMPLATE_NAME = "defaultTemplate";
    public static final String DEFAULT_MESSAGE_TEXT = "defaultText";
    public static final String DEFAULT_SIGNATURE = "defaultSignature";

    private MessageTemplateProvider provider;
    private MessageBuilder messageBuilder;

    @BeforeEach
    void setUp(){
        provider = Mockito.mock(MessageTemplateProvider.class);
        messageBuilder = new MessageBuilderImpl(provider);
    }

    @Test
    void buildMessageTest1() {
        Mockito.when(provider.getMessageTemplate(Mockito.any())).thenReturn(DEFAULT_TEMPLATE);

        String expectedMessage = String.format(DEFAULT_TEMPLATE, DEFAULT_MESSAGE_TEXT,
                DEFAULT_SIGNATURE);

        String actualMessage = messageBuilder.buildMessage(null, DEFAULT_MESSAGE_TEXT,
                DEFAULT_SIGNATURE);

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void buildMessageTest2() {
        Mockito.when(provider.getMessageTemplate(DEFAULT_TEMPLATE_NAME)).thenReturn(" ");
        messageBuilder.buildMessage(DEFAULT_TEMPLATE_NAME, null, null);
        Mockito.verify(provider, Mockito.times(1)).getMessageTemplate(DEFAULT_TEMPLATE_NAME);
    }
}