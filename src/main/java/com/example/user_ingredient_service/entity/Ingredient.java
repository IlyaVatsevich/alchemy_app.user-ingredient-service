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

@Node("Ingredient")
@NoArgsConstructor
@Builder(setterPrefix = "with")
@AllArgsConstructor
@Getter
@Setter
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "name")
    private String name;

    @Property(name = "price")
    private Integer price;

    @Property(name = "loss_probability")
    private Short lossProbability;

    @Relationship(type = "CREATED_FROM",direction = Relationship.Direction.OUTGOING)
    private List<IngredientsCreatedFrom> ingredients;

    @RelationshipProperties
    @NoArgsConstructor
    @Builder(setterPrefix = "with")
    @AllArgsConstructor
    @Getter
    @Setter
    public static class IngredientsCreatedFrom {

        @RelationshipId
        private Long id;

        @TargetNode
        private Ingredient ingredient;
    }

}
