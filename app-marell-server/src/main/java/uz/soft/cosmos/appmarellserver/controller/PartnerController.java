package uz.soft.cosmos.appmarellserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.soft.cosmos.appmarellserver.entity.Partner;
import uz.soft.cosmos.appmarellserver.payload.ApiResponse;
import uz.soft.cosmos.appmarellserver.payload.ReqAddUserToPartner;
import uz.soft.cosmos.appmarellserver.payload.ReqNameId;
import uz.soft.cosmos.appmarellserver.repository.AttachmentRepository;
import uz.soft.cosmos.appmarellserver.repository.PartnerRepository;

import java.util.UUID;

/**
 * Created by Sherlock on 25.01.2022.
 */

@RestController
@RequestMapping("/api/partner")
public class PartnerController {
    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public HttpEntity<?> save(@RequestBody ReqNameId reqNameId) {
        try {
            Partner partner;

            if (reqNameId.getId() == null)
                partner = new Partner();
            else
                partner = partnerRepository.getOne(reqNameId.getId());

            if (reqNameId.getPhoto() != null)
                partner.setPhoto(attachmentRepository.getOne(reqNameId.getPhoto()));

            partner.setName(reqNameId.getName());
            partner.setDescription(reqNameId.getDescription());
            partner.setCategory(reqNameId.getCategory());

            partnerRepository.save(partner);

            if (reqNameId.getId() == null) {
                return ResponseEntity.ok(new ApiResponse(true, "Добавлено"));
            } else {
                return ResponseEntity.ok(new ApiResponse(true, "Изменено"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @GetMapping
    public HttpEntity<?> getAll() {
        return ResponseEntity.ok(new ApiResponse(true, "", partnerRepository.findAllByOrderByCreatedAtDesc()));
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        try {
            partnerRepository.deleteById(id);
            return ResponseEntity.ok(new ApiResponse(true, "Удалено"));
        } catch (Exception e) {
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }
}
