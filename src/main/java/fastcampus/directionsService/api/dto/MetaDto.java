package fastcampus.directionsService.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MetaDto {
    @JsonProperty("total_count") //json 으로 응답을 받을 때 매핑하기 위해
    private Integer totalCount;
}
