package dev.warriorg.shop.account.domain;

import dev.warriorg.shop.infrastructure.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity {

    private String username;

    private String password;

    private String name;

    private String avatar;

    private String telephone;

    private String email;

    private String location;

}
