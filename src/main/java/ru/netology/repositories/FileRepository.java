package ru.netology.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.netology.entity.FileEntity;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    Optional<FileEntity> findByFileNameEquals(String fileName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update FileEntity file  set file.fileName = :filename  where file.id = :id")
    void updateFile(@Param("filename") String filename, @Param("id") Long id);

}
