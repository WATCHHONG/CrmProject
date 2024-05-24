package org.crm.crmproject.service;

import org.crm.crmproject.dto.CeoDTO;
import org.crm.crmproject.dto.CustomerDTO;

public interface MemberService {
    // 아이디 중복확인
    static class MidExistException extends Exception{
        public MidExistException() {}
        public MidExistException(String message) {super(message);}
    }
    // 고객 회원가입 서비스
    void customerJoin(CustomerDTO customerDTO) throws MidExistException;
    // 사장 회원가입 서비스
    void ceoJoin(CeoDTO ceoDTO) throws MidExistException;
}
