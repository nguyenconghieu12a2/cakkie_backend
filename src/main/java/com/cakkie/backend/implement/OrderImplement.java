package com.cakkie.backend.implement;

import com.cakkie.backend.dto.OrderDTO;
import com.cakkie.backend.model.*;
import com.cakkie.backend.repository.*;
import com.cakkie.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderImplement implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private orderLineRepository orderLineRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserPaymentMethodRepository userPaymentMethodRepository;

    @Autowired
    private ShippingMethodRepository shippingMethodRepository;
    @Autowired
    private ProductItemRepository productItemRepository;

    public shopOrder createOrder(OrderDTO orderDTO) {
        orderStatus status = orderStatusRepository.findById(2)
                .orElseThrow(() -> new RuntimeException("Order Status not found with ID: " + 2));
        userSite user = userRepository.findById(orderDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + orderDTO.getUserId()));
        address address = addressRepository.findById(orderDTO.getShippingAddress())
                .orElseThrow(() -> new RuntimeException("Address not found with ID: " + orderDTO.getShippingAddress()));
        userPaymentMethod userPaymentMethod = userPaymentMethodRepository.findById(orderDTO.getPaymentMethodId())
                .orElseThrow(() -> new RuntimeException("User payment method not found with ID: " + orderDTO.getPaymentMethodId()));
        shippingMethod shippingMethod = shippingMethodRepository.findById(orderDTO.getShippingMethodId())
                .orElseThrow(() -> new RuntimeException("Shipping Method not found with ID: " + orderDTO.getShippingMethodId()));
        shopOrder order = new shopOrder();
        order.setUserId(user);

        order.setShippingMethodId(shippingMethod);
        order.setShippingAddressId(address);
        order.setPaymentMethod(userPaymentMethod);
        order.setOrderStatusId(status);
        order.setOrderTotal(orderDTO.getOrderTotal());
        if (orderDTO.getCouponsId() != 0) {
            coupons coupon = couponRepository.findById(orderDTO.getCouponsId())
                    .orElseThrow(() -> new RuntimeException("Coupon not found with ID: " + orderDTO.getCouponsId()));

            if (coupon.getQuantity() > 0) {
                order.setCoupons(coupon);
                coupon.setQuantity(coupon.getQuantity() - 1);
                couponRepository.save(coupon);
            } else {
                throw new RuntimeException("Coupon is no longer available.");
            }
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.UTC));
        order.setOrderDate(date);
        order = orderRepository.save(order);

        shopOrder finalOrder = order;
        List<orderLine> orderLines = orderDTO.getOrderLineList().stream()
                .map(productId -> {
                    orderLine orderLine = new orderLine();
                    orderLine.setOrderId(finalOrder);
                    orderLine.setProductItemId(productItemRepository.findById(productId.getProductItemId()).orElseThrow(() -> new RuntimeException("Product item not found with ID: " + productId.getProductItemId())));
                    orderLine.setPrice(productId.getPrice());

                    productItem productItem = productItemRepository.findById(productId.getProductItemId())
                            .orElseThrow(() -> new RuntimeException("Product item not found with ID: " + productId.getProductItemId()));
                    if (productItem.getQtyInStock() < productId.getQuantity()) {
                        throw new RuntimeException("Not enough stock for product item ID: " + productId.getProductItemId());
                    }

                    productItem.setQtyInStock(productItem.getQtyInStock() - productId.getQuantity());
                    productItemRepository.save(productItem);
                    orderLine.setQty(productId.getQuantity());
                    orderLine.setDiscountPrice(productId.getDiscountPrice());
                    orderLine.setNote(productId.getNote());
                    return orderLine;
                })
                .collect(Collectors.toList());
        orderLineRepository.saveAll(orderLines);

        return order;
    }
}
