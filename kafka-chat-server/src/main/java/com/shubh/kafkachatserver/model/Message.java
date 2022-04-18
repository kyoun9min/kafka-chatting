package com.shubh.kafkachatserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Message {

    public enum MessageType {
        ENTER, TALK, EXIT
    }

    private MessageType type;
    private Long roomId;
    private String sender;
    private String content;
    private String timestamp;
}
