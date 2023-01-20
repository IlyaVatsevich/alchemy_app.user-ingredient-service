package com.example.user_ingredient_service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.security.core.GrantedAuthority;

@Node("UserRole")
@NoArgsConstructor
@Builder(setterPrefix = "with")
@AllArgsConstructor
@Getter
@Setter
public class UserRole implements GrantedAuthority {

    @Override
    public String getAuthority() {
        return roleName.name();
    }

    public enum RoleName {
        USER,ADMIN
    }

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "role_name")
    private RoleName roleName;

}
