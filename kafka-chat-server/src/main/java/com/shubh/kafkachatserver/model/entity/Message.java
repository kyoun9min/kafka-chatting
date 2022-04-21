package com.shubh.kafkachatserver.model.entity;

import com.shubh.kafkachatserver.model.enumclass.MessageType;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type;

    private Long roomId;

    private String sender;

    private String content;

    private String timestamp;

    @Builder
    public Message(MessageType type, Long roomId, String sender, String content, String timestamp) {
        this.type = type;
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
        this.timestamp = timestamp;
    }
}
