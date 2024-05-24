package org.crm.crmproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.domain.Ceo;
import org.crm.crmproject.domain.Customer;
import org.crm.crmproject.domain.Role;
import org.crm.crmproject.dto.CeoJoinDTO;
import org.crm.crmproject.dto.CustomerJoinDTO;
import org.crm.crmproject.repository.CeoRepository;
import org.crm.crmproject.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final CeoRepository ceoRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void ceoJoin(CeoJoinDTO ceoJoinDTO) throws MidExistException {

        String id = ceoJoinDTO.getCeoId();

        boolean exist = ceoRepository.existsByCeoId(id);

        boolean exist2 = customerRepository.existsByCustomerId(id);

        if(exist || exist2) {
            throw new MidExistException();
        }
        // DTO 에서 비밀번호 가져오기
        String rawPassword = ceoJoinDTO.getCeoPw();

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 암호화된 비밀번호를 DTO 에 다시 설정
        ceoJoinDTO.setCeoPw(encodedPassword);

        Ceo ceo = modelMapper.map(ceoJoinDTO, Ceo.class);

        ceoRepository.save(ceo);
    }

    @Override
    public void customerJoin(CustomerJoinDTO customerJoinDTO) throws MidExistException {

        String id = customerJoinDTO.getCustomerId();

        boolean exist = ceoRepository.existsByCeoId(id);

        boolean exist2 = customerRepository.existsByCustomerId(id);

        if(exist || exist2) {
            throw new MidExistException();
        }


        Customer customer = modelMapper.map(customerJoinDTO, Customer.class);
        customer.changePassword(passwordEncoder.encode(customerJoinDTO.getCustomerPw()));

        log.info(customer);
        log.info(customer.getRoleSet());

        customerRepository.save(customer);

        log.info("커스터머 가입 성공적");

    }

}