package ar.edu.itba.it.paw.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class API {
	
	private static HashMap<Integer, Hotel> hotel_list = null;
	private static HashMap<String, User> user_list = null;
	
	private static boolean messages_added = false;
	
	private static int next_hotel_id = 0;
	
	public static void createBogusDatabase(){
		createBogusHotels();
		createBogusUsers();
		createBogusMessages();
	}
	
	public static void createBogusHotels(){
		if(hotel_list == null){
			hotel_list = new HashMap<Integer, Hotel>();
			
			addHotel("jaja", "jojojo");
			addHotel("Tribilin Resort", "Es feo");
			addHotel("nananana", "batman");
		}
	}
	
	public static void createBogusUsers(){
		if (user_list == null){
			user_list = new HashMap<String, User>();
			
			user_list.put("Pedro", new User.Builder().email("asd@aa.com").password("asd").build());
//			user_list.put("Carlos", new User("Carlos", "123qwe", "carlos@yo.com"));
//			user_list.put("Tribilin", new User("Tribilin", "123qwe", "no@se.com"));
		}
	}
	
	public static void createBogusMessages(){
		if (!messages_added){
			addCommentToHotel(2, "Pedro", "Feo");
			addCommentToHotel(2, "Carlos", "Sep");
			addCommentToHotel(1, "Tribilin", "JAJAJAJA");
			addCommentToHotel(3, "Pedro", "REKT");
			addCommentToHotel(2, "Carlos", "T_T");
			messages_added = true;
		}
	}
	
	public static ArrayList<Hotel> getAllHotels(){
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		
		for(Hotel h : hotel_list.values()){
			hotels.add(h);
		}
		
		return hotels;
	}
	
	public static Hotel getHotelById(int id){
		return hotel_list.get(id);
	}
	
	public static ArrayList<Comment> getCommentsById(int id){
		return hotel_list.get(id).getComments();
	}
	
	public static void addCommentToHotel(int id, String username, String message){
		Hotel hotel = getHotelById(id);
		User user = getUserByName(username);
		
		if ( hotel != null && user != null){
			hotel.addComment(user, message);
		}
	}
	
	public static void editHotel(int id, String name, String description){
		Hotel hotel = getHotelById(id);
		
		if (hotel != null){
			hotel.setName(name);
			hotel.setDescription(description);
		}
	}
	
	public static void addHotel(String name, String description){
		hotel_list.put(next_hotel_id, new Hotel(next_hotel_id, name, description));
		next_hotel_id++;
	}
	
	public static boolean authenticateUser(String email, String password){
		boolean found = false;
		boolean failed = false;
		
		if (email == null || password == null) {
			return false;
		}
		
		for(User u : user_list.values()){
			if (!failed && !found){
				if(u.getEmail().compareTo(email) == 0){
					found = true;
					if(u.getPassword().compareTo(password) != 0){
						failed = true;
					}
				}
			}
		}
		return found && !failed;
	}
	
	public static User getUserByName(String username){
		return user_list.get(username);
	}
}
