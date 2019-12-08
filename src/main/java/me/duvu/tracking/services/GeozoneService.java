package me.duvu.tracking.services;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.Geofence;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.repository.GeofenceRepository;
import me.duvu.tracking.specification.GeofenceSpecification;
import me.duvu.tracking.web.rest.model.in.GeofenceRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author beou on 2/20/18 02:03
 */
@Slf4j
@Service
public class GeozoneService extends AbstractService<Geofence, GeofenceRequest> {

    private final GeofenceRepository geofenceRepository;
    private final GeofenceSpecification geofenceSpecification;
    private final AccountService accountService;

    public GeozoneService(GeofenceRepository geofenceRepository,
                          GeofenceSpecification geofenceSpecification,
                          AccountService accountService) {
        this.geofenceRepository = geofenceRepository;
        this.geofenceSpecification = geofenceSpecification;
        this.accountService = accountService;
    }


    @Override
    public Page<Geofence> getAll(String search, Pageable pageable) {
        Specification<Geofence> specification = geofenceSpecification.search(search);
        return geofenceRepository.findAll(specification, pageable);
    }

    @Override
    public List<Geofence> getAll(String search) {
        Specification<Geofence> specification = geofenceSpecification.search(search);
        return geofenceRepository.findAll(specification);
    }

    @Override
    public Geofence getById(Long id) {
        return geofenceRepository.getOne(id);
    }

    @Override
    public Geofence create(GeofenceRequest request) {
        Set<Long> acountIds = request.getAccountIds();
        if (acountIds == null) {
            acountIds = new HashSet<>();
        }
        acountIds.add(ApplicationContext.getAccountId());
        Set<Account> accountSet = acountIds.stream().map(accountService::getById).collect(Collectors.toSet());
        Geofence geofence = Geofence.builder()
                .accounts(accountSet)
                .name(request.getName())
                .address(request.getAddress())
                .color(request.getColor())
                .maxSpeedKPH(request.getMaxSpeedKPH())
                .reverseGeocode(request.isReverseGeocode())
                .privateArea(request.isPrivateArea())
                .geojson(request.getGeojson())
                .createdBy(ApplicationContext.getCurrentUserName())
                .build();

        return geofenceRepository.save(geofence);
    }

    @Override
    public void update(Long id, GeofenceRequest request) {
        Specification<Geofence> spec = geofenceSpecification.findOne(id);
        Geofence geofence = geofenceRepository.findOne(spec).orElse(null);
        if (geofence != null) {
            mapping(geofence, request);
            geofenceRepository.save(geofence);
        } else {
            throw new AccessDeninedOrNotExisted("Not found valid object");
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Specification<Geofence> spec = geofenceSpecification.findOne(id);
        geofenceRepository.findOne(spec).ifPresent(geofenceRepository::delete);
    }

    public String getZoneName(Long id) {
        Specification<Geofence> spec = geofenceSpecification.findOne(id);
        Geofence geofence = geofenceRepository.findOne(spec).orElse(null);
        if (geofence != null) {
            return geofence.getName();
        } else {
            return null;
        }
    }

    private void mapping(Geofence geofence, GeofenceRequest request) {
        Set<Long> accountIds = request.getAccountIds();
        Set<Account> accountSet = (accountIds != null ? accountIds.stream().map(accountService::getById).collect(Collectors.toSet()) : null);
        geofence.setAccounts(accountSet);
        geofence.setName(request.getName());
        geofence.setAddress(request.getAddress());
        geofence.setColor(request.getColor());
        geofence.setMaxSpeedKPH(request.getMaxSpeedKPH());
        geofence.setReverseGeocode(request.isReverseGeocode());
        geofence.setPrivateArea(request.isPrivateArea());

        geofence.setGeojson(request.getGeojson());
    }
}
