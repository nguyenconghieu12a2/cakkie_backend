package com.cakkie.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cakkie.backend.service.AdminCustomerService;
import com.cakkie.backend.dto.CustomerDTO;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

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
    public ResponseEntity<CustomerDTO> getBannedCustomerById(@PathVariable int id) {
        CustomerDTO customer = adminCustomerService.getBannedCustomerById(id);
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

    @PutMapping("/unlock-customer/{id}")
    public void unlockCustomer(@PathVariable int id) {
        adminCustomerService.recoverCustomer(id);
    }
}
