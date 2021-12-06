package com.stock.service;

import com.stock.dto.NotificationDto;
import com.stock.dto.internalTypes.Status;
import com.stock.dto.internalTypes.Type;
import com.stock.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @SqsListener("stock-load")
    public void loadMessageFromSQS(String message) {
        log.info(">>>> Message from SQS : {}", message);
        NotificationDto notification = NotificationDto.builder()
                .status(Status.NEW)
                .type(Type.INFO)
                .created(LocalDate.now())
                .createdBy(message)
                .modified(LocalDate.now())
                .modifiedBy(message)
                .build();


        notificationRepository.save(notification);
    }

}
