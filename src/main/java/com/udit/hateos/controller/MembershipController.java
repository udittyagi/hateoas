package com.udit.hateos.controller;

import com.udit.hateos.domain.Membership;
import com.udit.hateos.domain.Person;
import com.udit.hateos.resource.MembershipResource;
import com.udit.hateos.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/membership",produces = "application/hal+json")
public class MembershipController {
    final private ClubService clubService;

    @Autowired
    public MembershipController(ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Resources<MembershipResource>> all(@PathVariable final Long personId){
        final List<MembershipResource> collection = clubService.getAllMembership(personId).stream().map(MembershipResource::new).collect(Collectors.toList());
        final Resources<MembershipResource> resources = new Resources<>(collection);
        String uriString = MvcUriComponentsBuilder.fromController(PersonController.class).path("/{personId}").buildAndExpand(personId).toUriString();
        resources.add(new Link(uriString,"person"));
        return ResponseEntity.ok(resources);

    }

    @PutMapping("/{personId}/{membershipId}")
    public ResponseEntity<?> removeMembership(@PathVariable final Long personId, @PathVariable final Long membershipId){
        clubService.removeMembership(personId,membershipId);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @PutMapping("/add/{personId}")
    public ResponseEntity<?> addMembership(@PathVariable final Long personId, @RequestBody final String sport){

        Membership membership = new Membership(sport);
        clubService.addMembership(personId,membership);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }
}
