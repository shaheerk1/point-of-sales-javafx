package lk.target.dto;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ItemDTO {
    private String itemCode;
    private String name;
    private String description;
    private int qty;
    private double price;
}
