package com.ssafy.icecreamapp.model.dto.request;

import com.ssafy.icecreamapp.model.dto.OrderDetail;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequest {
    private String email;
    private int spoon;
    private int dryice;
    private int isForHere;
    private List<OrderDetailRequest> details;
}
