package org.crm.crmproject.dto;

import lombok.Data;
import org.crm.crmproject.domain.Role;

import java.util.Set;

@Data
public class CustomerDTO {
    private Long customerNo;

    private String customerId;

    private String customerPw;

    private String customerName;

    private String customerGender;

    private String customerEmail;

    private String customerPhone;

    private String customerNick;

    private Set<Role> roleSet;
}
