package com.udit.hateos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter @Setter
@NoArgsConstructor @ToString(exclude = "memberships")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "person_id")
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "person",orphanRemoval = true,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    private List<Membership> memberships = new ArrayList<>();

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(Person person) {
        this.firstName = person.firstName;
        this.lastName = person.lastName;
    }

    public void setMemberships(Membership membership) {
        this.memberships.add(membership);
        membership.setPerson(this);
    }

    public void removeMembership(Membership membership){
        if(!this.memberships.contains(membership))
            return;
        this.memberships.remove(membership);
        membership.setPerson(null);
    }
}
