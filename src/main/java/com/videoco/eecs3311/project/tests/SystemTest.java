package com.videoco.eecs3311.project.tests;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import org.junit.*;
import com.videoco.eecs3311.project.*;

public class SystemTest {

	SystemV sys;

	@Before
	public void beforeTests() {
		sys = SystemV.getInstance();
	}

	@Test
	public void test1() {
		HashMap<UUID, NormalUser> map = sys.getNormalUsersMap();
		ArrayList<UserOrder> orders = sys.getUserOrders();
//		fail("Not yet implemented");
		for (Entry<UUID, NormalUser> entry : map.entrySet()) {
			assertEquals(entry.getKey(), entry.getValue().getUserID());
			assertEquals(entry.getValue().getUsertype(), UserType.normalUser);
			NormalUser u = entry.getValue();
			assertNotNull(u.getEmail());
			assertNotNull(u.getOrders());
			assertNotNull(u.getPassword());
			assertNotNull(u.getUserID());
			assertNotNull(u.getPassword());
			for (UserOrder order : orders) {
				if (order.getUserID().equals(u.getUserID())) {
					assertEquals(order, u.getOrder(order.getOrderID()));
				}
			}
		}

	}
	// Testing payment info
	@Test
	public void test2() {
		PaymentInfo info = new PaymentInfo("5394865394592637", "First Street");
		assertTrue(info.isValid());
		info.setBillingAddress("Second Street");
		assertEquals("Second Street", info.getBillingAddress());
		assertEquals("5394865394592637", info.getCreditCardNumber());
		info.setCreditCardNumber("5394865394592634");
		assertFalse(info.isValid());
		info.setCreditCardNumber("53948653a4592634");
		assertFalse(info.isValid());

	}
	
	
	// Requirement 1
	@Test
	public void test3() {
		HashMap<UserType, ArrayList<? extends User>> map = sys.getUsers();
		for (Entry<UserType, ArrayList<? extends User>> entry : map.entrySet()) {
			for (User u : entry.getValue()) {
				User user = sys.login(u.getUsername(), u.getPassword());

				switch (user.getUsertype()) {
				case adminUser:
					AdminUser user1 = (AdminUser) user;
					assertEquals(sys.getAdminMap().get(user1.getUserID()), user1);
					break;
				case normalUser:
					NormalUser user2 = (NormalUser) user;
					assertEquals(sys.getNormalUsersMap().get(user2.getUserID()), user2);

					break;
				case operatorUser:
					OperatorUser user3 = (OperatorUser) user;
					assertEquals(sys.getOperatorMap().get(user3.getUserID()), user3);

					break;
				default:
					break;
				}
			}
		}
	}
	// Requirement1
	@Test
	public void test4() {
		User user =sys.login("fakeusername", "fakepassword");
		assertNull(user);
	}
	
	
	//Requirement 2 
	@Test
	public void test5() {

		AdminUser user= new AdminUser(UUID.fromString("9c18b2aa-fd21-4fd1-bde5-50743fd5a84a"), "newAdmin", "password", "adminemail@gmail.com");
		sys.removeAdminUser(user.getUserID());
		assertTrue(sys.registerUser(user));
		assertTrue(sys.getAdminUsers().contains(user));
		AdminUser user2= new AdminUser(UUID.fromString("9c18b2aa-fd21-4fd1-bde5-50743fd5a84a"), "newAdmin", "password", "adminemail@gmail.com");
		sys.registerUser(user2);
		assertFalse(sys.getAdminUsers().contains(user2));
		assertFalse(sys.registerUser(user));
		assertTrue(sys.removeAdminUser(user.getUserID()));

		



	}
	// Requirement 2
	@Test
	public void test6() {

		NormalUser user= new NormalUser(UUID.fromString("97c57724-2964-4b6e-8a1f-74d925940eca"), "newUser", "password", "useremail@gmail.com");
		sys.removeNormalUser(user.getUserID());
		sys.registerUser(user);
		assertTrue(sys.getNormalUsers().contains(user));
		NormalUser user2= new NormalUser(UUID.fromString("97c57724-2964-4b6e-8a1f-74d925940eca"), "newUser", "password", "useremail@gmail.com");
		sys.registerUser(user2);
		assertFalse(sys.getNormalUsers().contains(user2));
		assertFalse(sys.registerUser(user));
		assertTrue(sys.removeNormalUser(user.getUserID()));
		assertTrue(sys.registerUser(user));
		assertTrue(sys.removeNormalUser(user.getUserID()));
		assertFalse(sys.removeNormalUser(user.getUserID()));




	}
	
