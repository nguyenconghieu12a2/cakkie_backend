package com.cakkie.backend.repository;

import com.cakkie.backend.dto.*;
import com.cakkie.backend.model.*;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.swing.text.AbstractDocument;
import java.util.List;

public interface ProductRepository  extends JpaRepository<product, Long>{
    @Query(value = "SELECT * FROM product_item", nativeQuery = true)
    List<productItem> getAllProductItems();

    @Query(value = "SELECT  p.id, p.name, p.description, c.cate_name, pi.price, p.product_image, p.product_rating, pi.size, pi.qty_in_stock " +
            "            FROM product p " +
            "            JOIN category c ON p.categoryID = c.id " +
            "            JOIN product_item pi ON p.id = pi.pro_id ", nativeQuery = true)
    List<Object[]> getAllProducts();

    @Query(value = "SELECT new com.cakkie.backend.dto.ProductDTO(" +
            "p.id , p.name, p.description, pi.price)" +
            "FROM product p " +
            "JOIN p.productItemList pi ")
    List<ProductDTO> getAllProduct();


    @Query(value = "SELECT  p.id, p.name, p.description, c.cate_name, pi.price, p.product_image, COALESCE(p.product_rating, 0), pi.size, pi.qty_in_stock, d.discount_rate" +
            "            FROM product p " +
            "            JOIN category c ON p.categoryID = c.id " +
            "            JOIN product_item pi ON p.id = pi.pro_id " +
            "            JOIN discount_category dc ON c.id = dc.category_id " +
            "            JOIN discount d ON dc.discount_id = d.id " +
            "            WHERE p.id = :id", nativeQuery = true)
    List<Object[]> getProductById(@Param("id") int id);

//    @Query(value = "SELECT new com.cakkie.backend.dto.ProductDTO(" +
//            "p.id , pi.id, p.name, p.description, pc.cateName, pi.price, p.productImage, COALESCE(p.productRating, 0), pi.size, pi.qtyInStock, dc.discountRate)" +
//            "FROM product p " +
//            "JOIN p.productItemList pi " +
//            "JOIN p.categoryID pc " +
//            "JOIN pc.discountCategoryList dcl " +
//            "JOIN dcl.discountId dc " +
//            "WHERE pi.isDeleted = 1 AND pi.id = :productId")
//    List<ProductDTO> getProductsById(@Param("id") int id);

    @Query(value = "SELECT p.id, pdt.desTitleID, pdi.desInfo, pdt.desTitleName " +
            "FROM product p " +
            "JOIN productDesInfo pdi ON p.id = pdi.proID " +
            "JOIN productDesTitle pdt ON pdi.desTitleID = pdt.desTitleID " +
            "WHERE p.id = :id", nativeQuery = true)
    List<Object[]> getProductDescriptionById(@Param("id") int id);

    @Query(value = "SELECT new com.cakkie.backend.dto.CouponDTO( " +
            "co.id, co.code, co.name, co.quantity ,co.priceDiscount, co.startDate, co.endDate) " +
            "FROM coupons co " +
            "WHERE co.id = :id")
    CouponDTO getCouponById(@Param("id") int id);

    @Query (value = "SELECT new com.cakkie.backend.dto.productCartDTO(" +
            "pci.id, ui.id, pc.id, pi.id, pci.qty, pci.note)" +
            "FROM shoppingCartItem pci " +
            "JOIN pci.productItemId pi " +
            "JOIN pci.cartId pc " +
            "JOIN pc.userId ui " +
            "WHERE ui.id = :userId")
    List<productCartDTO> getProductCart(@Param("userId") int userId);


    @Query(value = "SELECT new com.cakkie.backend.dto.ProductDTO(" +
            "p.id , pi.id, p.name, p.description, pc.cateName, pi.price, p.productImage, COALESCE(p.productRating, 0), pi.size, pi.qtyInStock, d.discountRate)" +
            "FROM product p " +
            "JOIN p.productItemList pi " +
            "JOIN p.categoryID pc " +
            "JOIN pc.discountCategoryList dcl " +
            "JOIN dcl.discountId d " +
            "WHERE pi.isDeleted = 1 AND pi.id = :productId")
    List<ProductDTO> getProductItemById(@Param("productId") int productId);

    // Query to get product items that are not deleted
    @Query(value = "SELECT p FROM productItem p WHERE p.isDeleted = 0")
    List<productItem> getAvailableProductItems();

    @Query(value = "SELECT new com.cakkie.backend.dto.AddressDTO(" +
            "us.id, a.recievedName, us.phone, a.detailAddress, wc.name, dis.name, pc.name, ua.isDefault, ua.isDeleted )" +
            "FROM userAddress ua " +
            "JOIN ua.userId us " +
            "JOIN ua.addressId a " +
            "JOIN a.districtsCode dis " +
            "JOIN dis.provinceCode pc " +
            "JOIN a.wardsCode wc " +
            "WHERE us.id = :userId AND ua.isDeleted = 1")
    List<AddressDTO> getAddressById(@Param("userId") int userId);
    // Query to get product items with a specific price range
    @Query(value = "SELECT p FROM productItem p WHERE p.price BETWEEN :minPrice AND :maxPrice")
    List<productItem> getProductItemsByPriceRange(@Param("minPrice") long minPrice, @Param("maxPrice") long maxPrice);

    @Query(value = "SELECT new com.cakkie.backend.dto.OrderDTO(" +
            "so.id, us.id, sm.name, adr.id, pmt.name, os.status, so.orderDate, so.arrivedDate, so.canceledDate)" +
            "FROM shopOrder so " +
            "JOIN so.shippingAddressId adr " +
            "JOIN so.userId us " +
            "JOIN so.shippingMethodId sm " +
            "JOIN so.orderStatusId os " +
            "JOIN so.paymentMethod pm " +
            "JOIN pm.paymentTypeId pmt " +
            "WHERE us.id = :id")
    List<OrderDTO> getOrdersByUserId(@Param("id") int id);

    @Query(value = "SELECT new com.cakkie.backend.dto.OrderItemDTO(" +
            "ol.id, pi.id, so.id, ol.qty, ol.price, ol.discountPrice, ol.note)" +
            "FROM orderLine ol " +
            "JOIN ol.productItemId pi " +
            "JOIN ol.orderId so " +
            "WHERE so.id = :orderId")
    List<OrderItemDTO> getOrderItemsByOrderId(int orderId);
}
