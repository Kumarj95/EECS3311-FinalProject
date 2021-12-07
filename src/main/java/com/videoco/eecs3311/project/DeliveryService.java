package com.videoco.eecs3311.project;
import org.json.simple.JSONArray;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
public class DeliveryService {
	private final static String filepath=System.getProperty("user.dir")+ "/src/main/java/com/videoco/eecs3311/project/data/DeliveryDates.json";
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
	                if (Character.isWhitespace(c)) continue;
	            }
	        }

	        ret += c + (c == '\\' ? "" + chars[++i] : "");
	    }

	    return ret;
	}

	@SuppressWarnings("unchecked")
	public synchronized static void addNewDeliveryToWarehouse(Order order) {
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader1 = new FileReader(filepath);
			Object obj = jsonParser.parse(reader1);
			JSONArray orders= (JSONArray) obj;
			boolean newOrder=true;
			for(Object ob: orders) {
				JSONObject object= (JSONObject) ob;
				if(object.get("orderID").toString().equals(order.getOrderID().toString())) {
					newOrder=false;
				}
			}
			if(newOrder && order.getOrderStatus().equals(OrderStatus.Processed)) {
				HashMap<String,Object> map= new HashMap<String,Object>();
				map.put("orderID", order.getOrderID().toString());
				int deliveryTime= ((int)(Math.random()*5))+1;
				map.put("dateDelivered", order.getOrderDate().plusDays(deliveryTime).toString());
				JSONObject jsonMap= new JSONObject(map);
				orders.add(jsonMap);
				reader1.close();
				FileWriter writer= new FileWriter(filepath,false);
				writer.write(formatJSONStr(orders.toJSONString(),1));
				writer.flush();
				writer.close();
			}
			reader1.close();
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	
	public synchronized static HashMap<UUID,LocalDate> checkIfDelivered() {
		HashMap<UUID,LocalDate> result= new HashMap<UUID,LocalDate>();
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader4 = new FileReader(filepath);
			Object obj = jsonParser.parse(reader4);
			JSONArray orders= (JSONArray) obj;
			for(Object ob:orders) {
				JSONObject ob1= (JSONObject) ob;
				LocalDate now= LocalDate.now();
				LocalDate deliveryDate=LocalDate.parse(ob1.get("dateDelivered").toString());
				if(now.isAfter(deliveryDate) || now.isEqual(deliveryDate)) {
					result.put(UUID.fromString(ob1.get("orderID").toString()), deliveryDate);
				}
			}
			reader4.close();
			
			for(UUID key: result.keySet()) {
				removeFromDeliveries(key);
			}

			

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public synchronized static void removeFromDeliveries(UUID id) {
		try {
			JSONParser jsonParser = new JSONParser();
			FileReader reader6 = new FileReader(filepath);
			Object obj = jsonParser.parse(reader6);
			JSONArray orders= (JSONArray) obj;
			int i=0;
			for(Object ob:orders) {
				JSONObject json= (JSONObject) ob;
				if(json.get("orderID").toString().equals(id.toString())) {
					break;
				}
				i++;
			}
			if(orders.size()>=1)
				orders.remove(i);
			FileWriter writer= new FileWriter(filepath,false);
			writer.write(formatJSONStr(orders.toJSONString(),1));
			writer.flush();
			writer.close();
			reader6.close();
			

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
