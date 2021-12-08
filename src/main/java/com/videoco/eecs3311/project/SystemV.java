package com.videoco.eecs3311.project;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class SystemV {

	private volatile static SystemV INSTANCE = null;
	private final String filepath = "/src/main/java/com/videoco/eecs3311/project/data/";

	private HashMap<UserType, ArrayList<User>> users = new HashMap<UserType, ArrayList<User>>();
	private HashMap<UUID, NormalUser> normalUsers = new HashMap<UUID, NormalUser>();
	private HashMap<UUID, AdminUser> adminUsers = new HashMap<UUID, AdminUser>();
	private HashMap<UUID, OperatorUser> operatorUsers = new HashMap<UUID, OperatorUser>();

	private HashMap<UUID, Movie> movies = new HashMap<UUID, Movie>();
	private HashMap<UUID, UserOrder> userOrders = new HashMap<UUID, UserOrder>();
	private HashMap<UUID, PhoneOrder> phoneOrders = new HashMap<UUID, PhoneOrder>();

	public static String formatJSONStr(final String json_str, final int indent_width) {
		final char[] chars = json_str.toCharArray();
		final String newline = System.lineSeparator();

		String ret = "";
		boolean begin_quotes = false;

		for (int i = 0, indent = 0; i < chars.length; i++) {
			char c = chars[i];

			if (c == '\"') {
				ret += c;
				begin_quotes = !begin_quotes;
				continue;
			}

			if (!begin_quotes) {
				switch (c) {
				case '{':
				case '[':
					ret += c + newline + String.format("%" + (indent += indent_width) + "s", "");
					continue;
				case '}':
				case ']':
					ret += newline + ((indent -= indent_width) > 0 ? String.format("%" + indent + "s", "") : "") + c;
					continue;
				case ':':
					ret += c + " ";
					continue;
				case ',':
					ret += c + newline + (indent > 0 ? String.format("%" + indent + "s", "") : "");
					continue;
				default:
					if (Character.isWhitespace(c))
						continue;
				}
			}

			ret += c + (c == '\\' ? "" + chars[++i] : "");
		}

		return ret;
	}

	private SystemV() {

		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "normalUsers.json");
			Object obj = jsonParser.parse(reader);
//			System.out.println(obj);
			JSONArray normalUserList = (JSONArray) obj;

			for (Object obj1 : normalUserList) {
				JSONObject normalUser = (JSONObject) obj1;
				UUID userID = UUID.fromString(normalUser.get("userID").toString());
				String username = (String) normalUser.get("username");
				String password = (String) normalUser.get("password");
				String email = (String) normalUser.get("email");
				UserType userType = UserType.valueOf(normalUser.get("userType").toString());
				Integer loyaltyPoints = Integer.valueOf(normalUser.get("loyaltyPoints").toString());
				String province = (String) normalUser.get("province");

				Boolean allOrdersDelivered = Boolean.valueOf(normalUser.get("allOrdersDelivered").toString());
				NormalUser user = new NormalUser(userID, username, password, email, province, loyaltyPoints,
						allOrdersDelivered);
				normalUsers.put(user.getUserID(), user);
			}

			FileReader reader1 = new FileReader(System.getProperty("user.dir") + filepath + "adminUsers.json");
			obj = jsonParser.parse(reader1);
//			System.out.println(obj);
			JSONArray adminUserList = (JSONArray) obj;

			for (Object obj1 : adminUserList) {
				JSONObject adminUser = (JSONObject) obj1;
				UUID userID = UUID.fromString(adminUser.get("userID").toString());
				String username = (String) adminUser.get("username");
				String password = (String) adminUser.get("password");
				String email = (String) adminUser.get("email");
				AdminUser user = new AdminUser(userID, username, password, email);
				adminUsers.put(user.getUserID(), user);
			}
//			for(Entry<UUID,AdminUser> user:adminUsers.entrySet()) {
//				System.out.println(user.getValue());
//			}
			FileReader reader2 = new FileReader(System.getProperty("user.dir") + filepath + "/operatorUsers.json");

			obj = jsonParser.parse(reader2);
//				System.out.println(obj);
			JSONArray operatorUserList = (JSONArray) obj;

			for (Object obj1 : operatorUserList) {
				JSONObject operatorUser = (JSONObject) obj1;
				UUID userID = UUID.fromString(operatorUser.get("userID").toString());
				String username = (String) operatorUser.get("username");
				String password = (String) operatorUser.get("password");
				String email = (String) operatorUser.get("email");
				OperatorUser user = new OperatorUser(userID, username, password, email);
				operatorUsers.put(user.getUserID(), user);
			}
//				for(Entry<UUID,OperatorUser> user:operatorUsers.entrySet()) {
//					System.out.println(user.getValue());
//				}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader4 = new FileReader(System.getProperty("user.dir") + filepath + "movies.json");
			Object obj = jsonParser.parse(reader4);
			JSONArray movieList = (JSONArray) obj;

			for (Object obj1 : movieList) {
				JSONObject movie = (JSONObject) obj1;
//            	JSONObject movieInfo= (JSONObject) movie.get("movieInfo");
//        		JSONArray actorsArr = (JSONArray)movieInfo.get("actors");
				Movie movieOb = parseMovieObject(movie);
				movies.put(movieOb.getId(), movieOb);

			}
			reader4.close();

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch blocks
			e.printStackTrace();
		}

		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader35 = new FileReader(System.getProperty("user.dir") + filepath + "userOrders.json");
			Object obj = jsonParser.parse(reader35);
			JSONArray userOrderList = (JSONArray) obj;
			for (Object obj1 : userOrderList) {
				UserOrder order = parseUserOrder((JSONObject) obj1);
				userOrders.put(order.getOrderID(), order);
			}

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "phoneOrders.json");
			Object obj = jsonParser.parse(reader);
			JSONArray phoneOrderList = (JSONArray) obj;
			for (Object obj1 : phoneOrderList) {
				JSONObject phoneObj = (JSONObject) obj1;
				PhoneOrder order = parsePhoneOrder(phoneObj);
				phoneOrders.put(order.getOrderID(), order);
			}
			reader.close();
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (UUID id : userOrders.keySet()) {
			UserOrder order1 = userOrders.get(id);
			for (UUID userID : normalUsers.keySet()) {
				if (order1.getUserID().equals(userID)) {
					normalUsers.get(userID).addOrder(order1);
				}
			}
		}

		for (Entry<UUID, PhoneOrder> entry : phoneOrders.entrySet()) {
			PhoneOrder order = entry.getValue();
			for (Entry<UUID, OperatorUser> entry2 : operatorUsers.entrySet()) {
				if (order.getOperatorID().equals(entry2.getKey())) {
					entry2.getValue().addOrder(order);
				}
			}

		}
