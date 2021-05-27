package com.jinaz.learning.rest.Model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.jinaz.learning.rest.Exceptions.InsertNotFoundException;
import com.jinaz.learning.rest.Controller.InsertionController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class InsertModelAssembler implements RepresentationModelAssembler<Insertion, EntityModel<Insertion>>{
    @Override
    public EntityModel<Insertion> toModel(Insertion insrt) {

        // Unconditional links to single-item resource and aggregate root

        EntityModel<Insertion> insertModel = null;
        try {
            insertModel = EntityModel.of(insrt,
                    WebMvcLinkBuilder.linkTo(methodOn(InsertionController.class).one(insrt.getId())).withSelfRel(),
                    linkTo(methodOn(InsertionController.class).all()).withRel("inserts"));
        } catch (InsertNotFoundException e) {
            e.printStackTrace();
        }

        // Conditional links based on state of the order

        if (insrt.getStatus() == Status.IN_PROGRESS) {
            try {
            insertModel.add(linkTo(methodOn(InsertionController.class).cancel(insrt.getId())).withRel("cancel"));

                insertModel.add(linkTo(methodOn(InsertionController.class).complete(insrt.getId())).withRel("complete"));
            } catch (InsertNotFoundException e) {
                e.printStackTrace();
            }
        }

        return insertModel;
    }
}
