package Interfaces;

import Map.*;
import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuestionDivision.class, name = "question"),
        @JsonSubTypes.Type(value = LeverDivision.class, name = "lever"),
        @JsonSubTypes.Type(value = GoalDivision.class, name = "goal")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public interface IDivision {
    String getName();

    IDivision getComportament(IMap maze, IPlayer player);
}