package org.crm.crmproject.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
@ToString
public class CeoSecurityDTO extends User {
    private Long ceoNo;

    private String ceoId;

    private String ceoPw;

    private String ceoName;

    private String ceoEmail;

    private String ceoPhone;

    private String businessNum;

    private String storeName;

    private String storeAddress;

    public CeoSecurityDTO(Long ceoNo, String username, String password, String ceoName,
                               String ceoEmail, String ceoPhone, String businessNum,
                               String storeName, String storeAddress,
                               Collection<? extends GrantedAuthority> authorities) {

        super(username, password, authorities);

        this.ceoNo = ceoNo;
        this.ceoId = username;
        this.ceoPw = password;
        this.ceoName = ceoName;
        this.ceoEmail = ceoEmail;
        this.ceoPhone = ceoPhone;
        this.businessNum = businessNum;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }
}
