package org.npeonelove.profileservice.repository;

import org.npeonelove.profileservice.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findProfileById(long id);

    Profile findProfileByEmail(String email);
}
