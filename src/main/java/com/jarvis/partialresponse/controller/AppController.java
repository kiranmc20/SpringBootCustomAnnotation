package com.jarvis.partialresponse.controller;

import com.jarvis.partialresponse.annotation.PartialResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AppController {

    @GetMapping("/partialresponse")
    @PartialResponse(enabled = true)
    public ResponseEntity<Map<String, String>> testPartialResponse(@RequestParam("fields") @Nullable String fields){
        Map<String, String> hmap = new HashMap<>();
        hmap.put("id", "1234");
        hmap.put("name", "John");
        hmap.put("city", "Mum");
        return new ResponseEntity<>(hmap, HttpStatus.OK);
    }
}
