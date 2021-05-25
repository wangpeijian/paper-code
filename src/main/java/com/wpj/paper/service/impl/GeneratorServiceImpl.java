package com.wpj.paper.service.impl;

import com.wpj.paper.dao.entity.Product;
import com.wpj.paper.dao.repo.ProductRepository;
import com.wpj.paper.service.GeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public void saveProducts(ArrayList<Product> products) {
        productRepository.saveAll(products);
    }
}
