package org.crm.crmproject.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleSet")

public class Ceo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ceo_no")
    private Long ceoNo;

    @Column(unique = true, nullable = false)
    private String ceoId;

    @Column(nullable = false)
    private String ceoPw;

    private String ceoName;

    private String ceoEmail;

    private String ceoPhone;

    private String businessNum;

    private String storeName;

    private String storeAddress;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @Builder.Default
//    @JoinTable(name = "ceo_role_set", joinColumns = @JoinColumn(name = "ceo_no"))
//    @Column(name = "role_set")
    @Enumerated(EnumType.STRING)
    private Set<Role> roleSet = new HashSet<>();

    public void ceoChange(String ceoPw, String ceoName,
                          String ceoEmail, String ceoPhone, String storeAddress)
    {
        this.ceoPw = ceoPw;
        this.ceoName = ceoName;
        this.ceoEmail = ceoEmail;
        this.ceoPhone = ceoPhone;
        this.storeAddress = storeAddress;
    }
    


}