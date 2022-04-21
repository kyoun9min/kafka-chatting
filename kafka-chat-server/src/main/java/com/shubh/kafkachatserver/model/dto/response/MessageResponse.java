package com.shubh.kafkachatserver.model.dto.response;

import com.shubh.kafkachatserver.model.entity.Message;
import com.shubh.kafkachatserver.model.enumclass.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageResponse {

    private Long id;

    private MessageType type;

    private Long roomId;

    private String sender;

    private String content;

    private String timestamp;

    public static MessageResponse from(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .type(message.getType())
                .roomId(message.getRoomId())
                .sender(message.getSender())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}
