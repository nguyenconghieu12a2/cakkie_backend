package com.cakkie.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cakkie.backend.service.DashboardTableService;
import java.util.List;
import java.util.Map;

import com.cakkie.backend.dto.TableOrderDTO;
import com.cakkie.backend.dto.TableCustomerDTO;
import com.cakkie.backend.dto.MinMaxYearDTO;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DashboardController {
    private final DashboardTableService dashboardTableService;

    @GetMapping("/table-order")
    public List<TableOrderDTO> getOrderTable(){
        return dashboardTableService.getOrderTable();
    }

    @GetMapping("/table-customer")
    public List<TableCustomerDTO> getCustomerTable(){
        return dashboardTableService.getCustomerTable();
    }

    @GetMapping("/cards")
    public List<Map<String, String>> getCardsTable(){
        return dashboardTableService.getCards();
    }

    @GetMapping("/chart-orders")
    public List<Integer> getChartOrders(){
        return dashboardTableService.getChartOrderData();
    }

    @GetMapping("/chart-customers")
    public List<Integer> getChartCustomer(){
        return dashboardTableService.getChartCustomerData();
    }

    @GetMapping("/min-max-year-orders")
    public ResponseEntity<MinMaxYearDTO> getMinMaxOrder(){
        MinMaxYearDTO minMaxYearDTO = dashboardTableService.getYearOrder();
        return ResponseEntity.ok(minMaxYearDTO);
    }

    @GetMapping("/min-max-year-customers")
    public ResponseEntity<MinMaxYearDTO> getMinMaxCustomer(){
        MinMaxYearDTO minMaxYearDTO = dashboardTableService.getYearCustomer();
        return ResponseEntity.ok(minMaxYearDTO);
    }

    @GetMapping("/chart-orders/{year}")
    public ResponseEntity<List<Integer>> getChartOrderByYear(@PathVariable Integer year){
        List<Integer>  chartOrder = dashboardTableService.getChartOrderByYear(year);
        return ResponseEntity.ok(chartOrder);
    }

    @GetMapping("/chart-customers/{year}")
    public ResponseEntity<List<Integer>> getChartCustomerByYear(@PathVariable Integer year){
        List<Integer>  chartCustomer = dashboardTableService.getChartCustomerByYear(year);
        return ResponseEntity.ok(chartCustomer);
    }
}
