package com.bridgelabz.bookstoreapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartDTO {
	private int user_id;
	private int book_id;
	private int quantity;
}
