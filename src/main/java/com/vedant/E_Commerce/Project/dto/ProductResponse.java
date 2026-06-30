package com.vedant.E_Commerce.Project.dto;

import lombok.Data;

@Data
public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private double price;

    private String category;

    private String imageUrl;
}
