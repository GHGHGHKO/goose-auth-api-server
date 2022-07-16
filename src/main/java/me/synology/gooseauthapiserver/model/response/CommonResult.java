package me.synology.gooseauthapiserver.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CommonResult {

    @Schema(name = "응답 성공여부 : true/false")
    private boolean success;

    @Schema(name = "응답 성공 번호 : >= 0 정상, < 0 비정상")
    private int code;

    @Schema(name = "응답 메시지")
    private String msg;
}
