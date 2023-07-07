package fastcampus.directionsService.direction.controller;

import fastcampus.directionsService.direction.entity.Direction;
import fastcampus.directionsService.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DirectionController {
    private final DirectionService directionService;

    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable("encodedId") String encodedId) {
        //인코딩된 url 주소를 받아서 디코딩 후 리다이렉트
        String resultUrl = directionService.findDirectionUrlById(encodedId);

        log.info("[DirectionController searchDirection] resultUrl = {}", resultUrl);
        return "redirect:"+resultUrl;
    }


}
