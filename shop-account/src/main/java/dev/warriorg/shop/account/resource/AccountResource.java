package dev.warriorg.shop.account.resource;


import dev.warriorg.shop.account.application.AccountApplicationService;
import dev.warriorg.shop.account.domain.Account;
import dev.warriorg.shop.account.domain.validation.NotConflictAccount;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@CacheConfig(cacheNames = "resource.account")
public class AccountResource {

    @Resource
    private AccountApplicationService service;

    @GetMapping("{/username}")
    @Cacheable(key = "#username")
    public Account getUser (@PathVariable String username) {
        return service.findAccountByUsername(username);
    }


    @PostMapping
    @CacheEvict(key = "#user.username")
    public void createUser(@Valid @NotConflictAccount Account user) {
        service.createAccount(user);
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    @CacheEvict(key = "#user.username")
    public void updateUser(@Valid @NotConflictAccount Account user) {
        service.updateAccount(user);
    }

}
