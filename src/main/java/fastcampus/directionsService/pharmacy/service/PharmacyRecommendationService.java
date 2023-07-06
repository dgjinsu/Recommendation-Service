package fastcampus.directionsService.pharmacy.service;

import fastcampus.directionsService.api.dto.DocumentDto;
import fastcampus.directionsService.api.dto.KakaoApiResponseDto;
import fastcampus.directionsService.api.service.KakaoAddressSearchService;
import fastcampus.directionsService.direction.entity.Direction;
import fastcampus.directionsService.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;

    public void recommendPharmacyList(String address) {
        //위도, 경도, 주소명 등을 포함한 dto 로 변환
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentDtoList())) {
            log.error("PharmacyRecommendationService recommendPharmacyList fail / Input address: {}", address);
            return;
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentDtoList().get(0);

//        List<Direction> directionList = directionService.buildDirectionList(documentDto); //db에 저장된 약국에서 불러오기
        List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);
        directionService.saveAll(directionList);


    }
}
