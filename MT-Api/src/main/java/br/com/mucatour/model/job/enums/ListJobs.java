package br.com.mucatour.model.job.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ListJobs {
    TRAVEL_PENDENCY("travelPendency"),
    TRAVEL_MESSAGE("travelMessage");

    private String name;
}
