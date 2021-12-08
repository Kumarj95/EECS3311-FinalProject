package com.videoco.eecs3311.project;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class PhoneUser {
	private PaymentInfo paymentInfo;
	private String shippingAddress;
	
	public PhoneUser(String CreditCard, String BillingAddress, String ShippingAddress) {
		this.shippingAddress=ShippingAddress;
		this.paymentInfo= new PaymentInfo(CreditCard,BillingAddress);
	}
	public PhoneUser(PaymentInfo info, String ShippingAddress) {
		this.shippingAddress=ShippingAddress;
		this.paymentInfo= info;
	}
	
	
	public UUID placePhoneOrder(ArrayList<String> MovieNames) {
		SystemV sys= SystemV.getInstance();
		
		OperatorUser operator=sys.getOperatorUsers().get(new Random().nextInt(sys.getOperatorUsers().size()));
		
		ArrayList<Movie> movies=operator.search(MovieNames);
		
		PhoneOrder order= new PhoneOrder(UUID.randomUUID(),operator);
		
		order.setShippingAddress(shippingAddress);
		order.setPaymentInfo(paymentInfo);
		
		for(Movie movie: movies) {
			order.addToOrder(movie);
		}
		
		
		return order.getOrderID();
		
	}
	
	public OrderStatus getOrderStatus(String id) {
		try {
		UUID uuid= UUID.fromString(id);
		SystemV sys= SystemV.getInstance();
		OperatorUser operator=sys.getOperatorUsers().get(new Random().nextInt(sys.getOperatorUsers().size()));
		PhoneOrder oder= operator.getOrderGeneral(uuid);
		if(oder==null) {
			return null;
		}else {
			return oder.getOrderStatus();
		}
		}catch(IllegalArgumentException e) {
			return null;
		}
	}
	
	
	public boolean cancelPhoneOrder(String id) {
		try {
			UUID uuid= UUID.fromString(id);
			SystemV sys= SystemV.getInstance();
			return sys.cancelPhoneOrder(uuid);
		}catch(IllegalArgumentException e) {
			return false;
		}
	}
	
	

}
