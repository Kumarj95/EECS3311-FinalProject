package com.videoco.eecs3311.project;

import java.time.LocalDate;

public class PaymentService {
	public static boolean handleNormalUserOrder(UserOrder order) {
		SystemV s= SystemV.getInstance();
		if(order.isPayWithPoints()) {
			NormalUser a=s.getNormalUsersMap().get(order.getUserID());
			if(a.getLoyaltyPoints()>=10) {
				a.setLoyaltyPoints(a.getLoyaltyPoints()-10);
				s.updateNormalUser(order.getUserID(), a);
				return true;
			}
			return false;
		}else {
			if(order.getPaymentInfo().isValid()) {
				
				// do transaction
				return true;
			}
			return false;
		}
	}
	public static boolean handlePhoneUserOrder(PhoneOrder order) {
			if(order.getPaymentInfo().isValid()) {
				// do transaction
				return true;
			}
			return false;
	}
	
	public static boolean refundNormalUserOrder(UserOrder order) {
		SystemV s= SystemV.getInstance();
		if(order.isPayWithPoints()) {
			NormalUser a=s.getNormalUsersMap().get(order.getUserID());
			a.setLoyaltyPoints(a.getLoyaltyPoints()+10);
			s.updateNormalUser(order.getUserID(), a);
			return true;
		}else {
			// do refund through credit card
		}
		return true;
	}
	public static boolean refundPhoneOrder(PhoneOrder order) {
		//refund phone order
		return true;
	}
	
	public static boolean chargeUserLateFee(UserOrder order) {
		if(!order.getDateDelivered().equals(LocalDate.MIN)) {
			if(order.getDateDelivered().plusDays(14).isAfter(LocalDate.now())) {
				SystemV s= SystemV.getInstance();
				
				return true;
			}
		}
		return false;
	}



}
