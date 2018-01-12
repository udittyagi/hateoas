package com.udit.hateos.service;

import com.udit.hateos.domain.Membership;
import com.udit.hateos.domain.Person;

import java.util.List;
import java.util.Optional;

public interface ClubService {
    List<Person> getAllPerson();
    Optional<Person> getPersonById(Long personId);
    Optional<Person> savePerson(Person person);
    Optional<Person> updatePerson(Long personId, Person person);
    void deletePerson(Long personId);
    void addMembership(Long personId, Membership membership);
    void removeMembership(Long personId,Long membershipId);
    List<Membership> getAllMembership(Long personId);
    Optional<Membership> getMembershipById(Long membershipId);
    List<Membership> getAllToBeExpiredMemberships();
    List<Membership> getAllExpiredMemberships();
    void removeAllExpiredMemberships();
}
