package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.dto.CartDTO;
import lk.ijse.supermarketfx.util.CrudUtil;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 4/21/2025 10:28 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class OrderDetailsModel {
    private final ItemModel itemModel = new ItemModel();

    public boolean saveOrderDetailsLIst(ArrayList<CartDTO> cartList) throws SQLException {
        for (CartDTO cartDTO : cartList) {
            boolean isOrderDetailsSaved = saveOrderDetail(cartDTO);
            if (!isOrderDetailsSaved) {
                return false;
            }

            boolean isItemUpdated = itemModel.reduceQty(cartDTO);
            if (!isItemUpdated) {
                return false;
            }


        }
        return true;
    }

    private boolean saveOrderDetail(CartDTO cartDTO) throws SQLException {
        return CrudUtil.execute(
                "insert into order_details values (?,?,?,?)",
                cartDTO.getOrderId(),
                cartDTO.getItemId(),
                cartDTO.getQty(),
                cartDTO.getUnitPrice()
        );
    }
}
