package dev.warriorg.shop.account.domain;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

@CacheConfig(cacheNames = "repository.account")
public interface AccountRepository extends JpaRepository<Account, String> {

    @Cacheable(key = "#username")
    Account findByUsername(String username);

    /**
     * 判断唯一性，用户名、邮箱、电话不允许任何一个重复
     */
    boolean existsByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    /**
     * 判断唯一性，用户名、邮箱、电话不允许任何一个重复
     */
    Collection<Account> findByUsernameOrEmailOrTelephone(String username, String email, String telephone);

    @CacheEvict(key = "#entity.uid")
    void delete(Account entity);

    @CacheEvict(allEntries = true)
    void deleteAll(Iterable<? extends Account> entities);

    @CacheEvict(allEntries = true)
    void deleteAll();

}
