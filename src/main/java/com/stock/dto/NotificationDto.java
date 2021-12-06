package com.stock.dto;

import com.stock.dto.internalTypes.Status;
import com.stock.dto.internalTypes.Type;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "notification")
public class NotificationDto {

    private Status status;
    private Type type;

    private LocalDate created;
    private LocalDate modified;

    private String createdBy;
    private String modifiedBy;

}
