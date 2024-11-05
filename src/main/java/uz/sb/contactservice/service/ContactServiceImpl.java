package uz.sb.contactservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.sb.contactservice.clients.AuthServiceClient;
import uz.sb.contactservice.domain.dto.request.ContactRequest;
import uz.sb.contactservice.domain.dto.response.ContactResponse;
import uz.sb.contactservice.domain.dto.response.UserResponse;
import uz.sb.contactservice.domain.entity.ContactEntity;
import uz.sb.contactservice.domain.exception.DataNotFoundException;
import uz.sb.contactservice.domain.exception.UserNotFoundException;
import uz.sb.contactservice.repository.ContactRepository;


import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final AuthServiceClient authServiceClient;


    @Override
    public ContactResponse save(ContactRequest contactRequest) {

        UserResponse existsUser = authServiceClient.findById(contactRequest.getContactUserId());

        if (existsUser == null) {
            throw new UserNotFoundException("User not found with id " + contactRequest.getContactUserId());
        }

        ContactEntity existingContactEntity = contactRepository.findByContactUserIdAndContactName(contactRequest.getContactUserId(), contactRequest.getContactName());

        if (existingContactEntity != null) {
            // Update the existing contact's name if it's found
            existingContactEntity.setContactName(contactRequest.getContactName());
            contactRepository.save(existingContactEntity);
            return ContactResponse.builder()
                    .id(existingContactEntity.getId())
                    .contactName(existingContactEntity.getContactName())
                    .contactUserId(existingContactEntity.getContactUserId())
//                    .userId(existingContactEntity.getUserId())
                    .build();
        }

        ContactEntity contactEntity = ContactEntity.builder()
                .contactName(contactRequest.getContactName())
                .contactUserId(contactRequest.getContactUserId())
                .userId(contactRequest.getUserId())
                .build();
        contactRepository.save(contactEntity);

        return ContactResponse.builder()
                .id(contactEntity.getId())
                .contactName(contactEntity.getContactName())
                .contactUserId(contactEntity.getContactUserId())
//                .userId(contactEntity.getUserId())
                .build();
    }


    @Override
    public ContactEntity findById(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Contact not found"));
    }

    @Override
    public List<ContactResponse> findAll() {
        return contactRepository.findAll().stream()
                .map(customer -> ContactResponse.builder()
                .id(customer.getId())
                .contactName(customer.getContactName())
                .contactUserId(customer.getContactUserId())
//                .userId(customer.getUserId())
                .build())
                .collect(Collectors.toList());
    }

    @Override
    public ContactResponse update(Long id, ContactRequest contactRequest) {
        ContactEntity existsContact = findById(id);
        if (existsContact == null) {
            throw new DataNotFoundException("Contact not found");
        }

        existsContact.setContactName(contactRequest.getContactName());

        ContactEntity savedContact = contactRepository.save(existsContact);

        return ContactResponse.builder()
                .id(savedContact.getId())
                .contactName(savedContact.getContactName())
                .contactUserId(savedContact.getContactUserId())
                .build();
    }


    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactResponse> searchByName(String contactName) {
        if (contactName == null || contactName.trim().isEmpty()) {
            return Collections.emptyList();
        }
        List<ContactEntity> contactEntities = contactRepository.findByContactNameContaining(contactName);
        System.out.println("Searching contacts: " + contactEntities);

        return contactEntities.stream()
                .map(contactEntity -> new ContactResponse(
                        contactEntity.getId(),
//                        contactEntity.getUserId(),
                        contactEntity.getContactUserId(),
                        contactEntity.getContactName()
                ))
                .collect(Collectors.toList());
    }



    @Override
    public List<ContactResponse> findAllContactsByUserId(Long userId) {
        return contactRepository.findByUserId(userId).stream()
                        .map(contactEntity -> ContactResponse.builder()
                        .id(contactEntity.getId())
                        .contactName(contactEntity.getContactName())
                        .contactUserId(contactEntity.getContactUserId())
//                        .userId(contactEntity.getUserId())
                        .build()
                ).collect(Collectors.toList());
    }


}
