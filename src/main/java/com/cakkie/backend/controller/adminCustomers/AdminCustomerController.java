package com.cakkie.backend.controller.adminCustomers;

import com.cakkie.backend.dto.adminCustomer.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cakkie.backend.service.adminCustomers.AdminCustomerService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final AdminCustomerService adminCustomerService;

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = adminCustomerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/banned-customer")
    public ResponseEntity<List<CustomerDTO>> getAllBannedCustomers() {
        List<CustomerDTO> customers = adminCustomerService.getAllBannedCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/deleted-customer")
    public ResponseEntity<List<CustomerDTO>> getAllDeletedCustomers() {
        List<CustomerDTO> customers = adminCustomerService.getAllDeletedCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/get-customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        CustomerDTO customer = adminCustomerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/get-banned-customer/{id}")
    public ResponseEntity<CustomerBannedDTO> getBannedCustomerById(@PathVariable int id) {
        CustomerBannedDTO customer = adminCustomerService.getBannedCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/edit-customer/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDT){
        adminCustomerService.updateCustomerInfo(id, customerDT);
        return ResponseEntity.ok("Customer updated successfully");
    }

    @PutMapping("/delete-customer/{id}")
    public void deleteCustomer(@PathVariable int id) {
        adminCustomerService.deleteCustomer(id);
    }

    @PutMapping("/recover-customer/{id}")
    public void recoverCustomer(@PathVariable int id) {
        adminCustomerService.recoverCustomer(id);
    }

    @PutMapping("/bann-customer/{id}")
    public void bannedCustomer(@PathVariable int id, @RequestBody BannedCustomerReasonDTO bannedReason) {
        adminCustomerService.bannedCustomer(id, bannedReason);
    }

    @PutMapping("/unlock-customer/{id}")
    public void unlockCustomer(@PathVariable int id) {
        adminCustomerService.recoverCustomer(id);
    }

    @GetMapping("/customer-statistic-order/{id}")
    public List<Map<String, Object>> getCustomerStatisticOrder(@PathVariable int id) {
        return adminCustomerService.getCustomerStatisticOrder(id);
    }

    @GetMapping("/customer-statistic-order/processing/{id}")
    public List<CustomerProcessingDTO> getProcessingOrder(@PathVariable int id) {
        return adminCustomerService.getCustomerProcessingOrder(id);
    }

    @GetMapping("/customer-statistic-order/complete/{id}")
    public List<CustomerCompleteDTO> getCompleteOrder(@PathVariable int id) {
        return adminCustomerService.getCustomerCompleteOrder(id);
    }

    @GetMapping("/customer-statistic-order/cancel/{id}")
    public List<CustomerCancelDTO> getCancelOrder(@PathVariable int id) {
        return adminCustomerService.getCustomerCancelOrder(id);
    }
}
