package lk.ijse.supermarketfx.model;

import lk.ijse.supermarketfx.dto.CartDTO;
import lk.ijse.supermarketfx.dto.ItemDTO;
import lk.ijse.supermarketfx.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 4/21/2025 10:27 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

public class ItemModel {
    public ArrayList<String> getAllItemIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select item_id from item");
        ArrayList<String> list = new ArrayList<>();
        while (rst.next()) {
            String id = rst.getString(1);
            list.add(id);
        }
        return list;
    }

    public ItemDTO findById(String selectedItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute(
                "select * from item where item_id=?",
                selectedItemId
        );
        if (rst.next()) {
            return new ItemDTO(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getInt(3),
                    rst.getDouble(4)
            );
        }
        return null;
    }

    public boolean reduceQty(CartDTO cartDTO) throws SQLException {
        return CrudUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                cartDTO.getQty(),
                cartDTO.getItemId()
        );
    }
}
