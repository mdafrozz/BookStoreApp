package com.bridgelabz.bookstoreapp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {

	String bookName;
	String authorName;
	String bookDescription;
	String bookImage;
	int price;
	int quantity;
}
