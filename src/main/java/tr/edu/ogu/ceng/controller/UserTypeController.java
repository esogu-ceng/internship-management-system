package tr.edu.ogu.ceng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.edu.ogu.ceng.model.UserType;
import tr.edu.ogu.ceng.service.UserTypeService;

@RestController
@RequestMapping("/api/userType")
public class UserTypeController {
    @Autowired
    private UserTypeService userTypeService;

    @PostMapping("/saveUsertype")
    public ResponseEntity<UserType> addUser(@RequestBody UserType userType) {
        UserType userType1 = userTypeService.saveUsertype(userType);
        return new ResponseEntity<>(userType1, HttpStatus.CREATED);
    }
}
