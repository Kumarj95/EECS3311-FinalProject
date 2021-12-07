package com.videoco.eecs3311.project;

import java.util.UUID;

public class  PhoneOrder  extends Order{
	
	private OperatorUser operator;
	
	public PhoneOrder(UUID orderID, OperatorUser op) {
		super(orderID);
		this.operator=op;
		// TODO Auto-generated constructor stub
	}
	
	public UUID getOperatorID() {
		return operator.getUserID();
	}
	
	public OperatorUser getOperator() {
		return operator;
	}
	

	public boolean placeOrder() {
		SystemV s= SystemV.getInstance();
		if(!getPaymentInfo().isValid()) {
			return false;
		}
		return s.addPhoneOrder(this);
	}
	
	public boolean cancelOrder() {
		SystemV sys= SystemV.getInstance();
		return sys.cancelPhoneOrder(getOrderID());
	}

}
