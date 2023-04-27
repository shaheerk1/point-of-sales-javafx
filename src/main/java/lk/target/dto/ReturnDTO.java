package lk.target.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReturnDTO {
    private String id;
    private String itemCode;
    private String orderId;
    private int qty;
}
