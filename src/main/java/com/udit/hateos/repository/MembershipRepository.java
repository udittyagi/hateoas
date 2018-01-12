package com.udit.hateos.repository;

import com.udit.hateos.domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership,Long> {
    List<Membership> findByRenewalDateBetween(Date date1, Date date2);
    List<Membership> findByRenewalDateBefore(Date date);
}
