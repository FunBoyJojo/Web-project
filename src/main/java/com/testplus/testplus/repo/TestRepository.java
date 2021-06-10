package com.testplus.testplus.repo;

import com.testplus.testplus.models.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepository extends CrudRepository<Test, Integer> {
    public Test findByTitle(String title);
}
