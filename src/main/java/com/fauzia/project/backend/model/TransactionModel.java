package com.fauzia.project.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Setter
@Getter
public class TransactionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int transactionId;

    @Column(name = "travel_package_id")
    private int travelPackageId;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "transaction_total")
    private int transactionTotal;

    @Column(name = "transaction_status")
    private String transactionStatus;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy hh:mm:ss", shape = JsonFormat.Shape.STRING)
    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "create_by")
    private int createBy;

    @Column(name = "update_by")
    private int updateBy;
}
