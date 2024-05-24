package org.crm.crmproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.domain.Ceo;
import org.crm.crmproject.domain.Customer;
import org.crm.crmproject.dto.CeoDTO;
import org.crm.crmproject.dto.CustomerDTO;
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


    // ceo 회원가입
    @Override
    public void ceoJoin(CeoDTO ceoDTO) throws MidExistException {

        String id = ceoDTO.getCeoId();

        // 아이디 중복확인
        boolean exist = ceoRepository.existsByCeoId(id);

        boolean exist2 = customerRepository.existsByCustomerId(id);

        if(exist || exist2) {
            throw new MidExistException();
        }

        // DTO 에서 비밀번호 가져오기
        String rawPassword = ceoDTO.getCeoPw();

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 암호화된 비밀번호를 DTO 에 다시 설정
        ceoDTO.setCeoPw(encodedPassword);

        Ceo ceo = modelMapper.map(ceoDTO, Ceo.class);

        ceoRepository.save(ceo);
    }
    // 고객 회원가입
    @Override
    public void customerJoin(CustomerDTO customerDTO) throws MidExistException {

        String id = customerDTO.getCustomerId();

        // 아이디 중복 확인
        boolean exist = ceoRepository.existsByCeoId(id);

        boolean exist2 = customerRepository.existsByCustomerId(id);

        if(exist || exist2) {
            throw new MidExistException();
        }
        // DTO 에서 비밀번호 가져오기
        String rawPassword = customerDTO.getCustomerPw();

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // 암호화된 비밀번호를 DTO 에 다시 설정
        customerDTO.setCustomerPw(encodedPassword);

        Customer customer = modelMapper.map(customerDTO, Customer.class);

        customerRepository.save(customer);

        log.info("커스터머 가입 성공적");

    }

}