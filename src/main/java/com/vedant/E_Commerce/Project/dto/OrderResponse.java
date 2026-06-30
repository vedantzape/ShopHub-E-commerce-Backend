package com.vedant.E_Commerce.Project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {

    private Long id;

    private double totalAmount;

    private String status;

    private LocalDateTime orderDate;
}
