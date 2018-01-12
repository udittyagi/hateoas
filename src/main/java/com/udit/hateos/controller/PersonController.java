package com.udit.hateos.controller;

import com.udit.hateos.domain.Person;
import com.udit.hateos.resource.PersonResource;
import com.udit.hateos.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/person",produces = "application/hal+json")
public class PersonController {
    private final ClubService clubService;

    @Autowired
    public PersonController(final ClubService clubService) {
        this.clubService = clubService;
    }

    @GetMapping
    public ResponseEntity<Resources<PersonResource>> all(){
        final List<PersonResource> collection = clubService.getAllPerson().stream().map(PersonResource::new).collect(Collectors.toList());
        final Resources<PersonResource> resource = new Resources<>(collection);
        final String uriString = ServletUriComponentsBuilder.fromCurrentRequest().toUriString();
        resource.add(new Link(uriString,"self"));
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<PersonResource> get(@PathVariable final Long personId){
        return clubService.getPersonById(personId).map(p->ResponseEntity.ok(new PersonResource(p))).orElseThrow(()->new RuntimeException("Person Not Found"));
    }

    @PostMapping
    public ResponseEntity<PersonResource> create(@RequestBody final Person requestedPerson){
        Person person = new Person(requestedPerson);
        URI uri = MvcUriComponentsBuilder.fromController(this.getClass()).path("{id}").buildAndExpand(person.getId()).toUri();
        return clubService.savePerson(person).map(p->ResponseEntity.created(uri).body(new PersonResource(p))).orElseThrow(()->new RuntimeException("New Entry Is not Created Please Retry"));
    }

    @PutMapping("/{personId}")
    public ResponseEntity<PersonResource> update(@PathVariable final Long personId, @RequestBody final Person person){
        return clubService.updatePerson(personId,person).map(p->ResponseEntity.ok(new PersonResource(p))).orElseThrow(()->new RuntimeException("Cannot perform Update Operation"));
    }

    @DeleteMapping("/{personId}")
    public void delete(@PathVariable final Long personId){
        clubService.deletePerson(personId);
    }

}
