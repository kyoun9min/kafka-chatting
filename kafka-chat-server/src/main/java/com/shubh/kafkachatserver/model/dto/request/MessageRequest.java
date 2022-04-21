package com.shubh.kafkachatserver.model.dto.request;

import com.shubh.kafkachatserver.model.entity.Message;
import com.shubh.kafkachatserver.model.enumclass.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MessageRequest {

    private Long id;

    private MessageType type;

    private Long roomId;

    private String sender;

    private String content;

    private String timestamp;

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Builder
    public Message toEntity() {
        return Message.builder()
                .type(this.type)
                .roomId(this.roomId)
                .sender(this.sender)
                .content(this.content)
                .timestamp(this.timestamp)
                .build();
    }
}
