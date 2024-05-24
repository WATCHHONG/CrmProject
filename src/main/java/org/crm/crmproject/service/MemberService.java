package org.crm.crmproject.service;

import org.crm.crmproject.dto.CeoJoinDTO;
import org.crm.crmproject.dto.CustomerJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception{
        public MidExistException() {}
        public MidExistException(String message) {super(message);}
    }
    void customerJoin(CustomerJoinDTO customerJoinDTO) throws MidExistException;
    void ceoJoin(CeoJoinDTO ceoJoinDTO) throws MidExistException;
}
