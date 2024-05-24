package org.crm.crmproject.repository;

import org.crm.crmproject.domain.Ceo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CeoRepository extends JpaRepository<Ceo, String> {

    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from Ceo m where m.ceoId = :ceoId")
    Optional<Ceo> getWithRoles(String ceoId);

    boolean existsByCeoId (String ceoId);

    Optional<Ceo> findByCeoId(String ceoId);


    @Modifying
    @Transactional
    @Query("update Ceo c set c.ceoPw = :re_pw, c.ceoName = :re_name, c.ceoEmail = :re_email, " +
            "c.ceoPhone = :re_phone, c.storeAddress = :re_address where c.ceoId = :ceo_id")
    void updateCeo(@Param("re_pw") String pw, @Param("re_name")String name, @Param("re_email")String email,
                   @Param("re_phone")String phone, @Param("re_address")String address, @Param("ceo_id")String id );
}