package com.udit.hateos.resource;

import com.udit.hateos.controller.MembershipController;
import com.udit.hateos.controller.PersonController;
import com.udit.hateos.domain.Person;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@Getter
public class PersonResource extends ResourceSupport {

    final private Person person;

    public PersonResource(final Person person) {
        this.person = person;
        final long id = person.getId();
        add(linkTo(PersonController.class).withRel("person"));
        add(linkTo(methodOn(PersonController.class).get(id)).withSelfRel());
        add(linkTo(methodOn(MembershipController.class).all(id)).withRel("membership"));
    }
}
