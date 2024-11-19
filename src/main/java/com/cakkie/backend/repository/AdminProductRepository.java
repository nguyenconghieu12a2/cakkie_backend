    package com.cakkie.backend.repository;

    import com.cakkie.backend.model.category;
    import com.cakkie.backend.model.product;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    import java.util.List;

    public interface AdminProductRepository extends JpaRepository<product, Integer> {
        @Query(value = "SELECT p.id, p.name, c.id, c.cate_name, p.description, p.product_image, p.product_rating, [pi].id, [pi].size,[pi].qty_in_stock, [pi].price, [pi].is_deleted\n" +
                "from product_item [pi]\n" +
                "join product p on p.id = [pi].pro_id\n" +
                "join category c on c.id = p.categoryID\n" +
                "full join discount_category dc on dc.category_id = c.id\n" +
                "full join discount d on d.id = dc.discount_id\n" +
                "where p.id is not null AND p.is_deleted = 1 AND [pi].is_deleted = 1\n" +
                "order by p.id, c.id, [pi].id", nativeQuery = true)
        List<Object[]> getAllProducts();

        @Query(value = "select p.id, p.description, pdt.des_title_id ,pdt.des_title_name, pdi.des_info\n" +
                "from product_item [pi]\n" +
                "join product p on p.id = [pi].pro_id\n" +
                "join product_des_info pdi on pdi.product_id = p.id\n" +
                "join product_des_title pdt on pdt.des_title_id = pdi.des_title_id\n" +
                "where p.id = ?1 AND p.is_deleted = 1\n" +
                "order by p.id, [pi].id", nativeQuery = true)
        List<Object[]> getProductsById(int id);

        @Query(value = "SELECT p.size \n" +
                "FROM product_item p \n" +
                "GROUP BY p.size", nativeQuery = true)
        List<String> getProductsSize();

        List<product> findByCategoryID(category categoryID);

        @Query(value = """
        SELECT
        p.id, p.name, c.id, c.cate_name, p.description, p.product_image,
        p.product_rating, pi.id, pi.size, pi.qty_in_stock, pi.price
        FROM
            product_item pi
                JOIN product p ON p.id = pi.pro_id
                JOIN category c ON c.id = p.categoryID
        WHERE p.id IS NOT NULL AND p.is_deleted = 0 AND c.is_deleted = 0
        ORDER BY p.id, c.id, pi.id
    """, nativeQuery = true)
        List<Object[]> getAllDeletedProducts();

        @Query(value = """
        SELECT p.id, p.name, c.id, c.cate_name, p.description, p.product_image, p.product_rating, pi.id, pi.size, pi.qty_in_stock, pi.price
        FROM product_item pi
        JOIN product p ON p.id = pi.pro_id
        JOIN category c ON c.id = p.categoryID
        WHERE p.id IS NOT NULL AND p.is_deleted = 0 AND c.is_deleted = 1
        ORDER BY p.id, c.id, pi.id
        """, nativeQuery = true)
        List<Object[]> getDeletedCategoryProducts();
    }
