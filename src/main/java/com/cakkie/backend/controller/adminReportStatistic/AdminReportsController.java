package com.cakkie.backend.controller.adminReportStatistic;

import com.cakkie.backend.dto.adminReports.CCOPReportDTO;
import com.cakkie.backend.dto.adminReports.ProductReviewReportDTO;
import com.cakkie.backend.dto.adminReports.SOReportDTO;
import com.cakkie.backend.dto.adminReports.SalesReportDTO;
import com.cakkie.backend.service.adminReportsStatistic.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminReportsController {
    private final ReportsService reportsService;

    //Customer Order
    @GetMapping("/reports/customer-orders")
    public List<CCOPReportDTO> getCustomerOrdersReports() {
        return reportsService.getCustomerOrderReport();
    }

    //Cancel Order
    @GetMapping("/reports/cancel-orders")
    public List<CCOPReportDTO> getCancelOrdersReports() {
        return reportsService.getCancelOrderReport();
    }

    //Order
    @GetMapping("/reports/orders")
    public List<CCOPReportDTO> getOrdersReports() {
        return reportsService.getOrderReport();
    }

    //Product Sales
    @GetMapping("/reports/product-sales")
    public List<CCOPReportDTO> getProductSalesReports() {
        return reportsService.getProductSalesReport();
    }

    //Shipping method
    @GetMapping("/reports/shipping-method")
    public List<SOReportDTO> getShippingMethodReports() {
        return reportsService.getShippingMethodReport();
    }

    //Coupons
    @GetMapping("/reports/coupons")
    public List<SOReportDTO> getCouponsReports() {
        return reportsService.getCouponsReport();
    }

    //Product Review
    @GetMapping("/reports/product-review")
    public List<ProductReviewReportDTO> getProductReviewReports() {
        return reportsService.getProductReviewReport();
    }

    //Sales
    @GetMapping("/reports/sales")
    public List<SalesReportDTO> getSalesReports() {
        return reportsService.getSalesReport();
    }

    //FILTER
    //Customer Order Report
//    @GetMapping("/reports/customer-orders/filter")
//    public List<CCOPReportDTO> getCustomerOrdersReportFilter(@RequestBody ReportFilter reportFilter) {
//        return reportsService.getCustomerOrderReportFilter(reportFilter.getStartDate(), reportFilter.getEndDate());
//    }
    //Cancel Order Report
    //Order Report
    //Product Sales Report
    //Product review Reports
    //Sales Reports


    //Shipping Method Reports
    @PostMapping("/reports/shipping-method/filter")
    public List<SOReportDTO> getShippingMethodReportFilter(@RequestParam String startDate, @RequestParam String endDate) {
        return reportsService.getShippingMethodReportFilter(startDate, endDate);
    }

    //Coupons Reports
    @PostMapping("/reports/coupons/filter")
    public List<SOReportDTO> getCouponsReportFilter(@RequestParam String startDate, @RequestParam String endDate) {
        return reportsService.getCouponsReportFilter(startDate, endDate);
    }
}
