package me.warriorg.licenses.repository;

import me.warriorg.licenses.model.License;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author: warrior
 * @date: 2018/11/25
 */
public interface LicenseRepository extends CrudRepository<License, String> {
    List<License> findByOrganizationId(String organizationId);
    License findByOrganizationIdAndLicenseId(String organizationId, String licenseId);
}
