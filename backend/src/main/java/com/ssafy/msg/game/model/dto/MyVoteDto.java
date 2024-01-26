package com.ssafy.msg.game.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyVoteDto {
    private String normalVote;
    private String mafiaVote;
    private String doctorVote;
}