	// Requirement 2
	@Test
	public void test7() {
		OperatorUser user = new OperatorUser(UUID.fromString("b3f1f736-d1e8-496c-84f2-d4a414aa1d01"), "newOp",
				"password", "newOp@gmail.com");
		assertFalse(sys.removeOperatorUser(user.getUserID()));
		sys.registerUser(user);
		assertTrue(sys.getOperatorUsers().contains(user));
		OperatorUser user2 = new OperatorUser(UUID.fromString("b3f1f736-d1e8-496c-84f2-d4a414aa1d01"), "newOp",
				"password", "newOp@gmail.com");
		sys.registerUser(user2);
		assertFalse(sys.getOperatorUsers().contains(user2));
		assertFalse(sys.registerUser(user));
		assertTrue(sys.removeOperatorUser(user.getUserID()));
		assertTrue(sys.registerUser(user));
		assertTrue(sys.removeOperatorUser(user.getUserID()));
		assertFalse(sys.removeOperatorUser(user.getUserID()));
	}
	// Requirement 2
	@Test
	public void test8() {
		OperatorUser user = new OperatorUser(UUID.fromString("ec9fcc60-e8b1-40c4-a09e-e50acc613eeb"), "kjha95",
				"password", "adminemail@gmail.com");
		assertFalse(sys.removeOperatorUser(user.getUserID()));
		assertFalse(sys.registerUser(user));
		NormalUser user2= new NormalUser(UUID.fromString("cfd62330-8c3c-426d-a0b5-198970237182"),"uniqueuser","uniquepass","jha.baller2@gmail.com");
		assertFalse(sys.registerUser(user2));
	}
	 
	
	

	
	//Requirement4 creating order by adding movie to order + Requirement 13 cancelling order
	@Test
	public void test9() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		UserOrder order= new UserOrder(UUID.randomUUID(),sys.getNormalUsersMap().get(user.getUserID()));
		user.addOrder(order);
		user.addMovieToOrder(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")));
		order.setPayWithPoints(false);
		order.setShippingAddress("145 address way");
		order.setPaymentInfo(new PaymentInfo("4539307898619904","145 address way"));
		assertEquals(OrderStatus.Creating, order.getOrderStatus());
		order.placeOrder();
		assertEquals(OrderStatus.Delivering, order.getOrderStatus());
		assertTrue(order.cancelOrder());
		assertEquals(0, user.getOrders().size());
		assertTrue(sys.removeNormalUser(user.getUserID()));
		
	}
	//Req 4 + Req5 cant add movie with no stock to order,  can review tenative order
	@Test 
	public void test10() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		UserOrder order= new UserOrder(UUID.randomUUID(),sys.getNormalUsersMap().get(user.getUserID()));
		Movie movie=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).clone();
		movie.setID(UUID.randomUUID());
		movie.setStock(0);
		sys.addMovie(movie);
		assertFalse(order.addToOrder(movie));
		AdminUser user1= sys.getAdminUsers().get(0);
		user1.removeMovie(movie);
		assertEquals(0,order.getMovies().size());
		assertTrue(sys.removeNormalUser(user.getUserID()));

		
		
		
	}
	
	//Req5
	@Test
	public void test11() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		UserOrder order= new UserOrder(UUID.randomUUID(),sys.getNormalUsersMap().get(user.getUserID()));
		user.addOrder(order);
		user.addMovieToOrder(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")));
		user.addMovieToOrder(sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")));
		assertEquals(2, user.getOrder(order.getOrderID()).getMovies().size());
		user.getOrder(order.getOrderID()).deleteFromOrder(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf"));		
		user.getOrder(order.getOrderID()).deleteFromOrder(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae"));		
		assertEquals(0, user.getOrder(order.getOrderID()).getMovies().size());
		assertEquals(1,user.getOrders().size());
		user.cancelOrder(order.getOrderID());
		assertEquals(0,user.getOrders().size());		
		assertTrue(sys.removeNormalUser(user.getUserID()));
	}

	//req14
	@Test
	public void test12() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		user.setEmail("Normalemail2@gmail.com");
		sys.updateNormalUser(user.getUserID(), user);
		assertEquals(user.getEmail(), sys.getNormalUsersMap().get(user.getUserID()).getEmail());
		assertTrue(sys.removeNormalUser(user.getUserID()));
	}
	
	
	//req6 && req 7
	@Test
	public void test13() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario",10);
		assertTrue(sys.registerUser(user));
		assertEquals(10,user.getLoyaltyPoints());
		user.addMovieToOrder(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")));
		user.addMovieToOrder(sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")));
		UserOrder order=user.getOrders().get(user.getOrders().size()-1);
		order.setPaymentInfo(new PaymentInfo("4539307898619904","145 address way"));
		order.setShippingAddress("145 address way");
		order.setPayWithPoints(true);
		assertEquals(OrderStatus.Creating, order.getOrderStatus());
		order.placeOrder();
		assertEquals(OrderStatus.Delivering, order.getOrderStatus());
	
		assertEquals(0,user.getLoyaltyPoints());
		
		assertTrue(order.cancelOrder());
		assertTrue(sys.removeNormalUser(user.getUserID()));
	}
	//req9
	@Test
	public void test14() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario",10);
		assertTrue(sys.registerUser(user));
		int i=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		int j=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		user.addMovieToOrder(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")));
		user.addMovieToOrder(sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")));
		UserOrder order=user.getOrders().get(user.getOrders().size()-1);
		order.setPaymentInfo(new PaymentInfo("4539307898619904","145 address way"));
		order.setShippingAddress("145 address way");
		order.setPayWithPoints(true);
		assertEquals(OrderStatus.Creating, order.getOrderStatus());
		order.placeOrder();
		assertEquals(OrderStatus.Delivering, order.getOrderStatus());
		int i1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		int j1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		assertEquals(i-1,i1);
		assertEquals(j-1,j1);
		assertTrue(order.cancelOrder());
		i1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		j1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		assertEquals(i,i1);
		assertEquals(j,j1);
		assertTrue(sys.removeNormalUser(user.getUserID()));

	}
	//req9 & req10
	@Test
	public void test15() {
		PhoneUser user= new PhoneUser("4539307898619904","145 address way","145 address way");
		ArrayList<String> movieNames= new ArrayList<String>();
		int i=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		int j=sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getStock();
		
		movieNames.add(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getTitle());
		movieNames.add(sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getTitle());
		UUID orderID=user.placePhoneOrder(movieNames);
		assertNotNull(orderID);
		int i1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		int j1=sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getStock();
		
		assertEquals(i-1,i1);
		assertEquals(j-1,j1);
		
		assertTrue(sys.cancelPhoneOrder(orderID));
		i1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		j1=sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getStock();
		assertEquals(i,i1);
		assertEquals(j,j1);		
		
		
		
		
//		user.placePhoneOrder(null);
		

	}
	// req10 & req 11
	@Test
	public void test16() {
		PhoneUser user= new PhoneUser("4539307898619904","145 address way","145 address way");
		ArrayList<String> movieNames= new ArrayList<String>();
		int i=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		int j=sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getStock();
		
		movieNames.add(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getTitle());
		movieNames.add(sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getTitle());
		UUID orderID=user.placePhoneOrder(movieNames,sys.getOperatorUsers().get(0));
		assertNotNull(orderID);
		int i1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		int j1=sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getStock();
		
		assertEquals(i-1,i1);
		assertEquals(j-1,j1);
		assertEquals(OrderStatus.Delivering,user.getOrderStatus(orderID.toString()));	
		assertEquals(null,user.getOrderStatus(UUID.randomUUID().toString()));		
		
		assertTrue(sys.cancelPhoneOrder(orderID));
		
		i1=sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")).getStock();
		j1=sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")).getStock();
		assertEquals(i,i1);
		assertEquals(j,j1);		
	}
	//Req 26, Req 25, 13
	@Test
	public void test17() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		ArrayList<Movie> movies= sys.getMovies();
		int i=0;
		do {
			i=new Random().nextInt(movies.size());
		}while(i<0 || i>=movies.size());
		Movie random = movies.get(i);
		
		user.addMovieToOrder(random);
		UserOrder order= user.getOrders().get(0);
		order.setOrderDate(LocalDate.now().minusDays(10));
		order.setPayWithPoints(false);
		order.setPaymentInfo(new PaymentInfo("4539307898619904","145 address way"));
		order.setShippingAddress("145 address way");
		assertEquals(OrderStatus.Creating, order.getOrderStatus());
		
		order.placeOrder();
		
		assertEquals(OrderStatus.Delivering, order.getOrderStatus());
		
		HashMap<UUID, LocalDate> res = DeliveryService.checkIfDelivered();
//		System.out.println(res);
		sys.updateOrdersWithDeliveryDates(res);
		sys.findAndUpdatePhoneOverdue();
		sys.findAndUpdateUserOverdue();
		
		assertEquals(OrderStatus.Delivered,order.getOrderStatus());
		assertFalse(sys.cancelUserOrder(order.getOrderID()));
		assertTrue(sys.cancelUserOrderDelivered(order.getOrderID()));
		assertTrue(sys.removeNormalUser(user.getUserID()));		
	}
	
	@Test //req1 && req2
	public void test18() {
		NormalUser user= sys.getNormalUsers().get(0);
		NormalUser user2= new NormalUser(UUID.randomUUID(),user.getUsername(),"password","email");
		
		assertFalse(sys.registerUser(user2));
		
		assertNull(sys.validateUser(user2.getUsername(), user2.getPassword()));
		
	}
	@Test
	//req3
	public void test19() {
		NormalUser user= sys.getNormalUsers().get(0);
		ArrayList<Movie> movies=sys.getMovies();
		assertEquals(user.search(),movies);
	}
	//req3 && req14
	@Test
	public void test20() {
		NormalUser user= sys.getNormalUsers().get(0);
		ArrayList<Movie> movies=sys.getMovies();
		ArrayList<Movie> searchRes= new ArrayList<Movie>();
		for(Movie movie:movies) {
			if(movie.getMovieInfo().getGenre().equals(Genre.Action)) {
				searchRes.add(movie);
			}
		}
		ArrayList<Movie> search=user.search("Action");
		assertEquals(searchRes.size(),search.size());
		for(int i=0; i<searchRes.size();i++) {
			assertEquals(searchRes.get(i).getId(),search.get(i).getId());
		}
		 user.setUsername("Baller2");
	}
	// req3
	@Test
	public void test21() {
		NormalUser user= sys.getNormalUsers().get(0);
		ArrayList<Movie> movies=sys.getMovies();
		ArrayList<Movie> searchRes= new ArrayList<Movie>();
		for(Movie movie:movies) {
			searchRes.add(movie);
			break;
		}
		ArrayList<Movie> search=user.search(searchRes.get(0).getTitle());
		assertEquals(searchRes.size(),search.size());
		for(int i=0; i<searchRes.size();i++) {
			assertEquals(searchRes.get(i).getId(),search.get(i).getId());
		}
	}
	
	//Req 6 and req 19
	@Test
	public void test22() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		user.addMovieToOrder(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")));
		user.addMovieToOrder(sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")));
		UserOrder order= user.getOrders().get(user.getOrders().size()-1);
		order.setPayWithPoints(true);
		
		order.setPaymentInfo( new PaymentInfo("4539307898619904","145 address way"));
		order.setShippingAddress("145 address way");
		
		assertFalse(order.placeOrder());
		
		AdminUser user2= sys.getAdminUsers().get(0);
		
		
		user2.removeAccount(user.getUserID());
	
		
	}
	//Req 6 
	@Test
	public void test23() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		user.addMovieToOrder(sys.getMovie(UUID.fromString("6b9f2324-2442-4992-931a-ede3eeaecabf")));
		user.addMovieToOrder(sys.getMovie(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae")));
		UserOrder order= user.getOrders().get(user.getOrders().size()-1);
		order.setPayWithPoints(false);
		
		order.setPaymentInfo( new PaymentInfo("4539307a98619904","145 address way"));
		order.setShippingAddress("145 address way");
		
		assertFalse(order.placeOrder());
		
		AdminUser user2= sys.getAdminUsers().get(0);
		
		
		user2.removeAccount(user.getUserID());
	
		
	}
	
	
	//Req14
	@Test
	public void test24() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		user.setProvince("British Columbia");
		
		assertEquals("Normalemail@gmail.com",user.getEmail());
		
		user.setEmail("newNormalEmail@gmail.com");
		
		assertEquals("newNormalEmail@gmail.com",user.getEmail());

		AdminUser user2= sys.getAdminUsers().get(0);
		
		
		user2.removeAccount(user.getUserID());
	
	}
	//Req14 && req18
	@Test
	public void test25() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		assertEquals("Ontario", user.getProvince());
		user.setProvince("British Columbia");
		AdminUser user2= sys.getAdminUsers().get(0);
		
		user2.updateUser(user.getUserID(), user);
		
		assertEquals("British Columbia", user.getProvince());
		
		assertEquals("Normalemail@gmail.com",user.getEmail());
		
		user.setEmail("newNormalEmail@gmail.com");
		
		assertEquals("newNormalEmail@gmail.com",user.getEmail());

		
		
		user2.removeAccount(user.getUserID());
	
		
		
		
	}
	
	@Test
	public void test26() {
		MovieInfo info = new MovieInfo();
		String[] actors = { "actor25", "actor26", "actor27" };
		String[] directors = { "director25", "director26", "director27" };
		Integer[] ratings = { 1, 2, 3, 4, 2, 1, 0, 5 };
		Genre genre = Genre.Action;
		String releaseYear = "2012";
		String description = "Good Movie :D";
		info.setActors(new ArrayList<String>(Arrays.asList(actors)));
		info.addActor("newActor");
		assertEquals(actors.length+1,info.getActors().size());
		info.removeActor(3);
		assertEquals(actors.length,info.getActors().size());		
		info.setDirectors(new ArrayList<String>(Arrays.asList(directors)));
		info.addDirector("director");
		assertEquals(directors.length+1,info.getDirectors().size());
		info.removeDirector(3);
		assertEquals(directors.length,info.getDirectors().size());
		
		info.setRatings(new ArrayList<Integer>(Arrays.asList(ratings)));
		info.setDescription(description);
		info.setGenre(genre);
		info.setReleaseYear(releaseYear);
		
		assertEquals(genre,info.getGenre());
		
		assertEquals(actors.length,info.getActors().size());
		assertEquals(directors.length,info.getDirectors().size());
		assertEquals(ratings.length,info.getRatings().size());
		assertEquals(description,info.getDescription());
		assertEquals(releaseYear,info.getReleaseYear());
		info.toString();


		

	}
	//Req14 && req18
	@Test
	public void test27() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		assertEquals("Ontario", user.getProvince());
		user.setProvince("British Columbia");
		AdminUser user2= sys.getAdminUsers().get(0);
		
		user2.updateUser(user.getUserID(), user);
		
		assertEquals("British Columbia", user.getProvince());
		
		assertEquals("Normalemail@gmail.com",user.getEmail());
		
		user.setEmail("newNormalEmail@gmail.com");
		
		assertEquals("newNormalEmail@gmail.com",user.getEmail());

		
		
		user2.removeAccount(user.getUserID());
	
		
		
		
	}
	@Test
	public void test28() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		ArrayList<Movie> movies= sys.getMovies();
		int i=0;
		do {
			i=new Random().nextInt(movies.size());
		}while(i<0 || i>=movies.size());
		Movie random = movies.get(i);
		
		user.addMovieToOrder(random);
		UserOrder order= user.getOrders().get(0);
		order.setOrderDate(LocalDate.now().minusDays(10));
		order.setPayWithPoints(false);
		order.setPaymentInfo(new PaymentInfo("4539307898619904","145 address way"));
		order.setShippingAddress("145 address way");
		assertEquals(OrderStatus.Creating, order.getOrderStatus());
		
		order.placeOrder();
		
		assertEquals(OrderStatus.Delivering, order.getOrderStatus());
		
		HashMap<UUID, LocalDate> res = DeliveryService.checkIfDelivered();
//		System.out.println(res);
		sys.updateOrdersWithDeliveryDates(res);
		sys.findAndUpdatePhoneOverdue();
		sys.findAndUpdateUserOverdue();
		
		assertEquals(OrderStatus.Delivered,order.getOrderStatus());
		assertTrue(PaymentService.refundNormalUserOrder(order));
		assertFalse(sys.cancelUserOrder(order.getOrderID()));
		assertTrue(sys.cancelUserOrderDelivered(order.getOrderID()));
		assertTrue(sys.removeNormalUser(user.getUserID()));		
	}
	
	@Test
	public void test29() {
	MovieInfo info = new MovieInfo();
	String[] actors = { "actor25", "actor26", "actor27" };
	String[] directors = { "director25", "director26", "director27" };
	Integer[] ratings = { 1, 2, 3, 4, 2, 1, 0, 5 };
	Genre genre = Genre.Action;
	String releaseYear = "2012";
	String description = "Good Movie :D";
	info.setActors(new ArrayList<String>(Arrays.asList(actors)));
	info.setDirectors(new ArrayList<String>(Arrays.asList(directors)));
	info.setRatings(new ArrayList<Integer>(Arrays.asList(ratings)));
	info.setDescription(description);
	info.setGenre(genre);
	info.setReleaseYear(releaseYear);
	Movie movie = new Movie("Greatest Movie Ever", 15, UUID.fromString("ee97b06c-55e5-4948-b9f6-2c7fa93a3ec1"),
			500.6, info);
	AdminUser user=sys.getAdminUsers().get(0);
	user.addMovie(movie);
	user.removeMovie(movie);
	}

	
	
	
	
	
		
	
	
		
	
	
		
	
	




}
