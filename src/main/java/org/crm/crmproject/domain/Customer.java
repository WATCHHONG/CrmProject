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

public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_no")
    private Long customerNo;

    @Column(unique = true, nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String customerPw;

    private String customerName;

    private String customerGender;

    private String customerEmail;

    private String customerPhone;

    private String customerNick;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.LAZY)
    @Builder.Default
//    @JoinTable(name = "customer_role_set", joinColumns = @JoinColumn(name = "customer_no"))
//    @Column(name = "role_set")
    @Enumerated(EnumType.STRING)
    private Set<Role> roleSet = new HashSet<>();

    public void changePassword(String customerPw) {
        this.customerPw = customerPw;
    }

    public void addRole(Role role) {
        this.roleSet.add(role);
    }

    public void clearRoles() {
        this.roleSet.clear();
    }


}