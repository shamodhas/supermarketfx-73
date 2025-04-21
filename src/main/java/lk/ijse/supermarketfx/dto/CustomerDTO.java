package lk.ijse.supermarketfx.dto;

import lombok.*;

/**
 * --------------------------------------------
 * Author: Shamodha Sahan
 * GitHub: https://github.com/shamodhas
 * Website: https://shamodha.com
 * --------------------------------------------
 * Created: 4/21/2025 11:05 AM
 * Project: SupermarketFX
 * --------------------------------------------
 **/

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CustomerDTO {
    private String customerId;
    private String name;
    private String nic;
    private String email;
    private String phone;

//    @NoArgsConstructor
//    public CustomerDTO() {
//    }

    //    @AllArgsConstructor
//    public CustomerDTO(String customerId, String name, String nic, String email, String phone) {
//        this.customerId = customerId;
//        this.name = name;
//        this.nic = nic;
//        this.email = email;
//        this.phone = phone;
//    }
}
