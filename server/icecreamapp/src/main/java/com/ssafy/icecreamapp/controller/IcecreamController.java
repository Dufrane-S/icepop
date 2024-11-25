package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.request.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.Icecream;
import com.ssafy.icecreamapp.service.IcecreamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ice")
public class IcecreamController {
    private final IcecreamService icecreamService;

    @PostMapping("/list-with-con")
    @Operation(summary = "아이스크림 리스트", description = "<b>아이스크림 종류 String : type 아이스크림, 케이크, 레디팩<br>" +
            "할인율 int : eventRate<br>" +
            "다 비워져있으면 모든 아이스크림 반환 <br>" +
            "int : age(남자 1, 여자 2)와 int : gender가 동시에 채워져 있을 경우 인기순 반영<br>" +
            "(둘 다 있어야 인기순 ex) age:10, gender:1 => 10대남성인기순)")
    public ResponseEntity<List<Icecream>> iceWithCon(@RequestBody IceSelectCon iceSelectCon) {
        return ResponseEntity.ok(icecreamService.getIcecreamsWithCon(iceSelectCon));
    }

    @GetMapping("/img/{img}")
    @Operation(summary = "제품 이미지 반환", description = "<b>string : img 아이스크림 정보의 img를 넣으면 img를 반환</b>")
    public ResponseEntity<byte[]> img(
            @Parameter(description = "아이스크림 정보 조회의 img", example = "nycheese")
            @PathVariable String img
    ) throws IOException {
        Resource resource = new ClassPathResource("img/" + img + ".png");
        InputStream inputStream = resource.getInputStream();
        byte[] imageBytes = inputStream.readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "아이스크림 정보", description = "<b>int : id 제품 번호 입력하여 제품 정보 획득</b>")
    public ResponseEntity<Icecream> icecream(
            @Parameter(example = "1" , description = "제품 번호")
            @PathVariable Integer id) {
        return ResponseEntity.ok(icecreamService.getIcecreamById(id));
    }
}
