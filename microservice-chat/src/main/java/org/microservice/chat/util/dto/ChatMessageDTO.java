package org.microservice.chat.util.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ChatMessageDTO {
    private String message;
    private String user;
    private String roomId;
    private String dateTime;
}
