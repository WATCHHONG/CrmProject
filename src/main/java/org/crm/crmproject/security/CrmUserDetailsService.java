package org.crm.crmproject.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.domain.Ceo;
import org.crm.crmproject.domain.Customer;
import org.crm.crmproject.dto.CeoSecurityDTO;
import org.crm.crmproject.dto.CustomerSecurityDTO;
import org.crm.crmproject.repository.CeoRepository;
import org.crm.crmproject.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CrmUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    private final CeoRepository ceoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername : " + username);

        // 일반 사용자 검색
        Optional<Customer> customerResult = customerRepository.getWithRoles(username);
        if (customerResult.isPresent()) {
            Customer customer = customerResult.get();
            CustomerSecurityDTO customerSecurityDTO = new CustomerSecurityDTO(
                    customer.getCustomerNo(),
                    customer.getCustomerId(),
                    customer.getCustomerPw(),
                    customer.getCustomerName(),
                    customer.getCustomerGender(),
                    customer.getCustomerEmail(),
                    customer.getCustomerPhone(),
                    customer.getCustomerNick(),
                    customer.getRoleSet().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                            .collect(Collectors.toList())
            );

            log.info("Customer 시큐리티 DTO: " + customerSecurityDTO);
            return customerSecurityDTO;
        }

        // 사업자 검색
        Optional<Ceo> ceoResult = ceoRepository.getWithRoles(username);
        if (ceoResult.isPresent()) {
            Ceo ceo = ceoResult.get();
            CeoSecurityDTO ceoSecurityDTO = new CeoSecurityDTO(
                    ceo.getCeoNo(),
                    ceo.getCeoId(),
                    ceo.getCeoPw(),
                    ceo.getCeoName(),
                    ceo.getCeoEmail(),
                    ceo.getCeoPhone(),
                    ceo.getBusinessNum(),
                    ceo.getStoreName(),
                    ceo.getStoreAddress(),
                    ceo.getRoleSet().stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                            .collect(Collectors.toList())
            );

            log.info("Ceo 시큐리티 DTO: " + ceoSecurityDTO);
            log.info(ceoSecurityDTO.getAuthorities());
            return ceoSecurityDTO;
        }

        throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + username);
    }

}