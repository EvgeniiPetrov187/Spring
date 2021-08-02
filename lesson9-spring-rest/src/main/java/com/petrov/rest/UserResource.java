package com.petrov.rest;

import com.petrov.controller.NotFoundException;

import com.petrov.controller.UserDto;
import com.petrov.service.UserService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public UserDto findById(@PathVariable("id") Long id) {
        return userService.findById(id)
                .orElseThrow(()-> new NotFoundException("User not found"));
    }

    @PostMapping(produces = "application/json")
    public UserDto create(@RequestBody UserDto userDto) throws BadRequestException {
        if(userDto.getId() != null){
            throw new BadRequestException("Request false");
        }
        userService.save(userDto);
        return userDto;
    }

    @PutMapping(produces = "application/json")
    public void update(@RequestBody UserDto userDto) throws BadRequestException {
        if(userDto.getId() == null){
            throw new BadRequestException("Request false");
        }
        userService.save(userDto);
    }

    @DeleteMapping(path = "/{id}", produces = "application/json")
    public void delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
    }
}
