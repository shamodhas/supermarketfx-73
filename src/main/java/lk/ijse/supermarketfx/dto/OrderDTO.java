package lk.ijse.supermarketfx.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 4/28/2025 4:18 PM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDTO {
    private String orderId;
    private String customerId;
    private Date orderDate;
    private ArrayList<CartDTO> cartList;
}
