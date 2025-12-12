package Lever;

import Interfaces.IDivision;
import Interfaces.ILever;

import com.fasterxml.jackson.annotation.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Lever.class, name = "lever")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Lever implements ILever {
    private IDivision division;
    private int id;

    public Lever() {
    }

    public Lever(IDivision division, int id) {
        this.division = division;
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public IDivision getDivision() {
        return this.division;
    }

    public void setDivision(IDivision division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return "Alavanca " + this.id;
    }
}