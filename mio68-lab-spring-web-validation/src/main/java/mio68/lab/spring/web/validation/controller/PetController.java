package mio68.lab.spring.web.validation.controller;

import lombok.extern.slf4j.Slf4j;
import mio68.lab.spring.web.validation.dto.Pet;
import mio68.lab.spring.web.validation.dto.Pets;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Validated
public class PetController {

    @PostMapping("/pet")
    public Pet createPet(@RequestBody @Valid Pet pet) {
      log.info("{}", pet);
      return pet;
    }


    @PostMapping("/pets")
    public void createPets(@RequestBody @Valid Pets pets) {
        log.info("{}", pets);
    }
}
