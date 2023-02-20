package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/users/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        //TODO implement this method
        //URI createdUserUri = URI.create("");
        try {
            User user = new User(userDto);
            usersService.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        //TODO implement this method
        return ResponseEntity.ok(usersService.all());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        //TODO implement this method
        //return ResponseEntity.ok(null);
        try {
            return new ResponseEntity<User>(usersService.findById(id).get(), HttpStatus.OK);
        } catch (Exception e) {
            throw new UserNotFoundException(id);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") String id, @RequestBody UserDto userDto) {
        //TODO implement this method
        User user = new User(userDto);
        Optional<User> users = usersService.findById(id);
        if(users.isPresent()){
            usersService.update(user,id);
            usersService.save(users.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            throw new UserNotFoundException(id);

        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        //TODO implement this method
        Optional<User> users = usersService.findById(id);
        if(users.isPresent()){
            usersService.deleteById(id);
            usersService.save(users.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            throw new UserNotFoundException(id);

        }
    }
}
