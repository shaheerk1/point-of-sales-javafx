package lk.target.dto.tm;

/*
    @author DanujaV
    @created 3/20/23 - 1:25 PM   
*/

import javafx.scene.control.Button;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PlaceOrderTM {
    private String code;
    private String name;
    private String description;
    private Integer qty;
    private Double price;
    private Double total;
    private Button action;
}
