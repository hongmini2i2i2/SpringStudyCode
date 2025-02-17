package com.example.testproject.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.testproject.data.MemberDTO;

@RestController
@RequestMapping("/api/v1/put-api")
public class PutController {

    // http://localhost:8080/api/v1/put-api/default
    @PutMapping(value = "/default")
    public String putMethod() {
        return "Hello World!";
    }

    // http://localhost:8080/api/v1/put-api/member
    @PutMapping(value = "/member")
    public String postMember(@RequestBody Map<String, Object> putData) { // Map<String, Object> 형태로 바디 요청.
        StringBuilder sb = new StringBuilder(); //그냥 append로 문자열 쉽게 추가할 수 있는거.

        putData
                .entrySet()
                .forEach(
                        map -> {
                            sb.append(map.getKey() + " : " + map.getValue() + "\n");
                        });

    /*
    param.forEach((key, value) -> sb.append(key).append(" : ").append(value).append("\n"));
     */

        return sb.toString();
    }

    // http://localhost:8080/api/v1/put-api/member2
    @PutMapping(value = "/member1")
    public String postMemberDto1(@RequestBody MemberDTO memberDTO) {
        return memberDTO.toString(); //이상하게 보내는거니까 쓸모없어.
    }

    // http://localhost:8080/api/v1/put-api/member2
    @PutMapping(value = "/member2")
    public MemberDTO postMemberDto2(@RequestBody MemberDTO memberDTO) {
        return memberDTO;
    }

    // http://localhost:8080/api/v1/put-api/member2
    @PutMapping(value = "/member3")
    public ResponseEntity<MemberDTO> postMemberDto3(@RequestBody MemberDTO memberDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED/**httpStatus를 자기가 설정해서 넣기 가능.*/).body(memberDTO); //http메소드로 body 설정해서 보내기 가능.(결과는 2번이랑 같음.)
    }
}