package lk.target.dto.tm;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class ItemTM {
    private String itemCode;
    private String name;
    private String description;
    private int qty;
    private double price;
}
