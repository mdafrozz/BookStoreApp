package com.bridgelabz.bookstoreapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {
	
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{2,}$", message="Invalid Book Name(First Letter Should be in Upper Case and min 3 Characters.)")
	private String bookName;
	@Pattern(regexp = "^[A-Z]{1}[a-zA-Z\\s]{1,}$", message="Invalid Author Name(First Letter Should be in Upper Case)")
	private String authorName;
	@NotNull
	private String bookDescription;
	@NotEmpty
	private String bookImage;
	@NotNull
	private int price;
	@NotNull
	private int quantity;
}
