package fastcampus.directionsService.pharmacy.repository

import fastcampus.AbstractIntegrationContainerBaseTest
import fastcampus.directionsService.pharmacy.entity.Pharmacy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import javax.swing.undo.AbstractUndoableEdit

@SpringBootTest
class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private PharmacyRepository pharmacyRepository

    def "PharmacyRepository save"() {
        given:
        String address = "부산광역시 해운대구"
        String name = "진수 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
        def result = pharmacyRepository.save(pharmacy)

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude
    }

}
