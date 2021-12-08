package com.stock.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stock.dto.internalTypes.Status;
import com.stock.dto.internalTypes.Type;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notification")
public class NotificationDto {

    private Status status;
    private Type type;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp created;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp modified;

    private String createdBy;
    private String modifiedBy;

}
