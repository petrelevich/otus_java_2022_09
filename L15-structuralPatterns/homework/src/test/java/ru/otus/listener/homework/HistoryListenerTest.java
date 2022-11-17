package ru.otus.listener.homework;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class HistoryListenerTest {

    @Test
    @Disabled //надо удалить
    void listenerTest() {
        //given
        var historyListener = new HistoryListener();

        var id = 100L;
        var data = "33";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        var message = new Message.Builder(id)
                .field10("field10")
//TODO: раскоментировать       .field13(field13)
                .build();

        //when
        historyListener.onUpdated(message);
//TODO: раскоментировать        message.getField13().setData(new ArrayList<>()); //меняем исходное сообщение
//TODO: раскоментировать        field13Data.clear(); //меняем исходный список

        //then
        var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
//TODO: раскоментировать        assertThat(messageFromHistory.get().getField13().getData()).containsExactly(data);
    }
}