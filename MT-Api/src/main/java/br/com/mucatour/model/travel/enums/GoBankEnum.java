package br.com.mucatour.model.travel.enums;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public enum GoBankEnum {
    SOMENTE_IDA("Somente Ida"),
    IDA_E_VOLTA("Ida e volta");

    private String name;
}
