package com.example.ElectronicStore.Controller;

import com.example.ElectronicStore.Dto.OrderDto;
import com.example.ElectronicStore.Service.OrderService;
import com.example.ElectronicStore.Utils.ApiResponseMessage;
import com.example.ElectronicStore.Utils.OrderRequestBody;
import com.example.ElectronicStore.Utils.PageableResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(path = "/api/v1/order")
@RestController()
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // create an order
    @PostMapping()
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequestBody orb) {
        OrderDto orderDto = orderService.createNewOrder(orb);
        return new ResponseEntity<>(orderDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<PageableResponse<OrderDto>> getAllOrderOfUser(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                        @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                                        @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
                                                                        @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy) {
        Long userId = 52L;
        PageableResponse<OrderDto> allOrderOfUser = orderService.getAllOrderOfUser(userId, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allOrderOfUser, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<ApiResponseMessage> deleteOrder(@RequestParam Long orderId){
        ApiResponseMessage apiResponseMessage = orderService.deleteOrder(orderId);
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }

    @GetMapping(path="/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id){
        OrderDto orderById = orderService.getOrderById(id);
        return new ResponseEntity<>(orderById,HttpStatus.OK);
    }
}
