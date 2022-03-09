package com.tabeldata.bootcamp.model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class Product {
    @NotNull
    private Integer id ;
    @NotEmpty
    @Length(min = 4,message = "panjang minimal 4")
    private String name;

    @Min(value = 0, message = "harga tidak boleh kosong")
    private BigDecimal price;

    @NotNull
    private String category;

    private LocalDate create_date;
    private String create_by;
}
