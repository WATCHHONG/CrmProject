package org.crm.crmproject.Repository;

import lombok.extern.log4j.Log4j2;
import org.crm.crmproject.dto.BoardDTO;
import org.crm.crmproject.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;


    @Test
    public void testRegisterWithImages() {

        log.info(boardService.getClass().getName());

        BoardDTO boardDTO = BoardDTO.builder()
                .content("샘플이다")
                .writer("샘플")
                .build();

        boardDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID() + "_aaa.jpg",
                        UUID.randomUUID() + "_bbb.jpg",
                        UUID.randomUUID() + "_bbb.jpg"
                )
        );


        long bno = boardService.register(boardDTO);

        log.info("bno : " + bno);
    }

    @Test
    public void testReadAll() {

        Long bno = 2L;

        BoardDTO boardDTO = boardService.readOne(bno);

        log.info(boardDTO);

        for (String filename : boardDTO.getFileNames()) {
            log.info(filename);
        }
    }
}
