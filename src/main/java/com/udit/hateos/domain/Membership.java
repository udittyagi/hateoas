package com.udit.hateos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity @Getter @Setter
@NoArgsConstructor @ToString(exclude = "person")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membership_id")
    private Long id;

    @Column(name="sport")
    private String sport;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "renewal_date")
    private Date renewalDate;

    @JsonIgnore
    @Setter(AccessLevel.NONE)
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "person_id")
    private Person person;

    public Membership(final String sport) {

        this.sport = sport;
        startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.MONTH,6);
        renewalDate = calendar.getTime();
    }

    public void setPerson(Person person) {
        if(person == null){
            this.person=null;
            return;
        }
        this.person = person;
        person.getMemberships().add(this);
    }

    public void removePerson(Person person){
        person.removeMembership(this);
        this.person = null;
    }
}
