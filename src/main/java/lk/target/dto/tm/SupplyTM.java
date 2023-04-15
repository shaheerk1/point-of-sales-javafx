package lk.target.dto.tm;

import javafx.scene.control.Button;
import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class SupplyTM {
    private String code;
    private String name;
    private String description;
    private Integer qty;
    private Double price;
    private Double total;
    private Button action;
}

