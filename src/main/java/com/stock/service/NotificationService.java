package com.stock.service;

import com.amazonaws.util.json.Jackson;
import com.stock.dto.NotificationDto;
import com.stock.dto.internalTypes.Status;
import com.stock.dto.internalTypes.Type;
import com.stock.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @SqsListener("stock-load")
    public void loadMessageFromSQS(String jsonMessage) {
        log.info(">>>> Message from SQS : {}", jsonMessage);
        NotificationDto notification = Jackson.fromJsonString(jsonMessage, NotificationDto.class);
        notificationRepository.save(notification);
    }

    public NotificationDto createNotification(Status status, Type type, String message) {
        return NotificationDto.builder()
                .status(status)
                .type(type)
                .created(new Timestamp(System.nanoTime()))
                .createdBy(message)
                .modified(new Timestamp(System.nanoTime()))
                .modifiedBy(message)
                .build();
    }

}
