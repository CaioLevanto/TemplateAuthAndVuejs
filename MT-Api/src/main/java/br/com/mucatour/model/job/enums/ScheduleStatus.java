package br.com.mucatour.model.job.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ScheduleStatus {
    RUNNING("Rodando"),
    PAUSED("Pausado"),
    RESUMED("Aguardando retomar");

    private String name;
}
