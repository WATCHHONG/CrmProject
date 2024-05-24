package org.crm.crmproject.service;

import org.crm.crmproject.domain.Board;
import org.crm.crmproject.dto.BoardDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    BoardDTO findById(Long bno);

    BoardDTO readOne(Long bno);

    default Board dtoToEntity(BoardDTO boardDTO) {

        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .writer(boardDTO.getWriter())
                .content(boardDTO.getContent())
                .build();

        if (boardDTO.getFileNames() != null) {
            boardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }

    default BoardDTO entityToDTO(Board board) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .content(board.getContent())
                .writer(board.getWriter())
                .build();

        List<String> fileNames = board.getImageSet().stream().sorted().map(boardImage ->
                boardImage.getUuid() + "_" + boardImage.getFileName()).toList();

        boardDTO.setFileNames(fileNames);

        return boardDTO;
    }
}
