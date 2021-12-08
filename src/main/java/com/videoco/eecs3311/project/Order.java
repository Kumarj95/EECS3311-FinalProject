package com.videoco.eecs3311.project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public abstract  class   Order {
	protected ArrayList<Movie> movies;
	private UUID orderID;
	public UUID getOrderID() {
		return orderID;
	}


	private double orderPrice=0;
	private OrderStatus orderStatus;
	private PaymentInfo paymentInfo;
	public ArrayList<Movie> getMovies() {
		return movies;
	}


	private String shippingAddress;
	private LocalDate orderDate;
	private LocalDate dateDelivered=LocalDate.MIN;
	public LocalDate getDateDelivered() {
		return dateDelivered;
	}

	public void setDateDelivered(LocalDate dateDelivered) {
		this.dateDelivered = dateDelivered;
	}

	public Order( UUID orderID) {
		this.orderID = orderID;
		movies= new ArrayList<Movie>();
		this.orderStatus=OrderStatus.Creating;
		orderDate= LocalDate.now();
	}
	
	public boolean checkValidOrder() {
		if(paymentInfo==null || paymentInfo.getBillingAddress().isBlank()|| paymentInfo.getCreditCardNumber().isBlank() || movies.size()<=0 || shippingAddress.isBlank()) {
			return false;
		}
                for(Movie movie:movies){
                    if(movie.getStock()<=0){
                        return false;
                    }
                }
		return true;
	}
	
//	private void updateMovieStock() {
//		SystemV s= SystemV.getInstance();
//		for(Movie movie:movies) {
//			
//		}
//	}
	
	public boolean addToOrder(UUID id) {
		SystemV s= SystemV.getInstance();
		Movie mov= s.getMovie(id);
//		if(mov!=null) {
//			movies.add(mov);
//		}
		boolean inOrder=false;
		if(mov!=null) {
			for(Movie movie: movies) {
				if(movie.getId().equals(id)) {
					inOrder=true;
                                        return false;
				}
			}
			if(!inOrder && mov.getStock()>0) {
				orderPrice=orderPrice+mov.getPrice();
				movies.add(mov);
				return true;
			}
		}
		return false;
	}
	public boolean addToOrder(Movie id) {
//		if(mov!=null) {
//			movies.add(mov);
//		}
		boolean inOrder=false;
		
		for(Movie movie: movies) {
			if(movie.getId().equals(id.getId())) {
				inOrder=true;
                                return false;
			}
		}
		if(!inOrder && id.getStock()>0) {
			orderPrice=orderPrice+id.getPrice();
			movies.add(id);
			return true;
		} 
		return false;
	}

	
	public boolean deleteFromOrder(UUID id) {
		int index=0;
		for(Movie movie:movies) {
			if(movie.getId().equals(id)) {
                                    orderPrice-=movie.getPrice();
                                    movies.remove(index);
                                                               return true;
			}
			index++;
		}
                return false;
	}
	
	
	
	public void setPaymentInfo(PaymentInfo info) {
		this.paymentInfo=info;
	}

	
	public void setShippingAddress(String address) {
		this.shippingAddress=address;
	}
	
	public String viewOrder() {
		return this.toString();
	}
	
	@Override 
	public String toString() {
		String s="";
		for(Movie movie: movies) {
			s+= movie.toString() +"\n";
		}
		return "order with id "+ orderID.toString() +" with price " + orderPrice+ " of movies: " +s ;
	}
	
	public void SetOrderStatus(OrderStatus status) {
		this.orderStatus=status;
	}
	public boolean validatePayment() {
		if(paymentInfo==null) {
			return false;
		}else {
			return paymentInfo.isValid();
		}
	}
	public abstract boolean placeOrder() ;
	
	
	public LocalDate getOrderDate() {
		if(orderDate!=null) {
			return orderDate;
		}else {
			return null;
		}
	}
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}


	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	

}
