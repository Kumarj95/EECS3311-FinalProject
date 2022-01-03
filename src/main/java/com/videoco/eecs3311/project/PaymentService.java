package com.videoco.eecs3311.project;

import java.time.LocalDate;

public class PaymentService {
	/**
	 * javadoc
	 *
	 */
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
			NormalUser a=s.getNormalUsersMap().get(order.getUserID());                    
			if(order.getPaymentInfo().isValid()) {
				a.setLoyaltyPoints(a.getLoyaltyPoints()+1);
				s.updateNormalUser(order.getUserID(), a);
                                
				// do transaction
				return true;
			}
			return false;
		}
	}
	/**
	 * javadoc
	 *
	 */

	public static boolean handlePhoneUserOrder(PhoneOrder order) {
			if(order.getPaymentInfo().isValid()) {
				// do transaction
				return true;
			}
			return false;
	}
	/**
	 * javadoc
	 *
	 */

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
	/**
	 * javadoc
	 *
	 */

	public static boolean refundPhoneOrder(PhoneOrder order) {
		//refund phone order
		return true;
	}
	/**
	 * javadoc
	 *
	 */

	
	public static boolean chargeUserLateFee(UserOrder order) {
				double lateFee=calculateUserLateFee(order);
				System.out.println("Charging Late Fee! " + lateFee+ " on order id "+ order.getOrderID());
				return true;

	}
        
	/**
	 * javadoc
	 *
	 */

        public static double calculateUserLateFee(UserOrder order){
            SystemV sys= SystemV.getInstance();
            String userProvince= sys.getNormalUsersMap().get(order.getUserID()).getProvince();
            double lateFee=0;
            if(!userProvince.equals("Ontario")){
                lateFee+=10;
            }

            for(Movie movie: order.getMovies()){
                lateFee+=1;
            }
            return lateFee;
        }
    	/**
    	 * javadoc
    	 *
    	 */

    	public static boolean chargePhoneUserLateFee(PhoneOrder order) {
    				double lateFee=calculatePhoneUserLateFee(order);
    				System.out.println("Charging Late Fee! " + lateFee+ " on order id "+ order.getOrderID());
    				
    				// do third party stuff
    				
    				return true;
    	}
    	
    	/**
    	 * javadoc
    	 *
    	 */

    	
    	public static double calculatePhoneUserLateFee(PhoneOrder order) {
    		double d=0;
    		for(Movie movie: order.getMovies()) {
    			d+=1;
    		}
    		if(!order.getShippingAddress().contains("Ontario")) {
    			d+=9.99;
    		}
    		//assume phone users dont live in toronto
    		
    		return d;
    	}
        



}
