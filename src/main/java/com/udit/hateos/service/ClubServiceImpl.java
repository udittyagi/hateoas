package com.udit.hateos.service;

import com.udit.hateos.domain.Membership;
import com.udit.hateos.domain.Person;
import com.udit.hateos.repository.MembershipRepository;
import com.udit.hateos.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService{
    final private PersonRepository personRepository;
    final private MembershipRepository membershipRepository;

    @Autowired
    public ClubServiceImpl(PersonRepository personRepository, MembershipRepository membershipRepository) {
        this.personRepository = personRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<Person> getAllPerson() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return Optional.of(personRepository.findOne(id));
    }

    @Override
    public Optional<Person> savePerson(Person person) {
        return Optional.of(personRepository.save(person));
    }

    @Override
    public Optional<Person> updatePerson(Long id, Person person) {
        Person person1 = personRepository.findOne(id);
        if(person.getFirstName()!=null)
            person1.setFirstName(person.getFirstName());
        if(person.getLastName()!=null)
            person1.setLastName(person.getLastName());
        return Optional.of(personRepository.save(person1));
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.delete(id);
    }

    @Override
    public void addMembership(Long id, Membership membership) {
        Person person1 = personRepository.findOne(id);
        person1.setMemberships(membership);
        personRepository.save(person1);
    }

    @Override
    public void removeMembership(Long personId, Long membershipId) {
        Person person = personRepository.findOne(personId);
        Membership membership = membershipRepository.findOne(membershipId);
        person.removeMembership(membership);
        personRepository.save(person);
    }

    @Override
    public List<Membership> getAllMembership(Long personId) {
        return personRepository.findOne(personId).getMemberships();
    }

    @Override
    public Optional<Membership> getMembershipById(Long membershipId) {
        return Optional.of(membershipRepository.findOne(membershipId));
    }

    @Override
    public List<Membership> getAllToBeExpiredMemberships() {
        Date date1 = new Date();
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE,5);
        Date date2 = calendar.getTime();
        return membershipRepository.findByRenewalDateBetween(date1,date2);
    }

    @Override
    public List<Membership> getAllExpiredMemberships() {
        Date date = new Date();
        return membershipRepository.findByRenewalDateBefore(date);
    }

    @Override
    public void removeAllExpiredMemberships() {
        List<Membership> expiredMemberships = this.getAllToBeExpiredMemberships();
        for(Membership membership : expiredMemberships){
            membershipRepository.delete(membership);
        }

    }
}
