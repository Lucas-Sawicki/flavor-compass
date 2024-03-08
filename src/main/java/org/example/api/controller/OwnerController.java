package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.business.OwnerService;
import org.example.domain.Owner;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OwnerController {

    private static final String OWNER = "/owner";


    private final OwnerService ownerService;


    @GetMapping(value = OWNER)
    public String owner(Model model) {
        return "owner_portal";
    }

    @GetMapping("/owner/{ownerID}")
    public String showOwner(@PathVariable Integer ownerID, Model model) {
        try {
            Owner findOwner = ownerService.findOwnerById(ownerID);
            if (findOwner != null) {
                model.addAttribute("owner", findOwner);
                log.info("Owner found");
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Owner not found");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error displaying owner", e);
        }
        return "owner_portal";
    }

}
