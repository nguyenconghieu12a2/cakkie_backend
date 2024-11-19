package com.cakkie.backend.controller.adminCancelOrder;

import com.cakkie.backend.dto.adminCaceledOrder.AdminCancelOrderDTO;
import com.cakkie.backend.model.userSite;
import com.cakkie.backend.service.adminCancelOrder.AdminCancelOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
public class AdminCancelOrderController {
    private final AdminCancelOrderService adminCancelOrderService;

    @GetMapping("/cacel-order")
    public ResponseEntity<List<AdminCancelOrderDTO>> cancelOrder() {
        List<AdminCancelOrderDTO> cancelOrder = adminCancelOrderService.getAllCanceledOrder();
        return new ResponseEntity<>(cancelOrder, HttpStatus.OK);
    }

    @GetMapping("/cancel-order/detail/{userId}")
    public ResponseEntity<List<AdminCancelOrderDTO>> detailCancelOrderByUserId(@PathVariable int userId) {
        List<AdminCancelOrderDTO> detailCancelOrders = adminCancelOrderService.getDetailCancelOrderByUserId(userId);
        return new ResponseEntity<>(detailCancelOrders, HttpStatus.OK);
    }

    @GetMapping("/cancel-order/product-detail/{userId}")
    public ResponseEntity<List<AdminCancelOrderDTO>> getProductCancelDetailByUserId(@PathVariable int userId) {
        List<AdminCancelOrderDTO> productCancelDetails = adminCancelOrderService.getDetailProductCancelByUserId(userId);
        return new ResponseEntity<>(productCancelDetails, HttpStatus.OK);
    }

//    @PostMapping("/ban-user/{userId}")
//    public ResponseEntity<String> banUser(
//            @PathVariable int userId,
//            @RequestBody String bannedReason) {
//
//        Optional<userSite> user = adminCancelOrderService.findUserById(userId);
//
//        if (user.isPresent()) {
//            adminCancelOrderService.banUser(userId, bannedReason);
//            return new ResponseEntity<>("User banned successfully with reason: " + bannedReason, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/ban-user/{userId}")
    public ResponseEntity<String> banUser(
            @PathVariable int userId,
            @RequestBody String bannedReason) {

        if (adminCancelOrderService.isUserBanned(userId)) {
            userSite user = adminCancelOrderService.findUserById(userId).get();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User has already been banned. Reason: " + user.getBannedReason());
        }

        adminCancelOrderService.banUser(userId, bannedReason);
        return ResponseEntity.status(HttpStatus.OK)
                .body("User banned successfully with reason: " + bannedReason);
    }

}
