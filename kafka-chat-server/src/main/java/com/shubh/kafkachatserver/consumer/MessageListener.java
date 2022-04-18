package com.shubh.kafkachatserver.consumer;

import com.shubh.kafkachatserver.constants.KafkaConstants;
import com.shubh.kafkachatserver.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageListener {

    private final SimpMessagingTemplate template;

    // 메시지가 토픽에 오면 springboot가 자동으로 메소드 호출
    @KafkaListener(
        topics = KafkaConstants.KAFKA_TOPIC,
        groupId = KafkaConstants.GROUP_ID
    )
    public void listen(Message message) {
        log.info("sending via kafka listener..");
        template.convertAndSend("/topic/" + message.getRoomId(), message);
    }
}
