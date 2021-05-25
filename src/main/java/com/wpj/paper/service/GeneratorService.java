package com.wpj.paper.service;

import com.wpj.paper.dao.entity.Product;

import java.util.ArrayList;

public interface GeneratorService {
    void saveProducts(ArrayList<Product> products);
}
