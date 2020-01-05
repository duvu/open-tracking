package me.duvu.tracking.services;

import me.duvu.tracking.ApplicationContext;
import me.duvu.tracking.domain.Account;
import me.duvu.tracking.domain.Driver;
import me.duvu.tracking.exception.AccessDeninedOrNotExisted;
import me.duvu.tracking.repository.AccountRepository;
import me.duvu.tracking.repository.DriverRepository;
import me.duvu.tracking.specification.DriverSpecification;
import me.duvu.tracking.web.rest.model.request.DriverRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author beou on 9/20/18 11:50
 */
@Service
public class DriverService extends AbstractService<Driver, DriverRequest> {

    private final DriverSpecification driverSpecification;
    private final DriverRepository driverRepository;
    private final AccountRepository accountRepository;

    protected DriverService(DriverSpecification driverSpecification,
                             DriverRepository driverRepository,
                            AccountRepository accountRepository) {
        this.driverSpecification = driverSpecification;
        this.driverRepository = driverRepository;
        this.accountRepository = accountRepository;
    }

    @Override
    public Page<Driver> getAll(String search, Pageable pageable) {
        Specification<Driver> specification = driverSpecification.search(search);
        return driverRepository.findAll(specification, pageable);
    }

    @Override
    public List<Driver> getAll(String search) {
        Specification<Driver> specification = driverSpecification.search(search);
        return driverRepository.findAll(specification);
    }

    @Override
    public Driver getById(Long id) {
        Specification<Driver> specification = driverSpecification.findOne(id);
        return driverRepository.findOne(specification).orElse(null);
    }

    @Override
    public Driver create(DriverRequest request) {
        Account account = ApplicationContext.getAccount();

        Driver driver = Driver.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .account(account)
                .dateOfBirth(request.getDateOfBirth())
                .typeOfVehicleExperienced(request.getTypeOfVehicleExperienced())
                .driverLicenseNumber(request.getDriverLicenseNumber())
                .driverLicenseType(request.getDriverLicenseType())
                .driverLicenseTypeDescription(request.getDriverLicenseTypeDescription())
                .driverLicenseIssueDate(request.getDriverLicenseIssueDate())
                .driverLicenseExpiredDate(request.getDriverLicenseExpiredDate())
                .build();

        return driverRepository.save(driver);
    }

    @Override
    public Driver update(Long id, DriverRequest request) {
        Driver driver = getById(id);
        if (driver != null) {

            Account account = accountRepository.findById(ApplicationContext.getAccountId()).orElse(null);

            driver.setFirstName(request.getFirstName());
            driver.setLastName(request.getLastName());
            driver.setAccount(account);
            driver.setDateOfBirth(request.getDateOfBirth());
            driver.setTypeOfVehicleExperienced(request.getTypeOfVehicleExperienced());
            driver.setDriverLicenseNumber(request.getDriverLicenseNumber());
            driver.setDriverLicenseType(request.getDriverLicenseType());
            driver.setDriverLicenseTypeDescription(request.getDriverLicenseTypeDescription());
            driver.setDriverLicenseIssueDate(request.getDriverLicenseIssueDate());
            driver.setDriverLicenseExpiredDate(request.getDriverLicenseExpiredDate());

            return driverRepository.save(driver);
        } else {
            throw new AccessDeninedOrNotExisted("Driver not existed");
        }
    }

    @Override
    public void delete(Long id) {
        Driver driver = getById(id);
        if (driver != null) {
            driverRepository.delete(driver);
        } else {
            throw new AccessDeninedOrNotExisted("Driver not existed");
        }
    }
}
