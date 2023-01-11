package ru.otus.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;
import ru.otus.domain.Message;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @MessageMapping("/message.{roomId}")
    @SendTo("/topic/response.{roomId}")
    public Message getMessage(@DestinationVariable String roomId, Message message) {
        logger.info("got message:{}, roomId:{}", message, roomId);
        return new Message(HtmlUtils.htmlEscape(message.getMessageStr()));
    }

}
