package com.stock.repository;

import com.stock.dto.NotificationDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<NotificationDto, String> {
}
