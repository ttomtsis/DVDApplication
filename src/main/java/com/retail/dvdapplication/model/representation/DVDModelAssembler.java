package com.retail.dvdapplication.model.representation;

import com.retail.dvdapplication.controller.DVDController;
import com.retail.dvdapplication.model.entity.DVD;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DVDModelAssembler extends RepresentationModelAssemblerSupport<DVD, DVDModel> {
    public DVDModelAssembler() {
        super(DVDController.class, DVDModel.class);
    }

    @Override
    @NonNull
    public DVDModel toModel(@NonNull DVD entity) {
        DVDModel model = new DVDModel();

        BeanUtils.copyProperties(entity, model);
        model.setId(entity.getId());
        model.add(linkTo(methodOn(DVDController.class).searchDVDByID(entity.getId())).withSelfRel());
        model.add(linkTo(methodOn(DVDController.class).searchDVDByName(entity.getName(), 0, 1)).withSelfRel());
        model.add(linkTo(methodOn(DVDController.class).deleteDVDByID(entity.getId())).withRel("Delete"));
        model.add(linkTo(methodOn(DVDController.class).updateDVDByID(entity.getId(), entity)).withRel("Update"));
        model.add(linkTo(methodOn(DVDController.class).searchAllDVDs(0, 10)).withRel("All DVDs"));
        return model;
    }

    public PagedModel<DVDModel> createPagedModelForSearchByName(Page<DVD> dvdPage, String name ) {

        PagedModel<DVDModel> pagedModel = createModelFromPage(dvdPage);

        pagedModel.add(linkTo(methodOn(DVDController.class).searchDVDByName(name, dvdPage.getNumber(), dvdPage.getSize())).withSelfRel());

        if (dvdPage.hasNext()) {
            pagedModel.add(linkTo(methodOn(DVDController.class).searchDVDByName(name, dvdPage.getNumber() + 1, dvdPage.getSize())).withRel("next"));
        }

        if (dvdPage.hasPrevious()) {
            pagedModel.add(linkTo(methodOn(DVDController.class).searchDVDByName(name, dvdPage.getNumber() - 1, dvdPage.getSize())).withRel("previous"));
        }

        return pagedModel;
    }

    public PagedModel<DVDModel> createPagedModelForSearchAll(Page<DVD> dvdPage) {

        PagedModel<DVDModel> pagedModel = createModelFromPage(dvdPage);

        pagedModel.add(linkTo(methodOn(DVDController.class).searchAllDVDs(dvdPage.getNumber(), dvdPage.getSize())).withSelfRel());

        if (dvdPage.hasNext()) {
            pagedModel.add(linkTo(methodOn(DVDController.class).searchAllDVDs(dvdPage.getNumber() + 1, dvdPage.getSize())).withRel("next"));
        }

        if (dvdPage.hasPrevious()) {
            pagedModel.add(linkTo(methodOn(DVDController.class).searchAllDVDs(dvdPage.getNumber() - 1, dvdPage.getSize())).withRel("previous"));
        }

        return pagedModel;
    }

    private PagedModel<DVDModel>createModelFromPage ( Page<DVD> dvdPage ) {

        // Convert DVD objects to DVDModel representation models
        List<DVDModel> dvdModels = dvdPage.getContent().stream().map(this::toModel).collect(Collectors.toList());

        // Create PagedModel with links to next and previous pages
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(dvdPage.getSize(), dvdPage.getNumber(), dvdPage.getTotalElements());

        return PagedModel.of(dvdModels, pageMetadata);
    }
}
