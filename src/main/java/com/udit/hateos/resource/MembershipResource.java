package com.udit.hateos.resource;

import com.udit.hateos.controller.MembershipController;
import com.udit.hateos.domain.Membership;
import lombok.Getter;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Getter
public class MembershipResource extends ResourceSupport {
    final private Membership membership;

    public MembershipResource(final Membership membership) {
        this.membership = membership;
        final Long membershipId = membership.getId();
        final Long personId = membership.getPerson().getId();
        add(linkTo(methodOn(MembershipController.class).removeMembership(personId,membershipId)).withRel("remove"));
    }
}
