package com.cakkie.backend.repository;

import com.cakkie.backend.model.shopOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StatisticRepo extends JpaRepository<shopOrder, Integer> {
    @Query(value = "select sum(order_total) - (select sum(price/2)*3 as [cost] from product_item) as [order_sales] from shop_order", nativeQuery = true)
    long getOrderSaleValue();

    @Query(value = "select count(*) as [order_processing] from shop_order where canceled_date is null and arrived_date is null", nativeQuery = true)
    int getOrderProcessingValue();

    @Query(value = "select count(*) as [order_cancel] from shop_order where canceled_date is not null", nativeQuery = true)
    int getOrderCancelValue();

    @Query(value = "select count(*) as [order_complete] from shop_order where canceled_date is null and arrived_date is not null", nativeQuery = true)
    int getOrdersCompleteValue();

    @Query(value = "select count(*) as [out_of_stock] from product_item where qty_in_stock = 0", nativeQuery = true)
    int getOutofstockproductsValue();

    @Query(value = "select count(*) as [pending_review] from user_review where status_id = 2", nativeQuery = true)
    int getPendingReviewsValue();
}
