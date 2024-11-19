package com.cakkie.backend.service.adminReportsStatistic;

import com.cakkie.backend.dto.adminReports.CCOPReportDTO;
import com.cakkie.backend.dto.adminReports.ProductReviewReportDTO;
import com.cakkie.backend.dto.adminReports.SOReportDTO;
import com.cakkie.backend.dto.adminReports.SalesReportDTO;
import com.cakkie.backend.repository.adminReportsStatistic.ReportRepo;
import com.cakkie.backend.repository.adminReportsStatistic.ReportReviewRepo;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ReportsService {

    private final ReportRepo reportRepo;
    private final ReportReviewRepo reportReviewRepo;

    public ReportsService(ReportRepo reportRepo, ReportReviewRepo reportReviewRepo) {
        this.reportRepo = reportRepo;
        this.reportReviewRepo = reportReviewRepo;
    }


    //Customer Order Report
    public List<CCOPReportDTO> getCustomerOrderReport() {
        List<Object[]> resultSet = reportRepo.getCustomerOrders();
        return mapToCustomerOrderReportDTO(resultSet);
    }

    private List<CCOPReportDTO> mapToCustomerOrderReportDTO(List<Object[]> resultSet) {
        List<CCOPReportDTO> customerOrderReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String startDate = (String) row[0];
            String endDate = (String) row[1];
            int customers = ((Number) row[2]).intValue();
            int orders = ((Number) row[3]).intValue();
            Long total = ((Number) row[4]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            CCOPReportDTO customerOrderReportDTO = new CCOPReportDTO(startDate, endDate, customers, orders, formattedTotalPayment);
            customerOrderReportDTOList.add(customerOrderReportDTO);
        }
        return customerOrderReportDTOList;
    }


    //Cancel Order Report
    public List<CCOPReportDTO> getCancelOrderReport() {
        List<Object[]> resultSet = reportRepo.getCancelOrder();
        return mapToCancelOrderReportDTO(resultSet);
    }

    private List<CCOPReportDTO> mapToCancelOrderReportDTO(List<Object[]> resultSet) {
        List<CCOPReportDTO> cancelOrderReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String startDate = (String) row[0];
            String endDate = (String) row[1];
            int orders = ((Number) row[2]).intValue();
            int products = ((Number) row[3]).intValue();
            Long total = ((Number) row[4]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            CCOPReportDTO cancelOrderReportDTO = new CCOPReportDTO(startDate, endDate, orders, products, formattedTotalPayment);
            cancelOrderReportDTOList.add(cancelOrderReportDTO);
        }
        return cancelOrderReportDTOList;
    }

    //Order Report
    public List<CCOPReportDTO> getOrderReport() {
        List<Object[]> resultSet = reportRepo.getOrders();
        return mapToOrderReportDTO(resultSet);
    }

    private List<CCOPReportDTO> mapToOrderReportDTO(List<Object[]> resultSet) {
        List<CCOPReportDTO> orderReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String startDate = (String) row[0];
            String endDate = (String) row[1];
            int orders = ((Number) row[2]).intValue();
            int products = ((Number) row[3]).intValue();
            Long total = ((Number) row[4]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            CCOPReportDTO orderReportDTO = new CCOPReportDTO(startDate, endDate, orders, products, formattedTotalPayment);
            orderReportDTOList.add(orderReportDTO);
        }
        return orderReportDTOList;

    }

    //Product Sales Report
    public List<CCOPReportDTO> getProductSalesReport() {
        List<Object[]> resultSet = reportRepo.getProductSales();
        return mapToProductSalesReportDTO(resultSet);
    }

    private List<CCOPReportDTO> mapToProductSalesReportDTO(List<Object[]> resultSet) {
        List<CCOPReportDTO> productSalesReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String startDate = (String) row[0];
            String endDate = (String) row[1];
            int products = ((Number) row[2]).intValue();
            int quantity = ((Number) row[3]).intValue();
            Long total = ((Number) row[4]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            CCOPReportDTO productSalesReportDTO = new CCOPReportDTO(startDate, endDate, products, quantity, formattedTotalPayment);
            productSalesReportDTOList.add(productSalesReportDTO);
        }
        return productSalesReportDTOList;
    }

    //Shipping Method Reports
    public List<SOReportDTO> getShippingMethodReport() {
        List<Object[]> resultSet = reportRepo.getShipping();
        return mapToShippingMethodReportDTO(resultSet);
    }

    private List<SOReportDTO> mapToShippingMethodReportDTO(List<Object[]> resultSet) {

        List<SOReportDTO> shippingMethodReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String shippingMethod = (String) row[0];
            int orders = ((Number) row[1]).intValue();
            Long total = ((Number) row[2]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            SOReportDTO shippingMethodReportDTO = new SOReportDTO(shippingMethod, orders, formattedTotalPayment);
            shippingMethodReportDTOList.add(shippingMethodReportDTO);
        }
        return shippingMethodReportDTOList;
    }

    //Coupons Reports
    public List<SOReportDTO> getCouponsReport() {
        List<Object[]> resultSet = reportRepo.getCoupons();
        return mapToCouponsReportDTO(resultSet);
    }

    private List<SOReportDTO> mapToCouponsReportDTO(List<Object[]> resultSet) {

        List<SOReportDTO> couponsReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String coupons = (String) row[0];
            int orders = ((Number) row[1]).intValue();
            Long total = ((Number) row[2]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            SOReportDTO couponsReportDTO = new SOReportDTO(coupons, orders, formattedTotalPayment);
            couponsReportDTOList.add(couponsReportDTO);
        }
        return couponsReportDTOList;
    }

    //Product review Reports
    public List<ProductReviewReportDTO> getProductReviewReport() {
        List<Object[]> resultSet = reportReviewRepo.getCustomerReview();
        return mapToProductReviewReportDTO(resultSet);
    }

    private List<ProductReviewReportDTO> mapToProductReviewReportDTO(List<Object[]> resultSet) {
        List<ProductReviewReportDTO> productReviewReportDTOList = new ArrayList<>();
        for (Object[] row : resultSet) {
            String startDate = (String) row[0];
            String endDate = (String) row[1];
            int pendRv = ((Number) row[2]).intValue();
            int appRv = ((Number) row[3]).intValue();
            int rjRv = ((Number) row[4]).intValue();
            int totalRv = ((Number) row[5]).intValue();

            ProductReviewReportDTO productReviewReportDTO = new ProductReviewReportDTO(startDate, endDate, pendRv, appRv, rjRv, totalRv);
            productReviewReportDTOList.add(productReviewReportDTO);
        }
        return productReviewReportDTOList;
    }

    //Sales Reports
    public List<SalesReportDTO> getSalesReport() {
        List<Object[]> resultSet = reportRepo.getSales();
        return mapToSalesReportDTO(resultSet);
    }

    private List<SalesReportDTO> mapToSalesReportDTO(List<Object[]> resultSet) {

        List<SalesReportDTO> salesReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String dateStart = (String) row[0];
            String dateEnd = (String) row[1];
            Long total = ((Number) row[2]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            SalesReportDTO salesReportDTO = new SalesReportDTO(dateStart, dateEnd, formattedTotalPayment);
            salesReportDTOList.add(salesReportDTO);
        }
        return salesReportDTOList;
    }

    //FILTER
    //Customer Order Report
    public List<CCOPReportDTO> getCustomerOrderReportFilter(String startDate, String endDate) {
        List<Object[]> resultSet = reportRepo.getCustomerOrdersFilter(startDate, endDate);
        return mapToCustomerOrderReportDTOFilter(resultSet);
    }

    private List<CCOPReportDTO> mapToCustomerOrderReportDTOFilter(List<Object[]> resultSet) {
        List<CCOPReportDTO> customerOrderReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String startDate = (String) row[0];
            String endDate = (String) row[1];
            int customers = ((Number) row[2]).intValue();
            int orders = ((Number) row[3]).intValue();
            Long total = ((Number) row[4]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            CCOPReportDTO customerOrderReportDTO = new CCOPReportDTO(startDate, endDate, customers, orders, formattedTotalPayment);
            customerOrderReportDTOList.add(customerOrderReportDTO);
        }
        return customerOrderReportDTOList;
    }

    //Cancel Order Report
    //Order Report
    //Product Sales Report
    //Product review Reports
    //Sales Reports


    // Shipping Method Reports implementation
    public List<SOReportDTO> getShippingMethodReportFilter(String startDate, String endDate) {
        List<Object[]> resultSet = reportRepo.getShippingFilter(startDate, endDate);
        return mapToShippingMethodReportDTOFilter(resultSet);
    }

    private List<SOReportDTO> mapToShippingMethodReportDTOFilter(List<Object[]> resultSet) {
        List<SOReportDTO> shippingMethodReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String shippingMethod = (String) row[0];
            int orders = ((Number) row[1]).intValue();
            Long total = ((Number) row[2]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            SOReportDTO shippingMethodReportDTO = new SOReportDTO(shippingMethod, orders, formattedTotalPayment);
            shippingMethodReportDTOList.add(shippingMethodReportDTO);
        }
        return shippingMethodReportDTOList;
    }

    //Coupons Reports
    public List<SOReportDTO> getCouponsReportFilter(String startDate, String endDate) {
        List<Object[]> resultSet = reportRepo.getCouponsFilter(startDate, endDate);
        return mapToCouponsReportDTOFilter(resultSet);
    }

    private List<SOReportDTO> mapToCouponsReportDTOFilter(List<Object[]> resultSet) {

        List<SOReportDTO> couponsReportDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            String coupons = (String) row[0];
            int orders = ((Number) row[1]).intValue();
            Long total = ((Number) row[2]).longValue();
            String formattedTotalPayment = currencyFormat.format(total) + " VND";

            SOReportDTO couponsReportDTO = new SOReportDTO(coupons, orders, formattedTotalPayment);
            couponsReportDTOList.add(couponsReportDTO);
        }
        return couponsReportDTOList;
    }
}
