package com.example.pinspired.repositories;

import com.example.pinspired.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//import java.util.List;
//import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
//    List<PostEntity> findAllByDeleted(boolean deleted);
//      List<Matches> findAll()
//    //    PostEntity findByIdAndDeleted(long id, boolean deleted);
//    Optional<PostEntity> findByIdAndDeleted(long id, boolean deleted);
}
