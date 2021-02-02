package com.salinteam.emdadcustomerclub.controller;


import com.salinteam.emdadcustomerclub.exception.BadRequestException;
import com.salinteam.emdadcustomerclub.model.GroupLevel;
import com.salinteam.emdadcustomerclub.payload.ApiResponse;
import com.salinteam.emdadcustomerclub.payload.GLRequest;
import com.salinteam.emdadcustomerclub.repository.GroupLevelRepository;
import com.salinteam.emdadcustomerclub.security.CompanyPrincipal;
import com.salinteam.emdadcustomerclub.security.CurrentUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/business")
@Api(value = "/api/business", description = "client(company) functionalities such as adding user , event , transactions and use them to score users", produces = "application/json")
public class GLController {


    @Autowired
    GroupLevelRepository groupLevelRepository;

    @PostMapping("/gls/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
    @PreAuthorize("hasRole('ADMIN_GL')")
    public ResponseEntity<?> addGroupLeves(@Valid @RequestBody List<GLRequest> glRequests,
                                           @CurrentUser CompanyPrincipal currentUser) {

        if (glRequests.stream().map(glRequest -> glRequest.getLevelName())
                .collect(Collectors.toList()).stream().distinct().count() != glRequests.size()) {
            throw new BadRequestException("all level names are not unique!");

        }


        for (int i = 0; i < glRequests.size() - 1; i++) {
            GLRequest glRequest = glRequests.get(i);
            GLRequest nextglRequest = glRequests.get(i + 1);
            Long score = glRequest.getScore();
            Long nextscore = nextglRequest.getScore();
            if (score >= nextscore)
                throw new BadRequestException("sequential level scores not required");
        }

        groupLevelRepository.deleteAll();

        for (int i = 0; i < glRequests.size() - 1; i++) {
            GLRequest glRequest = glRequests.get(i);
            GLRequest nextglRequest= glRequests.get(i + 1);

            GroupLevel save = groupLevelRepository.save(new GroupLevel(((long) i),
                    glRequest.getLevelName(), glRequest.getScore(), nextglRequest.getScore()));
        }
        int index = glRequests.size() - 1;
        GLRequest glRequest = glRequests.get(index);
        GroupLevel save = groupLevelRepository.save(new GroupLevel(((long) index), glRequest.getLevelName()
                , glRequest.getScore(),100000000000L));


        return ResponseEntity.ok(new ApiResponse(true, "updated"));
    }

}
