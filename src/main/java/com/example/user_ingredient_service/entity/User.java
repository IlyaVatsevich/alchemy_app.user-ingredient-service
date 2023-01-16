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
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.List;
import java.util.Set;

@Node("User")
@NoArgsConstructor
@Builder(setterPrefix = "with")
@AllArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "mail")
    private String mail;

    @Property(name = "login")
    private String login;

    @Relationship(type = "HAS_A_ROLE",direction = Relationship.Direction.OUTGOING)
    private Set<UserRole> roles;

    @Property(name = "password")
    private String password;

    @Property(name = "gold")
    private Integer gold;

    @Relationship(type = "OBTAIN",direction = Relationship.Direction.OUTGOING)
    private List<UserIngredient> userIngredients;

    @RelationshipProperties
    @NoArgsConstructor
    @Builder(setterPrefix = "with")
    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserIngredient {

        @RelationshipId
        private Long id;

        private Long count;

        @TargetNode
        private Ingredient ingredient;

    }

}
