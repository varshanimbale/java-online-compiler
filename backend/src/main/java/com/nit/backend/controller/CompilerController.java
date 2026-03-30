//package com.nit.backend.controller;
//
//import org.springframework.web.bind.annotation.*;
//
//import com.nit.backend.service.CompilerService;
//
//@RestController
////@CrossOrigin(origins = "*")
////@CrossOrigin(origins = "http://127.0.0.1:5501")
//public class CompilerController {
//
//    @PostMapping("/run")
//    public String runCode(@RequestBody String code) {
//        return CompilerService.executeJava(code);
//    }
//}

package com.nit.backend.controller;

import org.springframework.web.bind.annotation.*;

import com.nit.backend.service.CompilerService;

@RestController
@CrossOrigin("*")
public class CompilerController {

    @PostMapping("/run")
    public String runCode(@RequestBody CodeRequest request) {
        return CompilerService.executeJava(request.getCode(), request.getInput());
    }
}