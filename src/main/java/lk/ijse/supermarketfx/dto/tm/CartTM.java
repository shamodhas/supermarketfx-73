package lk.ijse.supermarketfx.dto.tm;

import javafx.scene.control.Button;
import lombok.*;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 4/28/2025 2:54 PM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CartTM {
    private String itemId;
    private String itemName;
    private int cartQty;
    private double unitPrice;
    private double total;
    private Button btnRemove;
}
