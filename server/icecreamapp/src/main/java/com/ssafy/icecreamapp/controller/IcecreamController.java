package com.ssafy.icecreamapp.controller;

import com.ssafy.icecreamapp.model.dto.request.IceSelectCon;
import com.ssafy.icecreamapp.model.dto.Icecream;
import com.ssafy.icecreamapp.service.IcecreamService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "아이스크림 리스트", description = "아이스크림 종류 String type, 할인율 int eventRate 받음 //// 다 비워져있으면 모든 아이스크림 반환 ////" +
            "age 와 gender가 채워져 있을 경우 인기순 반영 (둘 다 있어야 인기순 ex)age:10, gender:1 => 10대남성인기순)")
    public ResponseEntity<List<Icecream>> iceWithCon(@RequestBody IceSelectCon iceSelectCon) {
        return ResponseEntity.ok(icecreamService.getIcecreamsWithCon(iceSelectCon));
    }

    @GetMapping("/img/{name}")
    @Operation(summary = "제품 이미지 반환", description = "아이스크림 정보의 img를 넣으면 img를 반환")
    public ResponseEntity<byte[]> img(@PathVariable String name) throws IOException {
        Resource resource = new ClassPathResource("img/" + name + ".png");
        InputStream inputStream = resource.getInputStream();
        byte[] imageBytes = inputStream.readAllBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "아이스크림 정보", description = "아이스크림 아이디를 입력하여 아이스크림 정보 획득")
    public ResponseEntity<Icecream> icecream(@PathVariable int id) {
        return ResponseEntity.ok(icecreamService.getIcecreamById(id));
    }
}
