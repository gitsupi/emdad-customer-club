package com.salinteam.emdadcustomerclub.controller;


import com.salinteam.emdadcustomerclub.model.RoleName;
import com.salinteam.emdadcustomerclub.payload.AddGrouplevelRequest;
import com.salinteam.emdadcustomerclub.payload.AddNewUserRequest;
import com.salinteam.emdadcustomerclub.payload.GLRequest;
import com.salinteam.emdadcustomerclub.security.CurrentUser;
import com.salinteam.emdadcustomerclub.security.UserPrincipal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/business")
@Api(value = "/api/business/gl", description = "client(company) functionalities such as adding user , event , transactions and use them to score users", produces = "application/json")
public class GLController {


    @PostMapping("/gls/add")
    @ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, paramType = "header", example = "Bearer access_token")
//    @PreAuthorize("hasRole('ADMIN_GL')")
    public ResponseEntity<?> addGroupLeves(@Valid @RequestBody List<GLRequest> glRequests,
                                        @CurrentUser UserPrincipal currentUser) {

        return null;
    }

}
