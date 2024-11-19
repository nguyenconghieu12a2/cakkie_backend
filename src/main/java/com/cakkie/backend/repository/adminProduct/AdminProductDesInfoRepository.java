package com.cakkie.backend.repository.adminProduct;

import com.cakkie.backend.model.productDesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface AdminProductDesInfoRepository extends JpaRepository<productDesInfo, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO product_des_info(product_id, des_title_id, des_info, is_deleted) VALUES (:productId, :desTitleId, :desInfo, :isDeleted)", nativeQuery = true)
    void insertProductDesInfo(@Param("productId") int productId,
                              @Param("desTitleId") int desTitleId,
                              @Param("desInfo") String desInfo,
                              @Param("isDeleted") int isDeleted);

    @Modifying
    @Transactional
    @Query("UPDATE productDesInfo pdi SET pdi.desInfo = :desInfo WHERE pdi.proID.id = :productId AND pdi.desTitleId.desTitleID = :desTitleId")
    void updateProductDesInfo(@Param("productId") int productId,
                              @Param("desTitleId") int desTitleId,
                              @Param("desInfo") String desInfo);

    @Modifying
    @Transactional
    @Query("DELETE productDesInfo pdi WHERE pdi.desTitleId.desTitleID = :desTitleId AND pdi.proID.id = :productId")
    void deleteDesInfo(@Param("productId") int productId,
                       @Param("desTitleId") int desTitleId);
}
