package com.shubh.kafkachatserver.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessagePage {

    private List<MessageResponse> messages;

    private int size;
}
