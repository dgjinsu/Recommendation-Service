package fastcampus.directionsService.pharmacy.service;

import fastcampus.directionsService.api.dto.DocumentDto;
import fastcampus.directionsService.api.dto.KakaoApiResponseDto;
import fastcampus.directionsService.api.service.KakaoAddressSearchService;
import fastcampus.directionsService.direction.dto.OutputDto;
import fastcampus.directionsService.direction.entity.Direction;
import fastcampus.directionsService.direction.service.Base62Service;
import fastcampus.directionsService.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;
    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;
    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    public List<OutputDto> recommendPharmacyList(String address) {
        //위도, 경도, 주소명 등을 포함한 dto 로 변환
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentDtoList())) {
            log.error("PharmacyRecommendationService recommendPharmacyList fail / Input address: {}", address);
            return Collections.emptyList();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);

        List<Direction> directionList = directionService.buildDirectionList(documentDto); //db에 저장된 약국에서 불러오기
//        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto); //api 요청으로 약국 데이터 불러오기

        return directionService.saveAll(directionList).stream()
                .map(direction -> convertToOutputDto(direction))
                .collect(Collectors.toList());

    }

    private OutputDto convertToOutputDto(Direction direction) {



        return OutputDto.builder()
                .pharmacyName(direction.getTargetPharmacyName())
                .pharmacyName(direction.getTargetAddress())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}
