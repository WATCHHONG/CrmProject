package org.crm.crmproject.dto;

import lombok.Data;
import org.crm.crmproject.domain.Role;

import java.util.Set;

@Data
public class CeoJoinDTO {

    private Long ceoNo;

    private String ceoId;

    private String ceoPw;

    private String ceoName;

    private String ceoEmail;

    private String ceoPhone;

    private String businessNum;

    private String storeName;

    private String storeAddress;

    private Set<Role> roleSet;

}
