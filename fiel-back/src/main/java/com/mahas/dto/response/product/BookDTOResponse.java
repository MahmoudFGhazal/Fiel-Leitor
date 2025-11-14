package com.mahas.dto.response.product;

import java.math.BigDecimal;
import java.util.List;

import com.mahas.domain.DomainEntity;
import com.mahas.domain.product.Book;
import com.mahas.dto.response.DTOResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTOResponse implements DTOResponse {

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

    private PriceGroupDTOResponse priceGroup;
    private List<CategoryDTOResponse> categories;

    @Override
    public void mapFromEntity(DomainEntity entity) {
        if (entity instanceof Book b) {
            this.id = b.getId();

            this.name = b.getName();
            this.author = b.getAuthor();
            this.publisher = b.getPublisher();
            this.edition = b.getEdition();
            this.year = b.getYear();
            this.isbn = b.getIsbn();
            this.barcode = b.getBarcode();
            this.synopsis = b.getSynopsis();
            this.pages = b.getPages();

            this.height = b.getHeight();
            this.width = b.getWidth();
            this.depth = b.getDepth();
            this.weight = b.getWeight();

            this.price = b.getPrice();
            this.stock = b.getStock();
            this.active = b.getActive();

            if (b.getPriceGroup() != null) {
                this.priceGroup = new PriceGroupDTOResponse();
                this.priceGroup.mapFromEntity(b.getPriceGroup());
            }

            if (b.getCategories() != null) {
                this.categories = b.getCategories()
                    .stream()
                    .map(cat -> {
                        CategoryDTOResponse dto = new CategoryDTOResponse();
                        dto.mapFromEntity(cat);
                        return dto;
                    })
                    .toList();
            }
        }
    }
}