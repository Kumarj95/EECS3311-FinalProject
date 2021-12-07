package com.videoco.eecs3311.project;

import java.util.ArrayList;

public class PaymentInfo {
	private String creditCardNumber;
	private String billingAddress;
	public PaymentInfo(String creditCardNumber, String billingAddress) {
		this.creditCardNumber = creditCardNumber;
		this.billingAddress = billingAddress;
	}
	
	public boolean isValid() {
		ArrayList<Integer> l1=new  ArrayList<Integer>();
		ArrayList<Integer> l2=new  ArrayList<Integer>();
		for(int i=creditCardNumber.length()-1; i>=0;i--) {
			if(!Character.isDigit(creditCardNumber.charAt(i))) {
				return false;
			}
			if((i+1)%2==1) {
				int a= Character.getNumericValue(creditCardNumber.charAt(i))*2;
				if(a<10) {
					l1.add(a);
				}else {
					l1.add(a%10+a/10);
					
				}
			}else {
				int a= Character.getNumericValue(creditCardNumber.charAt(i));
				l2.add(a);
			}
		}
		int num1= 0;
		for(Integer i1: l1) {
			num1+=i1;
		}
		
		
		for(Integer i2: l2) {
			num1+=i2;
		}
		
		if(num1%10==0) {
			return true;
		}else {
			return false;
		}

	}
	

	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}
	

}
