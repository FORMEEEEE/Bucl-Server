package com.freeder.buclserver.domain.openbanking.dto;

import com.freeder.buclserver.global.util.DateUtils;

public record SendApiDto(
        String bank_tran_id,
        String bank_code_std,
        String account_num,
        String account_holder_info_type,
        String account_holder_info,
        String tran_dtime
) {
    public static SendApiDto merge(OpenBankingAccessTokenDto dto, ReqApiDto reqApiDto){
        return new SendApiDto(
                dto.getClient_use_code(),
                reqApiDto.bankNm().getBankCode(),
                reqApiDto.account(),
                "",
                reqApiDto.birth(),
                DateUtils.nowDateString()
        );
    }
}
