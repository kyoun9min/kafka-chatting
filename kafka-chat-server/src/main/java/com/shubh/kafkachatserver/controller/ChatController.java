package com.shubh.kafkachatserver.controller;

import com.shubh.kafkachatserver.model.dto.request.MessageRequest;
import com.shubh.kafkachatserver.model.dto.response.MessageResponse;
import com.shubh.kafkachatserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping(value = "/api/send", consumes = "application/json", produces = "application/json")
    public void sendMessage(@RequestBody MessageRequest messageRequest) {
        chatService.sendMessage(messageRequest);
    }

    @GetMapping("/data")
    public ResponseEntity<List<MessageResponse>> getMessages(@RequestParam Long lastMessageId,
                                             @RequestParam int size,
                                             @RequestParam Long roomId) {

//        MessagePage<MessageResponse> messagePage = chatService.fetchMessagePagesBy(lastMessageId, size, roomId);
//        return messagePage;
        List<MessageResponse> messageResponses = chatService.fetchMessagePagesBy(lastMessageId, size, roomId);
        return new ResponseEntity<>(messageResponses, HttpStatus.OK);
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
//    @MessageMapping("/sendMessage")
//    @SendTo("/topic/group")
//    public Message broadcastGroupMessage(@Payload Message message) {
//        //Sending this message to all the subscribers
//        return message;
//    }
//
//    @MessageMapping("/newUser")
//    @SendTo("/topic/group")
//    public Message addUser(@Payload Message message, SimpMessageHeaderAccessor headerAccessor) {
//        // Add user in web socket session
//        headerAccessor.getSessionAttributes().put("username", message.getSender());
//        return message;
//    }

}
