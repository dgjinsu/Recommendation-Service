package fastcampus.directionsService.pharmacy.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fastcampus.directionsService.pharmacy.dto.PharmacyDto;
import fastcampus.directionsService.pharmacy.entity.Pharmacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRedisTemplateService {
    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    //약국 키 값, 약국 pk, 약국 dto 를 json 으로 변환한 데이터
    private HashOperations<String, String, String> hashOperations;
    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(PharmacyDto pharmacyDto) {
        if(Objects.isNull(pharmacyDto) || Objects.isNull(pharmacyDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try{
            hashOperations.put(CACHE_KEY, pharmacyDto.getId().toString(), serializePharmacyDto(pharmacyDto));
            log.info("[PharmacyRedisTemplateService save success] id: {}", pharmacyDto.getId());
        } catch (Exception e) {
            log.error("[PharmacyRedisTemplateService save error] id: {}", pharmacyDto.getId());

        }
    }

    public List<PharmacyDto> findAll() {

        try {
            List<PharmacyDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                PharmacyDto pharmacyDto = deserializePharmacyDto(value);
                list.add(pharmacyDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[PharmacyRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[PharmacyRedisTemplateService delete]: {} ", id);
    }

    //DTO -> JSON
    private String serializePharmacyDto(PharmacyDto pharmacyDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(pharmacyDto);
    }

    //JOSN -> DTO
    private PharmacyDto deserializePharmacyDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, PharmacyDto.class);
    }
}
