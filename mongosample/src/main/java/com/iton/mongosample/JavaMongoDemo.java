package com.iton.mongosample;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
public class JavaMongoDemo {

	private MongoClient mongoClient;
	private DB db;
public JavaMongoDemo() {
	 mongoClient = new MongoClient("localhost",27017);
	  db = mongoClient.getDB("javatpointdb");
}

	public void mongodemo() {
try {
		
//		List l = mongoClient.getCredentialsList();
//		System.out.println("l size is "+l.size());
//		for(int i=0;i<l.size();i++) {
//			System.out.println(l.get(i));
//		}
		String connectPoint  =  mongoClient.getConnectPoint();
		System.out.println("connect point "+ connectPoint);
		
		List<String> dbs = mongoClient.getDatabaseNames();
		for(String dbss : dbs)
		{
			System.out.println(dbss);
		}

		
		Set<String> set = db.getCollectionNames();
		for(String collectionNames : set) {
			System.out.println("collection names are "+ collectionNames);
		}
		DBCollection dbCollection =db.getCollection("javatpoint");
		DBCursor cursor = dbCollection.find();
		while(cursor.hasNext()) {
			System.out.println(""+cursor.next());
		}
//		BasicDBObject dbObject = new BasicDBObject();
//		dbObject.put("name", "car");
//		dbObject.put("brand", "benz");
//		dbObject.put("model", "xyz");
//		DBCollection col = db.createCollection("cars", dbObject);
//		col.insert(dbObject);
		

		
}catch(Exception e) {
	e.printStackTrace();
}
	}
	
	
	public void addFields(User user) {
		try {

		DBCollection table = db.getCollection("user");
		BasicDBObject doc = new BasicDBObject();
		doc.put("name", user.getName());
		doc.put("salary", user.getSalary());
		table.insert(doc);
		
		}catch(Exception e) {
			
		}
	}
	 
	public ArrayList<User> getUserCollection(){
		ArrayList<String> list = new ArrayList();
		ArrayList<User> userCollection = new ArrayList<User>();
		String x;
		try {
		
	
		DBCollection table = db.getCollection("user");
		DBCursor cursor = table.find();
		int z = 0;
		while(cursor.hasNext()) {
//			x=null;
//			int y = ++z;
//			User user = new User();
//			System.out.println(y+"first");
//			System.out.println(cursor.next()+"");
//			System.out.println(cursor.next().getClass());
//			x = cursor.next()+"";
//			System.out.println(y+" second");
//			System.out.println("yyyyy "+x);
//			System.out.println(y+" third");
//			JSONObject js = new JSONObject(x);
//			x="";
//			user.setName(js.getString("name"));
//			user.setSalary(js.getString("salary"));
			list.add(cursor.next()+"");
		}
		
//			table.find().forEach();
		for(String jo:list) {
			User user = new User();
			JSONObject js = new JSONObject(jo);
			System.out.println(jo);
			JSONObject js1 = js.getJSONObject("_id");
			
            user.setId(js1.getString("$oid"));
			user.setName(js.getString("name"));
			user.setSalary(js.getString("salary"));
			userCollection.add(user);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userCollection;
	}
	
	
	public void updateDocument(User newValue,String user,String salary) {
		DBCollection table = db.getCollection("user");
		
		BasicDBObject newDoc = new BasicDBObject() ;
		newDoc.append("$set", new BasicDBObject().append("name", newValue.getName()));
		
		BasicDBObject searchQuery = new BasicDBObject().append("name",user);
		table.update(searchQuery, newDoc);
		BasicDBObject newDoc2 = new BasicDBObject() ;
		
		newDoc2.append("$set", new BasicDBObject().append("salary", newValue.getSalary()));
		BasicDBObject searchQuery2 = new BasicDBObject().append("salary",salary);
		table.update(searchQuery2, newDoc2);
	}
	
	public void deleteDocument(String name) {
DBCollection table = db.getCollection("user");
		
	System.out.println(name);
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", name);
		table.remove(searchQuery);
	}
}
