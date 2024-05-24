package org.crm.crmproject.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class CustomerSecurityDTO extends User { // Security 용도로만 사용되는 DTO
    private Long customerNo;

    private String customerId;

    private String customerPw;

    private String customerName;

    private String customerGender;

    private String customerEmail;

    private String customerPhone;

    private String customerNick;


    public CustomerSecurityDTO(Long customerNo, String username, String password, String customerName, String customerGender,
                               String customerEmail, String customerPhoneNum, String customerNick,
                               Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.customerNo = customerNo;
        this.customerId = username;
        this.customerPw = password;
        this.customerName = customerName;
        this.customerGender = customerGender;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhoneNum;
        this.customerNick = customerNick;

    }
}
