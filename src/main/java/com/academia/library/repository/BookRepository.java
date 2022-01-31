package com.academia.library.repository;

import com.academia.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByTitleAndAuthorId(String title, Long authorId);

    boolean existsByTitleAndAuthorIdAndIdNot(String title, Long authorId, Long id);
}
