package com.jinaz.learning.rest.Model;

import com.jinaz.learning.rest.Exceptions.UserNotFoundException;
import com.jinaz.learning.rest.Controller.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public
class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {

        try {
            return EntityModel.of(user, //
                    WebMvcLinkBuilder.linkTo(methodOn(UserController.class).one(user.getUserid())).withSelfRel(),
                    linkTo(methodOn(UserController.class).all()).withRel("employees"));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
