package com.freeder.buclserver.admin.고객관리.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class 유저프로필Dto {

    private String userName;
    List<유저프로필정보> name;

    public static class 유저프로필정보{
        private String userId;
        private String cellPhone;
        private Integer rewardSum;
        private float rewardRate;
    }


}
