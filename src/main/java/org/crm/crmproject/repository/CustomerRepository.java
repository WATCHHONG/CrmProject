package org.crm.crmproject.repository;

import org.crm.crmproject.domain.Customer;
import org.crm.crmproject.domain.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {
    // 로그인시 아이디와 권한 확인용
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Customer m where m.customerId = :customerId")
    Optional<Customer> getWithRoles(String customerId);

    // 아이디 중복 확인
    boolean existsByCustomerId (String customerId);

    // 회원 정보 수정
    @Modifying
    @Transactional
    @Query("update Customer c set c.customerPw = :re_pw, c.customerName = :re_name, c.customerEmail = :re_email, " +
            "c.customerPhone = :re_phone, c.customerNick = :re_nick  where c.customerId = :customer_id")
    void updateCustomer(@Param("re_pw") String pw, @Param("re_name")String name, @Param("re_email")String email,
                   @Param("re_phone")String phone, @Param("re_nick")String nick, @Param("customer_id")String id );

}
