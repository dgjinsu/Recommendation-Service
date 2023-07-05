package fastcampus.directionsService.api.service

import spock.lang.Specification

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {
    private KakaoUriBuilderService kakaoUriBuilderService;

    //실행 시 가장 먼저 실행
    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService()
    }

    def "buildUriByAddressSearch - 한글 파라미터의 경우 정상적으로 인코딩" () {
        given:
        String address = "부산광역시 해운대구"
        def charset = StandardCharsets.UTF_8
        when:
        def uri = kakaoUriBuilderService.buildUriByAddressSearch(address) //def 라는 키워드로 타입을 동적으로 선언 가능
        def decodeResult = URLDecoder.decode(uri.toString(), charset)
        then:
        decodeResult == "https://dapi.kakao.com/v2/local/search/address.json?query=부산광역시 해운대구"

    }

}
