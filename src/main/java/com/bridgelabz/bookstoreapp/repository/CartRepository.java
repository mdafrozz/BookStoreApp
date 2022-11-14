package com.bridgelabz.bookstoreapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bridgelabz.bookstoreapp.model.CartModel;

public interface CartRepository extends JpaRepository<CartModel, Integer>  {

}
