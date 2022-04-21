package com.shubh.kafkachatserver.service;

import com.shubh.kafkachatserver.constants.KafkaConstants;
import com.shubh.kafkachatserver.model.dto.request.MessageRequest;
import com.shubh.kafkachatserver.model.dto.response.MessagePage;
import com.shubh.kafkachatserver.model.dto.response.MessageResponse;
import com.shubh.kafkachatserver.model.entity.Message;
import com.shubh.kafkachatserver.model.enumclass.MessageType;
import com.shubh.kafkachatserver.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final KafkaTemplate<String, MessageResponse> kafkaTemplate;

    private final ChatRepository chatRepository;

    public void sendMessage(MessageRequest messageRequest) {

        messageRequest.setTimestamp(LocalDateTime.now().toString());
        if(messageRequest.getType() == MessageType.ENTER){
            messageRequest.setContent(messageRequest.getSender() + "님이 입장하셨습니다.");
            messageRequest.setSender("[알림]");
        }
        else if(messageRequest.getType() == MessageType.EXIT) {
            messageRequest.setContent(messageRequest.getSender() + "님이 퇴장하셨습니다.");
            messageRequest.setSender("[알림]");
        }
        System.out.println(messageRequest.getRoomId());
        Message message = chatRepository.save(messageRequest.toEntity());
        System.out.println(message);
        try {
            // post 요청으로 전달받은 메시지를 해당 카프카 토픽에 생산
            kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, MessageResponse.from(message)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MessageResponse> fetchMessagePagesBy(Long lastMessageId, int size, Long roomId) {
        Page<Message> messages = fetchPages(lastMessageId, size, roomId);
        return messages.getContent()
                .stream()
                .map(message -> MessageResponse.from(message))
                .collect(Collectors.toList());
//        List<MessageResponse> messageResponses = messages.getContent()
//                .stream()
//                .map(message -> MessageResponse.from(message))
//                .collect(Collectors.toList());
//
//        return MessagePage.<MessageResponse>builder()
//                .messages(messageResponses)
//                .size(messageResponses.size())
//                .build();
    }

    private Page<Message> fetchPages(Long lastMessageId, int size, Long roomId) {
        PageRequest pageRequest = PageRequest.of(0, size);
        return chatRepository.findByIdLessThanAndRoomIdOrderByIdDesc(lastMessageId, roomId, pageRequest);
    }

}
