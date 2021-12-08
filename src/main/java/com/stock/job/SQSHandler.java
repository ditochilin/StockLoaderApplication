package com.stock.job;

import com.amazonaws.util.json.Jackson;
import com.stock.dto.CompanyDto;
import com.stock.dto.NotificationDto;
import com.stock.dto.internalTypes.Status;
import com.stock.dto.internalTypes.Type;
import com.stock.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@EnableAsync
public class SQSHandler {

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;
    @Autowired
    private NotificationService notificationService;

    @Value("${cloud.aws.end-point.uri}")
    private String endPoint;

    @Async
    public void sendSuccessNotificationsToSQS(List<CompanyDto> updatedCompanies) {
        for (CompanyDto updatedCompany : updatedCompanies) {
            NotificationDto notification = createNotification(updatedCompany.getName(), Status.NEW, Type.INFO);
            String jsonNotification = Jackson.toJsonString(notification);
            queueMessagingTemplate.send(endPoint,
                    MessageBuilder
                            .withPayload(jsonNotification)
                            .build());
            log.info("==== Message {} was send into SQS ====", notification);
        }
    }

    @Async
    public void sendErrorNotificationToSQS(String message) {
        NotificationDto notification = createNotification(message, Status.NEW, Type.ERROR);
        String jsonNotification = Jackson.toJsonString(notification);
        queueMessagingTemplate.send(endPoint,
                MessageBuilder
                        .withPayload(jsonNotification)
                        .build());
        log.info("==== Error Message {} was send into SQS ====", notification);
    }

    private NotificationDto createNotification(String message, Status status, Type type) {
        return notificationService.createNotification(
          status,
          type,
          message
        );
    }
}
