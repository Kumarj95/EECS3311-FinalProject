package com.videoco.eecs3311.project.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
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
		NormalUser user2= new NormalUser(UUID.fromString("cfd62330-8c3c-426d-a0b5-198970237182"),"uniqueuser","uniquepass","jha.baller@gmail.com");
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
	//Req 4 + Req 5 cant add movie with no stock to order,  can review tenative order
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
		sys.removeMovie(movie);
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

	//
	@Test
	public void test12() {
		NormalUser user= new NormalUser(UUID.randomUUID(), "NewNormalUser","NewPassword","Normalemail@gmail.com","Ontario");
		assertTrue(sys.registerUser(user));
		user.setEmail("Normalemail2@gmail.com");
		sys.updateNormalUser(user.getUserID(), user);
		assertEquals(user.getEmail(), sys.getNormalUsersMap().get(user.getUserID()).getEmail());
		assertTrue(sys.removeNormalUser(user.getUserID()));
	}




}
