package com.stock.job;

import com.stock.dto.CompanyDto;
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

    @Value("${cloud.aws.end-point.uri}")
    private String endPoint;

    @Async  // TODO check possibility
    public void sendMessageToSQS(List<CompanyDto> updatedCompanies) {
        for (CompanyDto updatedCompany : updatedCompanies) {
            queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(updatedCompany.getName()).build());
            log.info("==== Message {} was send into SQS ====", updatedCompany.getName());
        }
    }
}
