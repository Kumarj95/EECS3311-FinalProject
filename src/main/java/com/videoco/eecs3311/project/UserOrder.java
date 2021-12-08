package com.videoco.eecs3311.project;

import java.util.ArrayList;
import java.util.UUID;

public class UserOrder extends Order {
	
	private NormalUser user;
	private Boolean payWithPoints;
 
	public Boolean isPayWithPoints() {
		return payWithPoints;
	}

	public void setPayWithPoints(boolean payWithPoints) {
		this.payWithPoints = payWithPoints;
	}

	public UserOrder(UUID orderID, NormalUser user) {
		super(orderID);
		this.user=user;
            }

	public UUID getUserID() {
		return user.getUserID();
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String s= super.toString();
		
		s+= " belonging to user " + user.getUserID();
		
		return s;
	}
	public boolean placeOrder() {
		SystemV s= SystemV.getInstance();
		if((payWithPoints &&user.getLoyaltyPoints()<10) || (!getPaymentInfo().isValid()) ) {
			return false;
		}else {
		return s.addUserOrder(this);
		}
	}
	
	public boolean cancelOrder() {
		SystemV sys= SystemV.getInstance();
		return sys.cancelUserOrder(getOrderID());
	}
	
	
//	@Override
//	public Order clone() {
//		ArrayList<Movie> moviesClone=new ArrayList<Movie>();
//		for(Movie movie: movies) {
//			moviesClone.add(movie.clone());
//		}
//		UserOrder clone = new UserOrder(this.getOrderID(),this.user);
//		clone.setOrderDate(this.getOrderDate());
//	}



}
