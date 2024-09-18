package com.jarvis.partialresponse.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * Create an Aspect class and a method annotated with @Around which holds
 * the logic to be performed
 */
@Aspect
@Component
public class PartialResponseAspect {

    @Around("@annotation(partialResponse)")
    public Object sendPartialResponse(ProceedingJoinPoint joinPoint, PartialResponse partialResponse) throws Throwable {
        if(partialResponse.enabled()) {
            String requiredFields = (String) joinPoint.getArgs()[0];
            System.out.println("Partial response fields input: " + requiredFields);
            ResponseEntity<Map<String, String>> responseEntity = (ResponseEntity<Map<String, String>>) joinPoint.proceed();
            Map<String, String> responseMap = responseEntity.getBody();
            return new ResponseEntity<>(processPartialResponse(requiredFields, responseMap), HttpStatus.OK);
        }
        return joinPoint.proceed();
    }

    private Map<String, String> processPartialResponse(String requiredFields, Map<String, String> responseMap){
        Map<String, String> filteredResponseMap = new LinkedHashMap<>();
        if(!requiredFields.isEmpty()){
            String[] fieldsArr = requiredFields.split(",");
            Set<String> fieldset = new LinkedHashSet<>(Arrays.asList(fieldsArr));
            fieldset.stream().forEach(f -> {
                if(responseMap.containsKey(f)){
                    filteredResponseMap.put(f, responseMap.get(f));
                }
            });
        }
        return filteredResponseMap;
    }
}
