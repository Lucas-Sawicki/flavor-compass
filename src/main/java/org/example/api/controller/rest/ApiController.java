package org.example.api.controller.rest;

import lombok.AllArgsConstructor;
import org.example.infrastructure.database.repository.OwnerRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiController.BASE_PATH)
@AllArgsConstructor
public class ApiController {

        public static final String BASE_PATH = "/api/";
        private OwnerRepository ownerRepository;


    }

