package com.ssafy.icecreamapp.model.dto.request;

import com.ssafy.icecreamapp.model.dto.OrderDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private int spoon;
    private int dryice;
    private String email;
    private List<OrderDetailRequest> details;
}
