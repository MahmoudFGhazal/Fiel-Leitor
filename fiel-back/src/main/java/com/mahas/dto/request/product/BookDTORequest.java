package com.mahas.dto.request.product;

import java.math.BigDecimal;
import java.util.List;

import com.mahas.dto.request.DTORequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTORequest implements DTORequest {

    private Integer id;

    private String name;
    private String author;
    private String publisher;
    private String edition;
    private Integer year;

    private String isbn;
    private String barcode;
    private String synopsis;
    private Integer pages;

    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal depth;
    private BigDecimal weight;

    private BigDecimal price;
    private Integer stock;
    private Boolean active;

    private Integer priceGroupId;

    private List<Integer> categories; 
}