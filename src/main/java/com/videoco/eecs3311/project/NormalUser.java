package com.videoco.eecs3311.project;

import java.util.ArrayList;
import java.util.UUID;

public class NormalUser extends User {
	private ArrayList<UserOrder> orders;
	int loyaltyPoints;
	String province;
	boolean allOrdersDelivered;
	public NormalUser(UUID userID, String username, String password, String email) {
		this(userID,username,password,email,"Ontario",0);
		
	}
	public NormalUser(UUID userID, String username, String password, String email, String province, int points) {
		this(userID,username,password,email,province,points,true);
		
	}
	public NormalUser(UUID userID, String username, String password, String email, String province) {
		this(userID,username,password,email,province,0);
	}
	public NormalUser(UUID userID, String username, String password, String email, String province, Boolean bool) {
		this(userID,username,password,email,province,0,bool);
	}
	public NormalUser(UUID userID, String username, String password, String email, String province, int points, Boolean bool) {
		super(userID,username,password,UserType.normalUser,email);
		orders= new ArrayList<UserOrder>();
		this.province=province;
		this.loyaltyPoints=points;
		this.allOrdersDelivered=bool;
	}



	
	public void addOrder(UserOrder order) {
		if(this.orders!=null) {
		orders.add(order);
		updateAllDelievered();
		}		
	}
	
	public boolean isAllOrdersDelivered() {
		return allOrdersDelivered;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
            SystemV sys= SystemV.getInstance();
              sys.updateNormalUser(this.getUserID(), this);             
                
	}
        @Override
       public void setUsername(String username) {
            SystemV sys= SystemV.getInstance();
                String oldUsername=this.getUsername();
		super.setUsername(username);
                if(!sys.isValidNewUser(this)){
                    super.setUsername(oldUsername);
                }else{
                        sys.updateNormalUser(this.getUserID(), this);
                                 
                }
	}
               @Override
       public void setEmail(String email) {
            SystemV sys= SystemV.getInstance();
                String oldEmail=this.getEmail();
		super.setEmail(email);
                if(!sys.isValidNewUser(this)){
                    super.setEmail(oldEmail);
                }else{
                        sys.updateNormalUser(this.getUserID(), this);
                                 
                }
	}
               @Override
       public void setPassword(String password) {
            SystemV sys= SystemV.getInstance();
                String oldPassword=this.getPassword();
		super.setPassword(password);
                if(!sys.isValidNewUser(this)){
                    super.setPassword(oldPassword);
                }else{
                        sys.updateNormalUser(this.getUserID(), this);
                                 
                }
	}
       



	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}
	private void updateAllDelievered() {
		for(Order order:orders) {
			if(!order.getOrderStatus().equals(OrderStatus.Delivered)) {
				this.allOrdersDelivered=false;
			}
		}
	}
	
	public boolean addMovieToOrder(Movie movie) {
            if(orders.size()==0 || !orders.get(orders.size()-1).getOrderStatus().equals(OrderStatus.Creating)){
                UserOrder userOrder= new UserOrder(UUID.randomUUID(),this);
                if(userOrder.addToOrder(movie)){
                    orders.add(userOrder);
                    return true;
                }else{
                    return false;
                }
            }else{
                return orders.get(orders.size()-1).addToOrder(movie);
            }
	}
	
	public Order getOrder(UUID orderID) {
		for(UserOrder order: orders) {
			if(order.getOrderID().equals(orderID)) {
				return order;
			}
		}
		return null;
	}
	
	public ArrayList<UserOrder> getOrders(){
		return orders;
	}
	
	public void setLoyaltyPoints(int i) {
		loyaltyPoints=i;
	}
	
	public void cancelOrder(UUID userOrder) {
		int i=0;
		for(UserOrder order:orders) {
			if(order.getOrderID().equals(userOrder)) {
					orders.remove(i);
					break;
			}
			i++;
		}
	}
	@Override
	public String toString() {
		String s= super.toString();
		s+= " with orders  " + orders.toString();
		return s;
	}
        
        
        public ArrayList<Movie> search(){
            SystemV sys= SystemV.getInstance();
            return sys.getMovies();
        }
        
         public ArrayList<Movie> search(String s){
            SystemV sys= SystemV.getInstance();
            ArrayList<Movie> ret= new ArrayList<Movie>();
            try{
                UUID id= UUID.fromString(s);
                for(Movie movie: sys.getMovies()){
                    if(movie.getId().equals(id)){
                        ret.add(movie);
                    }
                }
            }catch(IllegalArgumentException e){
                try{
                    Genre genre= Genre.valueOf(s);
                    for(Movie movie: sys.getMovies()){
                        if(movie.getMovieInfo().getGenre().equals(genre)){
                            ret.add(movie);
                        }
                    }
               }catch(IllegalArgumentException b){
                for(Movie movie: sys.getMovies()){
                    if(movie.getTitle().equals(s)){
                        ret.add(movie);
                    }
                }
                }

            }
            return ret;
        }
	
	


}
