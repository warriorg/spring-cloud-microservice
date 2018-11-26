package me.warriorg.organization.repository;

import me.warriorg.organization.model.Organization;
import org.springframework.data.repository.CrudRepository;

/**
 * @author: warrior
 * @date: 2018/11/25
 */
public interface OrganizationRepository  extends CrudRepository<Organization, String> {
    Organization findById(String organizationId);
}
