package dev.warriorg.shop.account.application;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import dev.warriorg.shop.account.domain.Account;
import dev.warriorg.shop.account.domain.AccountRepository;
import dev.warriorg.shop.infrastructure.utility.Encryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountApplicationService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private Encryption encoder;

    public void createAccount(Account account) {
        account.setPassword(encoder.encode(account.getPassword()));
        repository.save(account);
    }

    @SentinelResource(value = "findAccountByUsername", fallback = )
    public Account findAccountByUsername(String username) {
        return repository.findByUsername(username);
    }

    public void updateAccount(Account account) {
        repository.save(account);
    }

}
