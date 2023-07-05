package fastcampus.directionsService.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class KakaoUriBuilderService {
    private static String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    public URI buildUriByAddressSearch(String address) {

        //URI 만들어주는 빌더
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address);

        //공백이나 인식하지 못하는 특정 문자들을 인코딩해서 uri 생성
        URI uri = uriBuilder.build().encode().toUri();

        log.info("[uri:]" + uri);
        return uri;
    }
}
