package uz.sb.contactservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.sb.contactservice.domain.dto.request.ContactRequest;
import uz.sb.contactservice.domain.dto.response.ContactResponse;
import uz.sb.contactservice.domain.entity.ContactEntity;
import uz.sb.contactservice.service.ContactServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contact")
public class ContactController {

    private final ContactServiceImpl contactService;

    @PostMapping("/save")
    public ContactResponse save(@RequestBody ContactRequest contactRequest) {
        return contactService.save(contactRequest);
    }

    @GetMapping("/get-all")
    List<ContactResponse> getAll() {
        return contactService.findAll();
    }

    @PutMapping("/update/{id}")
    public ContactResponse update(@PathVariable("id") Long id, @RequestBody ContactRequest contactRequest) {
        return contactService.update(id, contactRequest);
    }

    @GetMapping("/{id}")
    ContactEntity get(@PathVariable("id") Long id) {
        return contactService.findById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        contactService.delete(id);
    }

    @GetMapping("/search")
    List<ContactResponse> search(@RequestBody String name) {
        return contactService.searchByName(name);
    }
}
