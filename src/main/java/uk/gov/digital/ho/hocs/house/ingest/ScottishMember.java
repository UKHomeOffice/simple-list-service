package uk.gov.digital.ho.hocs.house.ingest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ScottishMember {

    @JsonProperty(value = "ParliamentaryName")
    private String name;

    public String getName() {
        String[] names = name.split(",", 2);
        return (names[1] + " " + names[0]).trim();
    }

}
