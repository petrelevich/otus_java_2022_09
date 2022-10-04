package ru.otus.testing.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static ru.otus.testing.exercise.DefaultMessageTemplateProvider.DEFAULT_TEMPLATE;

class Solution2_MessageBuilderImplTest {

    public static final String DEFAULT_MESSAGE_TEXT = "defaultText";
    public static final String DEFAULT_SIGNATURE = "defaultSignature";

    private MessageTemplateProvider provider;
    private MessageBuilder messageBuilder;

    @BeforeEach
    void setUp(){
        provider = Mockito.mock(MessageTemplateProvider.class);
        //provider = Mockito.spy(new DefaultMessageTemplateProvider());
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
}