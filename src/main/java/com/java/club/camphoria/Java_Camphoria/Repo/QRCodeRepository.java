package com.java.club.camphoria.Java_Camphoria.Repo;


import com.java.club.camphoria.Java_Camphoria.Model.QRCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface QRCodeRepository extends JpaRepository<QRCodeEntity, Long> {
    Optional<QRCodeEntity> findByQrContent(String qrContent);
}
