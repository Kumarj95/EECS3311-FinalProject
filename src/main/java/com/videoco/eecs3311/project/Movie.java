package com.videoco.eecs3311.project;

import java.util.UUID;

public class Movie {
	private String title;
	private int stock;
	private UUID id;
	private double price;
	private MovieInfo movieInfo;
	public Movie() {
		
	}
	public Movie(String title, int stock, UUID id, double price, MovieInfo movieInfo) {
		super();
		this.title = title;
		this.stock = stock;
		this.id = id;
		this.price = price;
		this.movieInfo = movieInfo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public UUID getId() {
		return id;
	}
	public void setID(UUID id) {
		this.id=id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public MovieInfo getMovieInfo() {
		return movieInfo.clone();
	}
	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo.clone();
	}
	
	public Movie clone() {
		return new Movie(title,stock,id,price,movieInfo.clone());
	}
	@Override
	public String toString() {
		return "Movie [title=" + title + ", stock=" + stock + ", id=" + id + ", price=" + price + ", movieInfo="
				+ movieInfo + "]";
	}
	

}
