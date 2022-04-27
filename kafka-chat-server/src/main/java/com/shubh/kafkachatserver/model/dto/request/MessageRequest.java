package com.shubh.kafkachatserver.model.dto.request;

import com.shubh.kafkachatserver.model.entity.Message;
import com.shubh.kafkachatserver.model.enumclass.MessageType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MessageRequest {

    private Long id;

    private MessageType type;

    private Long roomId;

    private String sender;

    private String content;

    private String timestamp;

    public Message toEntity() {
        return Message.builder()
                .type(this.type)
                .roomId(this.roomId)
                .sender(this.sender)
                .content(this.content)
                .timestamp(this.timestamp)
                .build();
    }

    public void parsing() {
        this.timestamp = LocalDateTime.now().toString();
        if(this.type == MessageType.ENTER){
            this.content = this.sender + "님이 입장하셨습니다.";
            this.sender = "[알림]";
        }
        else if(this.type == MessageType.EXIT) {
            this.content = this.sender + "님이 퇴장하셨습니다.";
            this.sender = "[알림]";
        }
    }
}
