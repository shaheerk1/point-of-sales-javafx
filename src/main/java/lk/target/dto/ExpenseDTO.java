package lk.target.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class ExpenseDTO {
    private String ID;
    private String Description;
    private Double Amount;
    private String Type;
    private String ItemCode;
    private String SupplyID;
    private String ReturnID;
}