//		for(Entry<UUID,NormalUser> user:normalUsers.entrySet()) {
//			System.out.println(user.getValue().getOrders());
//		}

//		for(User user: users.get(UserType.normalUser)) {
//			NormalUser nuser= (NormalUser) user;
//			System.out.println(nuser);
//			System.out.println(nuser.getOrders());
//
//		}
		ScheduledExecutorService ses = Executors.newSingleThreadScheduledExecutor();
		ses.scheduleAtFixedRate(new Runnable() {
			@Override
			public synchronized void run() {
				System.out.println("Running JSON task");
				HashMap<UUID, LocalDate> res = DeliveryService.checkIfDelivered();
//				System.out.println(res);
				updateOrdersWithDeliveryDates(res);
			}
		}, 30, 5, TimeUnit.SECONDS);

	}
        
        public ArrayList<Movie> getMovies(){
            ArrayList<Movie> ret = new ArrayList<Movie>();
            
            for(Entry<UUID,Movie> entry:movies.entrySet()){
                ret.add(entry.getValue());
            }
            return ret;
        }
        
        
        public void findOverDue(){
        }

	@SuppressWarnings("unchecked")
	public synchronized void updateOrdersWithDeliveryDates(HashMap<UUID, LocalDate> input) {
		FileReader reader2;
		FileWriter writer;
		try {
			JSONParser jsonParser = new JSONParser();
			reader2 = new FileReader(System.getProperty("user.dir") + filepath + "userOrders.json");
			Object obj = jsonParser.parse(reader2);
			JSONArray userOrderList = (JSONArray) obj;
//			System.out.println(userOrderList);

			for (Object obj1 : userOrderList) {
				JSONObject ob = (JSONObject) obj1;
				UUID id = UUID.fromString(ob.get("orderID").toString());
				if (input.containsKey(id)) {
					ob.put("dateDelivered", input.get(id).toString());
					if (!ob.get("orderStatus").toString().equals("Delivered")) {
						ob.put("orderStatus", "Delivered");
						userOrders.get(id).setOrderStatus(OrderStatus.valueOf("Delivered"));
					}

				}
			}

			reader2.close();
			writer = new FileWriter(System.getProperty("user.dir") + filepath + "userOrders.json", false);
			writer.write(formatJSONStr(userOrderList.toJSONString(), 1));
			writer.flush();
			writer.close();

		} catch (IOException | ParseException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileReader reader3;
		FileWriter writer2;
		try {
			JSONParser jsonParser = new JSONParser();
			reader3 = new FileReader(System.getProperty("user.dir") + filepath + "phoneOrders.json");

			Object obj = jsonParser.parse(reader3);
			JSONArray phoneOrderList = (JSONArray) obj;
			for (Object obj1 : phoneOrderList) {
				JSONObject ob = (JSONObject) obj1;
				UUID id = UUID.fromString(ob.get("orderID").toString());
				if (input.containsKey(id)) {
					ob.put("dateDelivered", input.get(id).toString());
					if (!ob.get("orderStatus").toString().equals("Delivered")) {
						ob.put("orderStatus", "Delivered");
						phoneOrders.get(id).setOrderStatus(OrderStatus.valueOf("Delivered"));
					}

				}
			}
			writer2 = new FileWriter(System.getProperty("user.dir") + filepath + "phoneOrders.json", false);
			writer2.write(formatJSONStr(phoneOrderList.toJSONString(), 1));
			writer2.flush();
			writer2.close();
			reader3.close();

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ArrayList<AdminUser> getAdminUsers() {
		ArrayList<AdminUser> users = new ArrayList<AdminUser>();
		for (Entry<UUID, AdminUser> user : adminUsers.entrySet()) {
			users.add(user.getValue());
		}
		return users;
	}

	public HashMap<UUID, AdminUser> getAdminMap() {
		return adminUsers;
	}

	public HashMap<UUID, OperatorUser> getOperatorMap() {
		return operatorUsers;
	}

	public User login(String username, String password) {
		for (Entry<UUID, NormalUser> user : normalUsers.entrySet()) {
			if (user.getValue().getUsername().equals(username) && user.getValue().getPassword().equals(password)) {
				return user.getValue();
			}
		}
		for (Entry<UUID, OperatorUser> user : operatorUsers.entrySet()) {
			if (user.getValue().getUsername().equals(username) && user.getValue().getPassword().equals(password)) {
				return user.getValue();
			}
		}
		for (Entry<UUID, AdminUser> user : adminUsers.entrySet()) {
			if (user.getValue().getUsername().equals(username) && user.getValue().getPassword().equals(password)) {
				return user.getValue();
			}
		}

		return null;
	}

	public synchronized boolean removeNormalUser(UUID userID) {
		if (normalUsers.containsKey(userID)) {
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "normalUsers.json");
				Object obj = jsonParser.parse(reader);
				JSONArray normalUserList = (JSONArray) obj;
				int i = 0;
				for (Object obj1 : normalUserList) {
					JSONObject ob = (JSONObject) obj1;
					if (ob.get("userID").toString().equals(userID.toString())) {
						break;
					}
					i++;

				}
				normalUserList.remove(i);
				reader.close();
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "normalUsers.json",
						false);
				writer.write(formatJSONStr(normalUserList.toJSONString(), 1));
				writer.flush();
				writer.close();
				normalUsers.remove(userID);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean removeOperatorUser(UUID userID) {
		if (operatorUsers.containsKey(userID)) {
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "operatorUsers.json");
				Object obj = jsonParser.parse(reader);
				JSONArray operatorUserList = (JSONArray) obj;
				int i = 0;
				for (Object obj1 : operatorUserList) {
					JSONObject ob = (JSONObject) obj1;
					if (ob.get("userID").toString().equals(userID.toString())) {
						break;
					}
					i++;

				}
				operatorUserList.remove(i);
				reader.close();
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "operatorUsers.json",
						false);
				writer.write(formatJSONStr(operatorUserList.toJSONString(), 1));
				writer.flush();
				writer.close();
				operatorUsers.remove(userID);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	public synchronized boolean removeAdminUser(UUID userID) {
		if (adminUsers.containsKey(userID)) {
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "adminUsers.json");
				Object obj = jsonParser.parse(reader);
				JSONArray adminUserList = (JSONArray) obj;
				int i = 0;
				for (Object obj1 : adminUserList) {
					JSONObject ob = (JSONObject) obj1;
					if (ob.get("userID").toString().equals(userID.toString())) {
						break;
					}
					i++;

				}
				adminUserList.remove(i);
				reader.close();
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "adminUsers.json",
						false);
				writer.write(formatJSONStr(adminUserList.toJSONString(), 1));
				writer.flush();
				writer.close();
				adminUsers.remove(userID);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public synchronized boolean updateNormalUser(UUID userID, NormalUser user) {
		if (removeNormalUser(userID) && userID.equals(user.getUserID())) {
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "normalUsers.json");
				Object obj = jsonParser.parse(reader);
				JSONArray normalUserList = (JSONArray) obj;
				HashMap<String, Object> nUser = new HashMap<String, Object>();
				nUser.put("userID", user.getUserID().toString());
				nUser.put("username", user.getUsername());
				nUser.put("password", user.getPassword());
				nUser.put("email", user.getEmail());
				nUser.put("userType", UserType.normalUser.toString());
				nUser.put("loyaltyPoints", user.getLoyaltyPoints());
				nUser.put("allOrdersDelivered", Boolean.valueOf(user.isAllOrdersDelivered()).toString());
				JSONObject normalUser = new JSONObject(nUser);
				normalUserList.add(normalUser);
				reader.close();
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "normalUsers.json",
						false);
				writer.write(formatJSONStr(normalUserList.toJSONString(), 1));
				writer.flush();
				writer.close();
				normalUsers.put(userID, user);
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		} else {
			return false;
		}
	}

	public synchronized boolean cancelUserOrder(UUID orderID) {

		boolean isOrder = false;
		for (UUID id : userOrders.keySet()) {

			if (id.equals(orderID)) {
				isOrder = true;

			}
		}
		if (isOrder) {
			UserOrder order = userOrders.get(orderID);
			if (order.getOrderStatus() != OrderStatus.Delivered && order.getOrderStatus() != OrderStatus.Cancelled) {
				try {
					JSONParser jsonParser = new JSONParser();
					FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "userOrders.json");
					Object obj = jsonParser.parse(reader);
					JSONArray orderList = (JSONArray) obj;
					int i = 0;
					for (Object ob : orderList) {
						JSONObject obj1 = (JSONObject) ob;
						if (obj1.get("orderID").toString().equals(orderID.toString())) {
							break;
						}
						i++;
					}
					for (Movie movie : order.getMovies()) {
						changeMovieStock(movie.getId(), 1);
					}
					orderList.remove(i);
					reader.close();
					FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "userOrders.json",
							false);
					writer.write(formatJSONStr(orderList.toJSONString(), 1));
					writer.flush();
					normalUsers.get(userOrders.get(orderID).getUserID()).cancelOrder(orderID);;
					userOrders.remove(orderID);
					writer.close();
					DeliveryService.removeFromDeliveries(orderID);
					return true;

				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				return false;
			}
		}

		return false;

	}

	public synchronized boolean cancelPhoneOrder(UUID orderID) {

		boolean isOrder = false;
		for (UUID id : phoneOrders.keySet()) {

			if (id.equals(orderID)) {
				isOrder = true;

			}
		}
		if (isOrder) {
			UserOrder order = userOrders.get(orderID);
			if (order.getOrderStatus() != OrderStatus.Delivered) {
			
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "phoneOrders.json");
				Object obj = jsonParser.parse(reader);
				JSONArray orderList = (JSONArray) obj;
				int i = 0;
				for (Object ob : orderList) {
					JSONObject obj1 = (JSONObject) ob;
					if (obj1.get("orderID").toString().equals(orderID.toString())) {
						break;
					}
					i++;
				}
				for (Movie movie : order.getMovies()) {
					changeMovieStock(movie.getId(), 1);
				}
				orderList.remove(i);
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "phoneOrders.json",
						false);
				writer.write(formatJSONStr(orderList.toJSONString(), 1));
				writer.flush();
				userOrders.remove(orderID);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}

		return false;

	}

	@SuppressWarnings("unchecked")
	private synchronized void changeMovieStock(UUID id, int i) {
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "movies.json");
			Object obj = jsonParser.parse(reader);
			JSONArray movieList = (JSONArray) obj;
			for (Object ob : movieList) {
				JSONObject obj1 = (JSONObject) ob;
				int a = Integer.valueOf(obj1.get("stock").toString()) + i;
				if (obj1.get("id").toString().equals(id.toString())) {
					obj1.put("stock", a);
//					movies.get(id).setStock(a);
				}

			}
			FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "movies.json", false);
			writer.write(formatJSONStr(movieList.toJSONString(), 1));
			writer.flush();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public synchronized boolean addPhoneOrder(PhoneOrder order) {

		if (order.checkValidOrder()) {
			boolean isUnique = true;
			for (UUID id : phoneOrders.keySet()) {
				if (id.equals(order.getOrderID())) {
					isUnique = false;
				}
			}
			if (isUnique && order.getOrderStatus().equals(OrderStatus.Creating)) {
				order.setOrderStatus(OrderStatus.Processed);
				phoneOrders.put(order.getOrderID(), order);
				try {
					JSONParser jsonParser = new JSONParser();
					FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "phoneOrders.json");
					Object obj = jsonParser.parse(reader);
					JSONArray orderList = (JSONArray) obj;

					HashMap<String, Object> orderJSON = new HashMap<String, Object>();
					ArrayList<String> movieIds = new ArrayList<String>();
					for (Movie movie : order.getMovies()) {
						movieIds.add(movie.getId().toString());
						changeMovieStock(movie.getId(), -1);
					}
					orderJSON.put("movies", movieIds);
					orderJSON.put("orderID", order.getOrderID().toString());
					orderJSON.put("orderPrice", order.getOrderPrice());
					HashMap<String, String> payInfo = new HashMap<String, String>();
					payInfo.put("creditCardNumber", order.getPaymentInfo().getCreditCardNumber());
					payInfo.put("billingAddress", order.getPaymentInfo().getBillingAddress());

					orderJSON.put("paymentInfo", payInfo);
					orderJSON.put("operator", order.getOperatorID().toString());
					orderJSON.put("shippingAddress", order.getShippingAddress());
					orderJSON.put("orderDate", order.getOrderDate().toString());
					orderJSON.put("user", order.getOperatorID().toString());
					orderJSON.put("dateDelivered", order.getDateDelivered().toString());
					orderJSON.put("orderStatus", order.getOrderStatus().toString());
					JSONObject orderJSONM = new JSONObject(orderJSON);
					orderList.add(orderJSONM);
//					System.out.println(orderList);

					FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "phoneOrders.json",
							false);
					writer.write(formatJSONStr(orderList.toJSONString(), 1));
					writer.flush();
					writer.close();
					reader.close();
					DeliveryService.addNewDeliveryToWarehouse(order);

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JSONParser jsonParser = new JSONParser();
					FileReader reader2 = new FileReader(System.getProperty("user.dir") + filepath + "phoneOrders.json");
					Object obj = jsonParser.parse(reader2);
					JSONArray orderList = (JSONArray) obj;

					for (Object ob : orderList) {
						JSONObject obj1 = (JSONObject) ob;
						if (obj1.get("orderID").toString().equals(order.getOrderID().toString())) {
							obj1.put("orderStatus", OrderStatus.Delivering.toString());
						}
					}

//					System.out.println(orderList);

					FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "phoneOrders.json",
							false);
					writer.write(formatJSONStr(orderList.toJSONString(), 1));
					writer.flush();
					writer.close();
					reader2.close();
					return true;

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public synchronized boolean addUserOrder(UserOrder order) {
		if (order.checkValidOrder()) {
			boolean isUnique = true;
			for (UUID id : userOrders.keySet()) {
				if (id.equals(order.getOrderID())) {
					isUnique = false;
				}
			}
			if (isUnique && order.getOrderStatus().equals(OrderStatus.Creating)) {
				order.setOrderStatus(OrderStatus.Processed);
				userOrders.put(order.getOrderID(), order);
				try {
					JSONParser jsonParser = new JSONParser();
					FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "userOrders.json");
					Object obj = jsonParser.parse(reader);
					JSONArray orderList = (JSONArray) obj;

					HashMap<String, Object> orderJSON = new HashMap<String, Object>();
					ArrayList<String> movieIds = new ArrayList<String>();
					for (Movie movie : order.getMovies()) {
						movieIds.add(movie.getId().toString());
						changeMovieStock(movie.getId(), -1);
					}
					orderJSON.put("movies", movieIds);
					orderJSON.put("orderID", order.getOrderID().toString());
					orderJSON.put("orderPrice", order.getOrderPrice());
					HashMap<String, String> payInfo = new HashMap<String, String>();
					payInfo.put("creditCardNumber", order.getPaymentInfo().getCreditCardNumber());
					payInfo.put("billingAddress", order.getPaymentInfo().getBillingAddress());

					orderJSON.put("paymentInfo", payInfo);
					orderJSON.put("shippingAddress", order.getShippingAddress());
					orderJSON.put("orderDate", order.getOrderDate().toString());
					orderJSON.put("user", order.getUserID().toString());
					orderJSON.put("payWithPoints", order.isPayWithPoints().toString());
//					System.out.println(LocalDate.MIN);
					orderJSON.put("dateDelivered", order.getDateDelivered().toString());

					orderJSON.put("orderStatus", order.getOrderStatus().toString());
					JSONObject orderJSONM = new JSONObject(orderJSON);
//				System.out.println(orderJSONM);
					orderList.add(orderJSONM);

					FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "userOrders.json",
							false);
					writer.write(formatJSONStr(orderList.toJSONString(), 1));
					writer.flush();
                                        if(PaymentService.handleNormalUserOrder(order)){
                                            DeliveryService.addNewDeliveryToWarehouse(order);
                                        }else{
                                            return false;
                                        }
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					JSONParser jsonParser = new JSONParser();
					FileReader reader2 = new FileReader(System.getProperty("user.dir") + filepath + "userOrders.json");
					Object obj = jsonParser.parse(reader2);
					JSONArray orderList = (JSONArray) obj;

					for (Object ob : orderList) {
						JSONObject obj1 = (JSONObject) ob;
						if (obj1.get("orderID").toString().equals(order.getOrderID().toString())) {
							obj1.put("orderStatus", OrderStatus.Delivering.toString());
						}
					}
					userOrders.get(order.getOrderID()).setOrderStatus(OrderStatus.Delivering);
//					System.out.println(orderList);
					FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "userOrders.json",
							false);
					writer.write(formatJSONStr(orderList.toJSONString(), 1));
					writer.flush();
					writer.close();
					reader2.close();
					return true;

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		return false;

	}

	private PhoneOrder parsePhoneOrder(JSONObject phoneOrder) throws Exception {
		PaymentInfo info = parsePaymentInfo((JSONObject) phoneOrder.get("paymentInfo"));
		JSONArray movieIds = (JSONArray) phoneOrder.get("movies");
		UUID orderID = UUID.fromString((String) phoneOrder.get("orderID"));
		OrderStatus orderStatus = OrderStatus.valueOf((String) phoneOrder.get("orderStatus"));
		String shippingAddress = (String) phoneOrder.get("shippingAddress");
		UUID operatorID = UUID.fromString((String) phoneOrder.get("operator"));
		LocalDate orderDate = LocalDate.parse((String) phoneOrder.get("orderDate"));
		String dateDeliveredString = (String) phoneOrder.get("dateDelivered");
		LocalDate dateDelivered;
		dateDelivered = LocalDate.parse(dateDeliveredString);
		OperatorUser nuser = null;
		for (Entry<UUID, OperatorUser> user : operatorUsers.entrySet()) {
			if (user.getKey().equals(operatorID)) {
				nuser = user.getValue();
			}
		}
		PhoneOrder order = null;
		if (nuser != null) {
			order = new PhoneOrder(orderID, nuser);
		} else {
			throw new Exception("No Such USER!" + operatorID.toString());
		}
		order.setPaymentInfo(info);
		order.setShippingAddress(shippingAddress);
		order.setOrderDate(orderDate);
		order.setOrderStatus(orderStatus);
		order.setDateDelivered(dateDelivered);

		for (Object movie : movieIds) {
			order.addToOrder(movies.get(UUID.fromString(movie.toString())));
		}

		return order;
	}

	private UserOrder parseUserOrder(JSONObject userOrder) throws Exception {
		PaymentInfo info = parsePaymentInfo((JSONObject) userOrder.get("paymentInfo"));
		JSONArray movieIds = (JSONArray) userOrder.get("movies");
		UUID orderID = UUID.fromString((String) userOrder.get("orderID"));
		OrderStatus orderStatus = OrderStatus.valueOf((String) userOrder.get("orderStatus"));
		String shippingAddress = (String) userOrder.get("shippingAddress");
		UUID userID = UUID.fromString((String) userOrder.get("user"));
		Boolean payWithPoints = Boolean.valueOf((String) userOrder.get("payWithPoints"));
		LocalDate orderDate = LocalDate.parse((String) userOrder.get("orderDate"));
		String dateDeliveredString = (String) userOrder.get("dateDelivered");
		LocalDate dateDelivered;
		dateDelivered = LocalDate.parse(dateDeliveredString);

		NormalUser nuser = null;
		for (Entry<UUID, NormalUser> user : normalUsers.entrySet()) {
			if (user.getKey().equals(userID)) {
				nuser = user.getValue();
			}
		}
		UserOrder order = null;
		if (nuser != null) {
			order = new UserOrder(orderID, nuser);
		} else {
			throw new Exception("No Such USER!" + userID.toString());
		}
		order.setPaymentInfo(info);
		order.setShippingAddress(shippingAddress);
		order.setPayWithPoints(payWithPoints);
		order.setOrderDate(orderDate);
		order.setOrderStatus(orderStatus);
		order.setDateDelivered(dateDelivered);

		for (Object movie : movieIds) {
			order.addToOrder(movies.get(UUID.fromString(movie.toString())));
		}

		return order;
	}

	private PaymentInfo parsePaymentInfo(JSONObject paymentInfo) {
		String creditCardNumber = (String) paymentInfo.get("creditCardNumber");
		String billingAddress = (String) paymentInfo.get("billingAddress");
		return new PaymentInfo(creditCardNumber, billingAddress);

	}

	private Movie parseMovieObject(JSONObject movie) {
		MovieInfo info = parseMovieInfo((JSONObject) movie.get("movieInfo"));
		String title = (String) movie.get("title");
		Integer stock = Integer.valueOf(movie.get("stock").toString());
		String uuid = (String) movie.get("id");
		UUID id = UUID.fromString(uuid);
		double price = Double.valueOf(movie.get("price").toString());
		return new Movie(title, stock, id, price, info);

	}

	private MovieInfo parseMovieInfo(JSONObject movieInfo) {
		ArrayList<String> actors = new ArrayList<String>();
		ArrayList<String> directors = new ArrayList<String>();
		ArrayList<Integer> ratings = new ArrayList<Integer>();
		JSONArray actorsArr = (JSONArray) movieInfo.get("actors");
		JSONArray directorsArr = (JSONArray) movieInfo.get("directors");
		JSONArray ratingsArr = (JSONArray) movieInfo.get("ratings");
		String description = (String) movieInfo.get("description");
		String gen = (String) movieInfo.get("genre");
		String releaseYear = (String) movieInfo.get("releaseYear");
		Genre genre = Genre.valueOf(gen);
		for (Object ob : actorsArr) {
			actors.add(ob.toString());
		}
		for (Object ob : directorsArr) {
			directors.add(ob.toString());
		}
		for (Object ob : ratingsArr) {
			ratings.add(Integer.valueOf(ob.toString()));
		}

		return new MovieInfo(actors, directors, description, releaseYear, genre, ratings);

	}

	@SuppressWarnings("unchecked")
	public boolean addMovie(Movie movie) {
		if (movies.containsKey(movie.getId())) {
			return false;
		} else {
			movies.put(movie.getId(), movie);
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "movies.json");
				Object obj = jsonParser.parse(reader);
				JSONArray movieList = (JSONArray) obj;
				ArrayList<JSONObject> arr = new ArrayList<JSONObject>();
				for (Object ob : movieList) {
					arr.add((JSONObject) ob);
				}

				HashMap<String, Object> movieJSON = new HashMap<String, Object>();
				movieJSON.put("title", movie.getTitle());
				movieJSON.put("stock", movie.getStock());
				movieJSON.put("price", movie.getPrice());
				movieJSON.put("id", movie.getId().toString());
				HashMap<String, Object> movieInfoJson = new HashMap<String, Object>();
				movieInfoJson.put("actors", movie.getMovieInfo().getActors());
				movieInfoJson.put("directors", movie.getMovieInfo().getDirectors());
				movieInfoJson.put("description", movie.getMovieInfo().getDescription());
				movieInfoJson.put("genre", movie.getMovieInfo().getGenre().toString());
				movieInfoJson.put("releaseYear", movie.getMovieInfo().getReleaseYear());
				movieInfoJson.put("ratings", movie.getMovieInfo().getRatings());

				movieJSON.put("movieInfo", movieInfoJson);

				JSONObject movieJSONM = new JSONObject(movieJSON);

				reader.close();
				arr.add(movieJSONM);
//				JSONArray movieListFinal= new JSONArray();
//				for(Object ob: arr) {
//					movieListFinal.add(ob);
//				}
				movieList.add(movieJSONM);
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "movies.json", false);

				writer.write(formatJSONStr(movieList.toJSONString(), 1));
				writer.flush();
				writer.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		}

	}

	public synchronized boolean removeMovie(Movie movie) {
		if (movies.containsKey(movie.getId())) {
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "movies.json");
				Object obj = jsonParser.parse(reader);
				JSONArray movieList = (JSONArray) obj;
				int i = 0;
				for (Object ob : movieList) {
					JSONObject jMovie = (JSONObject) ob;
					if (jMovie.get("id").toString().equals(movie.getId().toString())) {
						break;
					}
					i++;

				}
				movieList.remove(i);
				movies.remove(movie.getId());
				reader.close();
//				JSONArray movieListFinal= new JSONArray();
//				for(Object ob: arr) {
//					movieListFinal.add(ob);
//				}
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "movies.json", false);

				writer.write(formatJSONStr(movieList.toJSONString(), 1));
				writer.flush();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		} else {
			return false;
		}
	}

	public boolean removeMovie(UUID movieId) {
		if (movies.containsKey(movieId)) {
			Movie movie = movies.get(movieId);
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "movies.json");
				Object obj = jsonParser.parse(reader);
				JSONArray movieList = (JSONArray) obj;
				int i = 0;
				for (Object ob : movieList) {
					JSONObject jMovie = (JSONObject) ob;
					if (jMovie.get("id").toString().equals(movie.getId().toString())) {
						break;
					}
					i++;

				}
				movieList.remove(i);
				movies.remove(movie.getId());
				reader.close();
//				JSONArray movieListFinal= new JSONArray();
//				for(Object ob: arr) {
//					movieListFinal.add(ob);
//				}
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "movies.json", false);

				writer.write(formatJSONStr(movieList.toJSONString(), 1));
				writer.flush();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return true;
		} else {
			return false;
		}
	}

	public HashMap<UUID, NormalUser> getNormalUsersMap() {
		return normalUsers;
	}

	public HashMap<UserType, ArrayList<? extends User>> getUsers() {
		HashMap<UserType, ArrayList<? extends User>> clone = new HashMap<UserType, ArrayList<? extends User>>();
		ArrayList<AdminUser> adminUsers1 = new ArrayList<AdminUser>();
		ArrayList<NormalUser> normalUsers1 = new ArrayList<NormalUser>();
		ArrayList<OperatorUser> operatorUsers1 = new ArrayList<OperatorUser>();

		for (Entry<UUID, AdminUser> user : adminUsers.entrySet()) {
			adminUsers1.add(user.getValue());
		}

		for (Entry<UUID, NormalUser> user : normalUsers.entrySet()) {
			normalUsers1.add(user.getValue());
		}
		for (Entry<UUID, OperatorUser> user : operatorUsers.entrySet()) {
			operatorUsers1.add(user.getValue());
		}

		clone.put(UserType.adminUser, adminUsers1);
		clone.put(UserType.normalUser, normalUsers1);
		clone.put(UserType.operatorUser, operatorUsers1);

		return clone;

	}

	public ArrayList<NormalUser> getNormalUsers() {
		ArrayList<NormalUser> nr = new ArrayList<NormalUser>();
		for (Entry<UUID, NormalUser> user : normalUsers.entrySet()) {
			nr.add(user.getValue());
		}
		return nr;
	}

	public static synchronized SystemV getInstance() {
		if (INSTANCE == null) {
			synchronized (SystemV.class) {
				if (INSTANCE == null) {
					INSTANCE = new SystemV();
					return INSTANCE;
				}
			}
		}
		return INSTANCE;
	}
        
        public ArrayList<OperatorUser> getOperatorUsers() {
		ArrayList<OperatorUser> nr = new ArrayList<OperatorUser>();
		for (Entry<UUID, OperatorUser> user : operatorUsers.entrySet()) {
			nr.add(user.getValue());
		}
		return nr;
	}


	public User validateUser(String username, String password) {
                User res = null;
                for(NormalUser user: this.getNormalUsers()){
                    if(user.getPassword().equals(password) && user.getUsername().equals(username)){
                        res=user;
                        return res;
                    }
                }
                  for(OperatorUser user: this.getOperatorUsers()){
                    if(user.getPassword().equals(password) && user.getUsername().equals(username)){
                        res=user;
                        return res;
                    }
                }
                for(AdminUser user: this.getAdminUsers()){
                    if(user.getPassword().equals(password) && user.getUsername().equals(username)){
                        res=user;
                        return res;
                    }
                }
                  
                
		return res;

	}

	public Movie getMovie(UUID id) {
		for (UUID id1 : movies.keySet()) {
			if (id1.equals(id)) {
				return movies.get(id);
			}
		}
		return null;
	}

	public ArrayList<PhoneOrder> getPhoneOrders() {
		ArrayList<PhoneOrder> orders = new ArrayList<PhoneOrder>();
		for (Entry<UUID, PhoneOrder> order : phoneOrders.entrySet()) {
			orders.add(order.getValue());
		}

		return orders;
	}

	public ArrayList<UserOrder> getUserOrders() {
		ArrayList<UserOrder> orders = new ArrayList<UserOrder>();
		for (Entry<UUID, UserOrder> order : userOrders.entrySet()) {
			orders.add(order.getValue());
		}

		return orders;
	}

	public boolean registerUser(User register) {
		HashMap<UserType, ArrayList<? extends User>> users = getUsers();
		for (Entry<UserType, ArrayList<? extends User>> entry : users.entrySet()) {
			ArrayList<? extends User> userL = entry.getValue();
			for (User user : userL) {
				if (user.getUsername().equals(register.getUsername()) || user.getUserID().equals(register.getUserID())
						|| user.getEmail().equals(register.getEmail())) {
					return false;
				}
			}

		}
		switch (register.getUsertype()) {
		case adminUser:
			return registerAdmin((AdminUser) register);
		case normalUser:
			return registerNormal((NormalUser) register);
		case operatorUser:
			return registerOperator((OperatorUser) register);

		}
		return false;

	}

	public boolean registerAdmin(AdminUser user) {
		if (!adminUsers.containsKey(user.getUserID())) {
			ArrayList<AdminUser> adminUsersL = getAdminUsers();
			for (AdminUser admin : adminUsersL) {
				if (admin.getUsername().equals(user.getUsername()) || admin.getEmail().equals(user.getEmail())) {
					return false;
				}
			}
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "adminUsers.json");
				Object obj = jsonParser.parse(reader);
				JSONArray adminUserList = (JSONArray) obj;
				HashMap<String, Object> aUser = new HashMap<String, Object>();
				aUser.put("userID", user.getUserID().toString());
				aUser.put("username", user.getUsername());
				aUser.put("password", user.getPassword());
				aUser.put("email", user.getEmail());
				aUser.put("userType", UserType.adminUser.toString());
				JSONObject adminUser = new JSONObject(aUser);
				adminUserList.add(adminUser);
				reader.close();
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "adminUsers.json",
						false);
				adminUsers.put(user.getUserID(), user);
				writer.write(formatJSONStr(adminUserList.toJSONString(), 1));
				writer.flush();
				writer.close();

			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		} else {
			return false;
		}
	}

	public boolean registerNormal(NormalUser user) {
		if (!normalUsers.containsKey(user.getUserID())) {
			ArrayList<NormalUser> normalUsersL = getNormalUsers();
			for (NormalUser nuser : normalUsersL) {
				if (user.getUsername().equals(nuser.getUsername()) || user.getEmail().equals(nuser.getEmail())) {
					return false;
				}
			}
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "normalUsers.json");
				Object obj = jsonParser.parse(reader);
				JSONArray normalUserList = (JSONArray) obj;
				HashMap<String, Object> User = new HashMap<String, Object>();
				User.put("userID", user.getUserID().toString());
				User.put("username", user.getUsername());
				User.put("password", user.getPassword());
				User.put("email", user.getEmail());
				User.put("userType", UserType.normalUser.toString());
				User.put("loyaltyPoints", user.getLoyaltyPoints());
				User.put("allOrdersDelivered", Boolean.valueOf(user.isAllOrdersDelivered()).toString());
				JSONObject normalUser = new JSONObject(User);
				normalUserList.add(normalUser);
				reader.close();
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "normalUsers.json",
						false);
				normalUsers.put(user.getUserID(), user);
				writer.write(formatJSONStr(normalUserList.toJSONString(), 1));
				writer.flush();
				writer.close();

			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		} else {
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public boolean registerOperator(OperatorUser user) {
		if (!operatorUsers.containsKey(user.getUserID())) {
			ArrayList<OperatorUser> operatorUsersL = getOperatorUsers();
			for (OperatorUser nuser : operatorUsersL) {
				if (user.getUsername().equals(nuser.getUsername()) || user.getEmail().equals(nuser.getEmail())) {
					return false;
				}
			}
			try {
				JSONParser jsonParser = new JSONParser();
				FileReader reader = new FileReader(System.getProperty("user.dir") + filepath + "operatorUsers.json");
				Object obj = jsonParser.parse(reader);
				JSONArray operatorUserList = (JSONArray) obj;
				HashMap<String, Object> User = new HashMap<String, Object>();
				User.put("userID", user.getUserID().toString());
				User.put("username", user.getUsername());
				User.put("password", user.getPassword());
				User.put("email", user.getEmail());
				User.put("userType", UserType.operatorUser.toString());
				JSONObject operatorUser = new JSONObject(User);
				operatorUserList.add(operatorUser);
				reader.close();
				FileWriter writer = new FileWriter(System.getProperty("user.dir") + filepath + "operatorUsers.json",
						false);
				operatorUsers.put(user.getUserID(), user);
				writer.write(formatJSONStr(operatorUserList.toJSONString(), 1));
				writer.flush();
				writer.close();

			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;

		} else {
			return false;
		}
	}

	public void addAdmin(String username, String password, UUID id, String adminEmail) {
		registerUser(new AdminUser(id, password, username, adminEmail));
	}

//	public boolean addMovie()

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		SystemV s = new SystemV();
//		UserOrder order= new UserOrder(UUID.fromString("4d56b74b-3e96-45c2-b4bd-41351537c806"),s.getNormalUsersMap().get(UUID.fromString("724a0ac3-2485-4f12-bd72-b81a137af28f")));
//		order.addToOrder(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae"));
//		order.setPayWithPoints(false);
//		PaymentInfo payInfo= new PaymentInfo("4716697359224219","123 Hello World Way");
//		order.setPaymentInfo(payInfo);
//		order.setShippingAddress("123 Hello World Way");
//		order.placeOrder();
//		HashMap<UUID, LocalDate> res ;
//		
//		for(int i=0; i<20;i++) {
//			res = DeliveryService.checkIfDelivered();
//			s.updateOrdersWithDeliveryDates(res);
//		}

//		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//		System.out.println(threadSet);

//		ArrayList<PhoneOrder> pOrders = s.getPhoneOrders();
//		ArrayList<UserOrder> uOrders = s.getUserOrders();
//
//		HashMap<UserType, ArrayList<? extends User>> users = s.getUsers();
//		System.out.println(users.get(UserType.operatorUser));
//		for(UUID id: s.movies.keySet()) {
//			System.out.println(s.movies.get(id).getMovieInfo());
//		}
//		NormalUser user= new NormalUser(UUID.randomUUID(),"user244","245","mynewemail@gmail.com");
//		MovieInfo info = new MovieInfo();
//		String[] actors = { "actor25", "actor26", "actor27" };
//		String[] directors = { "director25", "director26", "director27" };
//		Integer[] ratings = { 1, 2, 3, 4, 2, 1, 0, 5 };
//		Genre genre = Genre.Action;
//		String releaseYear = "2012";
//		String description = "Good Movie :D";
//		info.setActors(new ArrayList<String>(Arrays.asList(actors)));
//		info.setDirectors(new ArrayList<String>(Arrays.asList(directors)));
//		info.setRatings(new ArrayList<Integer>(Arrays.asList(ratings)));
//		info.setDescription(description);
//		info.setGenre(genre);
//		info.setReleaseYear(releaseYear);
//		Movie movie = new Movie("Greatest Movie Ever", 15, UUID.fromString("ee97b06c-55e5-4948-b9f6-2c7fa93a3ec1"),
//				500.6, info);
//		s.addMovie(movie);
//		UserOrder order1 = new UserOrder(UUID.fromString("231f4a86-d5e1-4a2d-83d5-8452de08d6d6"),
//				s.getNormalUsersMap().get(UUID.fromString("04fb603c-135c-4fa7-b842-e9226e23a4fb")));
//		PaymentInfo pinfo = new PaymentInfo("3528508800545179", "250 New House Way");
//		order1.setPaymentInfo(pinfo);
//		order1.setPayWithPoints(false);
//		order1.addToOrder(UUID.fromString("93d9f951-1999-4b82-8947-e6454a6e5339"));
//		order1.addToOrder(UUID.fromString("ee97b06c-55e5-4948-b9f6-2c7fa93a3ec1"));
//		order1.setShippingAddress("250 New House Way");
//		order1.setPayWithPoints(false);
//		order1.placeOrder();

//		PhoneOrder order2 = new PhoneOrder(UUID.fromString("41f5a03a-90a7-4997-ac2d-82df96a45ea5"),
//				s.operatorUsers.get(UUID.fromString("a8fec7a1-62b6-4863-8706-9cba413b0e0a")));
//		pinfo.setCreditCardNumber("4929802016653908");
//		pinfo.setBillingAddress("134 Manorville way");
//		order2.setPaymentInfo(pinfo);
//		order2.addToOrder(UUID.fromString("1ddf7695-03f8-40c8-856f-1f84846bc6ae"));
//		order2.addToOrder(UUID.fromString("ee97b06c-55e5-4948-b9f6-2c7fa93a3ec1"));
//		order2.setShippingAddress("134 Manorville way");
//		order2.placeOrder();
//		s.cancelUserOrder(UUID.fromString("f6aab151-f2d7-4fcc-9fc6-4fc6d638c5eb"));
//		java.lang.System.out.println(s.registerUser(user));
	}

//				System s=new System();
//				try {
//			FileWriter csvWriter = new FileWriter(java.lang.System.getProperty("user.dir")+filepath +"users.csv");
//			csvWriter.append("UserID");
//			csvWriter.append(", ");
//			csvWriter.append("Username");
//			csvWriter.append(", ");
//			csvWriter.append("Password");
//			csvWriter.append(", ");
//			csvWriter.append("Email");
//			csvWriter.append(", ");
//			csvWriter.append("UserType");
//			csvWriter.append(", ");
//			csvWriter.append('\n');
//			
//			String s1=			UUID.randomUUID().toString();
//			csvWriter.append(s1);
//			csvWriter.append(", ");
//			csvWriter.append("kjha95");
//			csvWriter.append(", ");
//			csvWriter.append("123456");
//			csvWriter.append(", ");
//			csvWriter.append("jha.kumarv@gmail.com");
//			csvWriter.append(", ");
//			csvWriter.append("adminUser");
//			csvWriter.append(", ");
//			csvWriter.append('\n');
//		
//			
//			 s1=			UUID.randomUUID().toString();
//			csvWriter.append(s1);
//			csvWriter.append(", ");
//			csvWriter.append("Baller24");
//			csvWriter.append(", ");
//			csvWriter.append("123456");
//			csvWriter.append(", ");
//			csvWriter.append("jha.baller@gmail.com");
//			csvWriter.append(", ");
//			csvWriter.append("normalUser");
//			csvWriter.append(", ");
//			csvWriter.append('\n');
//		
//			
//			 s1= UUID.randomUUID().toString();
//			csvWriter.append(s1);
//			csvWriter.append(", ");
//			csvWriter.append("Kobe");
//			csvWriter.append(", ");
//			csvWriter.append("123456");
//			csvWriter.append(", ");
//			csvWriter.append("nba2424@gmail.com");
//			csvWriter.append(", ");
//			csvWriter.append("operatorUser");
//			csvWriter.append(", ");
//			csvWriter.append('\n');
//		
//			
//		
//			
//			csvWriter.flush();
//			csvWriter.close();
//		
//		
//		
//		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		

}

//}
