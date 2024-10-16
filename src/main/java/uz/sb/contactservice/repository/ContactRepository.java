package uz.sb.contactservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sb.contactservice.domain.dto.response.ContactResponse;
import uz.sb.contactservice.domain.entity.ContactEntity;


import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {

    List<ContactResponse> searchByContactNameContaining(String name);

    ContactEntity findByContactUserIdAndContactName(Long contactUserId, String contactName);

    boolean existsByContactName(String contactName);

}
