package org.microservice.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.microservice.chat.service.UtilService;
import org.microservice.chat.util.dto.ChatMessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketController {
    @Autowired
    private UtilService utilService;

    @MessageMapping("/chat/{roomId}")
    @SendTo("/topic/{roomId}")
    public ChatMessageDTO chatMessage(@DestinationVariable String roomId, ChatMessageDTO message) {
        log.info(message.toString());

        //String time= utilService.getDateTimePeru("http://worldtimeapi.org/api/timezone/America/Lima").toLocalTime().toString().substring(0, 5);

        return ChatMessageDTO.builder()
                .message(message.getMessage())
                .user(message.getUser())
                .roomId(roomId)
                .dateTime("time")
                .build();
    }
}
