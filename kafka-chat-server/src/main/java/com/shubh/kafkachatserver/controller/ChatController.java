package com.shubh.kafkachatserver.controller;

import com.shubh.kafkachatserver.constants.KafkaConstants;
import com.shubh.kafkachatserver.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final KafkaTemplate<String, Message> kafkaTemplate;

    @PostMapping(value = "/api/send/{roomId}", consumes = "application/json", produces = "application/json")
    public void sendMessage(@PathVariable Long roomId, @RequestBody Message message) {

        message.setTimestamp(LocalDateTime.now().toString());
        message.setRoomId(roomId);
        if(message.getType() == Message.MessageType.ENTER){
            message.setContent(message.getSender() + "님이 입장하셨습니다.");
        }
        else if(message.getType() == Message.MessageType.EXIT) {
            message.setContent(message.getSender() + "님이 퇴장하셨습니다.");
        }
        try {
            // post 요청으로 전달받은 메시지를 해당 카프카 토픽에 생산
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    //    -------------- WebSocket API ----------------
    /*
    * @MessageMapping - 발행 경로
    * @SendTo - 구독 경로
    * 예를 들어 @MessageMapping("/chat"), @SendTo("/topic/message") 에서
    * 특정 사용자가 /chat 이라는 경로로 메세지를 보내면 /topic/message 라는 토픽을 구독하는 사용자들에게 모두 메세지를  뿌리는 것이다.
    * @SendTo는 1:n 으로 메세지를 뿌릴 때 사용하는 구조이며 보통 경로가 /topic 으로 시작한다.
    * @SendToUser는 1:1 로 메세지를 보낼 때 사용하는 구조이며 보통 경로가 /queue 로 시작한다.
    */
//    @MessageMapping("/sendMessage/{roomId}")
//    @SendTo("/topic/group/{roomId}")
//    public Message broadcastGroupMessage(@Payload Message message) {
//        //Sending this message to all the subscribers
//        return message;
//    }
//
//    @MessageMapping("/newUser/{roomId}")
//    @SendTo("/topic/group/{roomId}")
//    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
//        // Add user in web socket session
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        return message;
//    }

}
