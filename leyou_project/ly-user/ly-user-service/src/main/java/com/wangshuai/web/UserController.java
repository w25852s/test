package com.wangshuai.web;

import com.wangshuai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
@RequestMapping("user")
public class UserController  {

@Autowired
private UserService userService;


    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean> checkData(@PathVariable("data") String data, @PathVariable("type") Integer type) {
        return ResponseEntity.ok(userService.checkData(data, type));
    } 

    @GetMapping("code/{phone}")
    public ResponseEntity<Void> codeVerify(@PathVariable("phone") String phone) {
        userService.codeVerify(phone);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
