package dev.warriorg.shop.account.domain.validation;

import dev.warriorg.shop.account.domain.Account;
import dev.warriorg.shop.account.domain.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.function.Predicate;

public class AccountValidation<T extends Annotation> implements ConstraintValidator<T, Account> {

    @Autowired
    protected AccountRepository repository;

    protected Predicate<Account> predicate = c -> true;

    @Override
    public boolean isValid(Account value, ConstraintValidatorContext context) {
        // 在JPA持久化时，默认采用Hibernate实现，插入、更新时都会调用BeanValidationEventListener进行验证
        // 而验证行为应该尽可能在外层进行，Resource中已经通过@Vaild注解触发过一次验证，这里会导致重复执行
        // 正常途径是使用分组验证避免，但@Vaild不支持分组，@Validated支持，却又是Spring的私有标签
        // 另一个途径是设置Hibernate配置文件中的javax.persistence.validation.mode参数为“none”，这个参数在Spring的yml中未提供桥接
        // 为了避免涉及到数据库操作的验证重复进行，在这里做增加此空值判断，利用Hibernate验证时验证器不是被Spring创建的特点绕开
        return repository == null || predicate.test(value);
    }

    public static class ExistsAccountValidator extends AccountValidation<ExistsAccount> {
        public void initialize(ExistsAccount constraintAnnotation) {
            predicate = c -> repository.existsById(c.getUid());
        }
    }

    public static class NotConflictAccountValidator extends AccountValidation<NotConflictAccount> {
        public void initialize(NotConflictAccount constraintAnnotation) {
            predicate = c -> {
                Collection<Account> collection = repository.findByUsernameOrEmailOrTelephone(c.getUsername(), c.getEmail(), c.getTelephone());
                // 将用户名、邮件、电话改成与现有完全不重复的，或者只与自己重复的，就不算冲突
                return collection.isEmpty() || (collection.size() == 1 && collection.iterator().next().getUid().equals(c.getUid()));
            };
        }
    }
}
