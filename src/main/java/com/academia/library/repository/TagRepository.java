package com.academia.library.repository;

import com.academia.library.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    boolean existsByName(String name);
}
