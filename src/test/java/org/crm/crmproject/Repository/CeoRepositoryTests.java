package org.crm.crmproject.Repository;

import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.domain.Ceo;
import org.crm.crmproject.domain.Customer;
import org.crm.crmproject.domain.Role;
import org.crm.crmproject.repository.CeoRepository;
import org.crm.crmproject.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.stream.LongStream;

import static org.crm.crmproject.domain.QCustomer.customer;

@Log4j2
@SpringBootTest
public class CeoRepositoryTests {

    @Autowired
    CeoRepository ceoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void InsertCeoTests(){
        LongStream.range(1,10).forEach(i ->{
            Ceo ceo = Ceo.builder()
                    .ceoId("사장아이디" + i)
                    .ceoPw(passwordEncoder.encode("1111"))
                    .ceoName("사장이름")
                    .ceoEmail("사장@이메일")
                    .ceoPhone("01012341234")
                    .businessNum("412351241")
                    .storeName("박사장네"+i)
                    .storeAddress(i+"번지")
                    .build();
            ceo.addRole(Role.CEO);

            ceoRepository.save(ceo);
        });
    }

    @Test
    public void testRead() {

        Optional<Ceo> result = ceoRepository.getWithRoles("customer");

        Ceo ceo = result.orElseThrow();

        log.info(ceo);
        log.info(ceo.getRoleSet());

        ceo.getRoleSet().forEach(role -> log.info(role.name()));
    }

}
