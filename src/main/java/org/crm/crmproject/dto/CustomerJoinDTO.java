package org.crm.crmproject.dto;

import lombok.Data;

@Data
public class CustomerJoinDTO {
    private Long customerNo;

    private String customerId;

    private String customerPw;

    private String customerName;

    private String customerGender;

    private String customerEmail;

    private String customerPhone;

    private String customerNick;
}
