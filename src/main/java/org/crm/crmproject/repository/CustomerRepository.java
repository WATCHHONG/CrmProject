package org.crm.crmproject.repository;

import org.crm.crmproject.domain.Ceo;
import org.crm.crmproject.domain.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Customer m where m.customerId = :customerId")
    Optional<Customer> getWithRoles(String customerId);

    boolean existsByCustomerId (String customerId);

}
