package org.example.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.business.OwnerService;
import org.example.domain.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
@RequiredArgsConstructor
public class OwnerController {

    private static final String OWNER = "/owner";


    private final OwnerService ownerService;


    @GetMapping("/owner/{ownerID}")
    public String showOwner(@PathVariable Long ownerID, Model model) {
        Owner findOwner = ownerService.findOwnerById(ownerID);
        log.info("Owner found");
        if (findOwner != null) {
            model.addAttribute("owner", findOwner);
            return "owner_portal";
        } else {
            return "error";
        }
    }
}
