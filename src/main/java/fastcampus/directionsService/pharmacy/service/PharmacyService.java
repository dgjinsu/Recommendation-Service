package fastcampus.directionsService.pharmacy.service;

import fastcampus.directionsService.pharmacy.entity.Pharmacy;
import fastcampus.directionsService.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);
        if(Objects.isNull(pharmacy)) {
            log.error("ID값으로 엔티티를 찾을 수 없습니다. id: " + id);
            return;
        }
        pharmacy.changePharmacyAddress(address);

    }

    //for test
    @Transactional
    public void updateAddressWithoutTransaction(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id).orElse(null);
        if(Objects.isNull(pharmacy)) {
            log.error("ID값으로 엔티티를 찾을 수 없습니다. id: " + id);
            return;
        }
        pharmacy.changePharmacyAddress(address);
    }

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }
}
