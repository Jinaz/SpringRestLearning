package com.jinaz.learning.rest.Controller;

import java.util.List;
import java.util.stream.Collectors;

import com.jinaz.learning.rest.Model.ChatRepository;
import com.jinaz.learning.rest.Exceptions.UserNotFoundException;
import com.jinaz.learning.rest.Model.User;
import com.jinaz.learning.rest.Model.UserModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class UserController {
    private final ChatRepository repository;

    private final UserModelAssembler assembler;
    UserController(ChatRepository repository, UserModelAssembler uma) {
        this.repository = repository;
        this.assembler = uma;
    }


    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> users = repository.findAll().stream()
                .map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @PostMapping("/users")
    ResponseEntity<EntityModel<User>> newUser(@RequestBody User newUser) {
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    /*
    @GetMapping("/users/{id}")
    User one(@PathVariable int id) throws UserNotFoundException {

        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }*/

    @GetMapping("/users/{id}")
    public EntityModel<User> one(@PathVariable int id) throws UserNotFoundException {

        User user = repository.findById(id) //
                .orElseThrow(() -> new UserNotFoundException(id));

        return assembler.toModel(user);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<EntityModel<User>> replaceUser(@RequestBody User newuser, @PathVariable int id) {
        User updatedUser = repository.findById(id).map(user -> {
                    user.setPrename(newuser.getPrename());
                    user.setSurname(newuser.getSurname());
                    user.setMessage(newuser.getMessage());
                    return repository.save(user);
                }).orElseGet(() -> {
                    newuser.setUserid(id);
                    return repository.save(newuser);
                });

        EntityModel<User> entityModel = assembler.toModel(updatedUser);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/users/{id}")
    ResponseEntity<Object> deleteUser(@PathVariable int id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
