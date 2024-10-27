package com.cakkie.backend.repository;

import com.cakkie.backend.dto.AddressDTO;
import com.cakkie.backend.dto.CouponDTO;
import com.cakkie.backend.dto.productCartDTO;
import com.cakkie.backend.model.*;
import com.cakkie.backend.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
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


    @Query(value = "SELECT  p.id, p.name, p.description, c.cate_name, pi.price, p.product_image, COALESCE(p.product_rating, 0), pi.size, pi.qty_in_stock " +
            "            FROM product p " +
            "            JOIN category c ON p.categoryID = c.id " +
            "            JOIN product_item pi ON p.id = pi.pro_id " +
            "            WHERE p.id = :id", nativeQuery = true)
    List<Object[]> getProductById(@Param("id") int id);

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
            "pci.id, ui.id, pci.cartId, pci.productItemId, pci.qty, pci.note)" +
            "FROM shoppingCartItem pci " +
            "JOIN pci.cartId pc " +
            "JOIN pc.userId ui " +
            "WHERE ui.id = :userId")
    productCartDTO getProductCart(@Param("userId") int userId);

    @Query(value = "SELECT new com.cakkie.backend.dto.ProductDTO(" +
            "p.id, pi.id, p.name, p.description,  pc.cateName, pi.price, pi.productImage, p.productRating, pi.size, pi.qtyInStock, pi.isDeleted)" +
            "FROM product p " +
            "JOIN p.productItemList pi " +
            "JOIN p.categoryID pc " +
            "WHERE pi.isDeleted = 1 AND pi.id = :proItemId")
    ProductDTO getProductItemById(@Param("proItemId") int proItemId);

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
}
