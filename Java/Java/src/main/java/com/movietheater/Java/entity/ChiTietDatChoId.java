package com.movietheater.Java.entity;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChiTietDatChoId implements Serializable {
    private String datCho;
    private String ghe;
}
