package fastcampus.directionsService.pharmacy.service

import fastcampus.AbstractIntegrationContainerBaseTest
import fastcampus.directionsService.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class PharmacyServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    PharmacyService pharmacyService;

    @Autowired
    PharmacyRepository pharmacyRepository;

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "더티체킹 테스트"() {
        햪두
    }
}
