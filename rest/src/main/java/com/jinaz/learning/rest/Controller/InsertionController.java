package com.jinaz.learning.rest.Controller;

import com.jinaz.learning.rest.Exceptions.InsertNotFoundException;
import com.jinaz.learning.rest.Model.InsertModelAssembler;
import com.jinaz.learning.rest.Model.Insertion;
import com.jinaz.learning.rest.Model.InsertionRepository;
import com.jinaz.learning.rest.Model.Status;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class InsertionController {

    private final InsertionRepository insrtRepository;
    private final InsertModelAssembler assembler;

    InsertionController(InsertionRepository irt, InsertModelAssembler assembler){
        this.insrtRepository = irt;
        this.assembler = assembler;
    }

    @GetMapping("/insertions")
    public CollectionModel<EntityModel<Insertion>> all() {

        List<EntityModel<Insertion>> insertions = insrtRepository.findAll().stream() //
                .map(assembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(insertions, //
                linkTo(methodOn(InsertionController.class).all()).withSelfRel());
    }

    @GetMapping("/insertions/{id}")
    public EntityModel<Insertion> one(@PathVariable Long id) throws InsertNotFoundException {

        Insertion insertion = insrtRepository.findById(id) //
                .orElseThrow(() -> new InsertNotFoundException(id));

        return assembler.toModel(insertion);
    }

    @PostMapping("/insertions")
    ResponseEntity<EntityModel<Insertion>> newinsertion(@RequestBody Insertion insertion) throws InsertNotFoundException {

        insertion.setStatus(Status.IN_PROGRESS);
        Insertion newinsertion = insrtRepository.save(insertion);

        return ResponseEntity //
                .created(linkTo(methodOn(InsertionController.class).one(newinsertion.getId())).toUri()) //
                .body(assembler.toModel(newinsertion));
    }
    @DeleteMapping("/insertions/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable Long id) throws InsertNotFoundException {

        Insertion insertion = insrtRepository.findById(id) //
                .orElseThrow(() -> new InsertNotFoundException(id));

        if (insertion.getStatus() == Status.IN_PROGRESS) {
            insertion.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toModel(insrtRepository.save(insertion)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't cancel an insert with the " + insertion.getStatus() + " status"));
    }

    @PutMapping("/insertions/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) throws InsertNotFoundException {

        Insertion insertion = insrtRepository.findById(id) //
                .orElseThrow(() -> new InsertNotFoundException(id));

        if (insertion.getStatus() == Status.IN_PROGRESS) {
            insertion.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toModel(insrtRepository.save(insertion)));
        }

        return ResponseEntity //
                .status(HttpStatus.METHOD_NOT_ALLOWED) //
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed") //
                        .withDetail("You can't complete an insertion that is in the " + insertion.getStatus() + " status"));
    }

}
