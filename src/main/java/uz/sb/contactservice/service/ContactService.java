package uz.sb.contactservice.service;

import org.springframework.stereotype.Service;
import uz.sb.contactservice.domain.entity.ContactEntity;
import uz.sb.domain.dto.request.ContactRequest;
import uz.sb.domain.dto.response.ContactResponse;


import java.util.List;

@Service
public interface ContactService {

     ContactResponse save(ContactRequest contactRequest);

    ContactEntity findById(Long id);

    List<ContactResponse> findAll();

    ContactResponse update(Long id, ContactRequest contactRequest);

    void delete(Long id);

    List<ContactResponse> searchByName(String name);

    boolean existsByContactName(String contactName);

}
