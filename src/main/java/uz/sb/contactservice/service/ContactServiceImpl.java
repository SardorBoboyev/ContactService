package uz.sb.contactservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.sb.contactservice.clients.AuthServiceClient;
import uz.sb.contactservice.domain.entity.ContactEntity;
import uz.sb.contactservice.domain.exception.UnauthorizedException;
import uz.sb.contactservice.domain.dto.request.ContactRequest;
import uz.sb.contactservice.domain.dto.response.ContactResponse;
import uz.sb.contactservice.repository.ContactRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final AuthServiceClient authServiceClient;


    @Override
    public ContactResponse save(ContactRequest contactRequest) {

        boolean isAuthorized = authServiceClient.isUserAuthorized(contactRequest.getContactUserId());

        if (!isAuthorized) {
            throw new UnauthorizedException("User is not authorized");
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
                .userId(contactEntity.getUserId())
                .build();
    }

    @Override
    public ContactEntity findById(Long id) {
        return contactRepository.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public List<ContactResponse> findAll() {
        return contactRepository.findAll().stream()
                .map(customer -> ContactResponse.builder()
                .id(customer.getId())
                .contactName(customer.getContactName())
                .contactUserId(customer.getContactUserId())
                .userId(customer.getUserId())
                .build())
                .collect(Collectors.toList());
    }

    @Override
    public ContactResponse update(Long id, ContactRequest contactRequest) {
        ContactEntity existsContact = findById(id);
        if (existsContact == null) {
            throw new RuntimeException("Contact not found");
        }
        existsContact.setContactName(contactRequest.getContactName());
        existsContact.setContactUserId(contactRequest.getContactUserId());

        ContactEntity savedContact = contactRepository.save(existsContact);

        return ContactResponse.builder()
                .id(savedContact.getId())
                .contactName(savedContact.getContactName())
                .contactUserId(savedContact.getContactUserId())
                //.userId(savedContact.getUserId())
                .build();
    }

    @Override
    public void delete(Long id) {
        contactRepository.deleteById(id);
    }

    @Override
    public List<ContactResponse> searchByName(String name) {
        return contactRepository.searchByContactNameContaining(name);
    }
}
