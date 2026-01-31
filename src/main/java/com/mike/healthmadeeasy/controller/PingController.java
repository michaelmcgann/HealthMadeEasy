package com.mike.healthmadeeasy.controller;

import com.mike.healthmadeeasy.dto.response.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController()
@RequestMapping("/api")
public class PingController {

    @GetMapping("/ping")
    public PingResponse ping() {

        return ( new PingResponse( "UP", Instant.now() ) );
    }

}
