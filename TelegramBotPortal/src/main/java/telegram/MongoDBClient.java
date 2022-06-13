package telegram;

import com.google.gson.Gson;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bson.BsonDocument;
import org.bson.BsonRegularExpression;
import org.bson.Document;


public class MongoDBClient {

	
			   private final static String HOST = "localhost";
			   private final static int PORT = 27017;

			   public final static String DATABASE = "telegram";
			   public final static String COLLECTION = "Chat_Form";

			   public MongoClient mongoClient() {
			      return new MongoClient(new ServerAddress(HOST, PORT));
			   }

			   public void create(Candidate c) {
			      MongoClient mongoClient = new
			         MongoClient(new ServerAddress(HOST, PORT));
			      MongoCollection<Document> collection =
			         mongoClient.getDatabase(DATABASE).getCollection(COLLECTION);
			      if  (c!=null) {

			         Document d = new Document().append("id", c.getId())
			            .append("skillSet", c.getSkillSet())
			            .append("name", c.getName())
			            .append("email", c.getEmail())
			            .append("phone", c.getPhone())
			            .append("gender", c.getGender())
			            .append("lastDegree", c.getLastDegree()
			            .append("lastDesig", c.getLastDesig())
			            .append("expInYearMonth", c.getExpInYearMonth());
			         collection.insertOne(d);
			      }


			   }

			   public void update(Candidate c) {
			      MongoClient mongoClient = new
			         MongoClient(new ServerAddress(HOST, PORT));
			      MongoCollection<Document> collection =
			         mongoClient.getDatabase(DATABASE).getCollection(COLLECTION);
			      Document d = new Document();
			      d.append("id", c.getId())
			         .append("skillSet", c.getSkillSet())
			         .append("name", c.getName())
			         .append("email", c.getEmail())
			         .append("phone", c.getPhone())
			         .append("gender", c.getGender())
			         .append("lastDegree", c.getLastDegree())
			         .append("lastDesig", c.getLastDesig())
			         .append("expInYearMonth", c.getExpInYearMonth());
			      collection.updateOne(new Document("id", c.getId()),
			         new Document("$set", d));
			   }

			   public void delete(Candidate c) {
			      MongoClient mongoClient = new
			         MongoClient(new ServerAddress(HOST, PORT));
			      MongoCollection<Document> collection =
			         mongoClient.getDatabase(DATABASE).getCollection(COLLECTION);
			      collection.deleteOne(new Document("id", c.getId()));
			   }

			   public List<Candidate> find(String filter) {
			      List<Candidate> list = new ArrayList<>();
			      System.out.println("hello");
			      MongoClient mongoClient =
			         new MongoClient(new ServerAddress(HOST, PORT));
			      MongoCollection<Document> collection =
			         mongoClient.getDatabase(DATABASE).getCollection(COLLECTION);
			      FindIterable<Document> iter;
			      if (filter == null || filter.trim().length() == 0) {
			         iter = collection.find();
			      } else {

			         BsonRegularExpression bsonRegex = new
			            BsonRegularExpression(filter);
			         BsonDocument bsonDoc = new BsonDocument();
			         bsonDoc.put("skillSet", bsonRegex);
			         iter = collection.find(bsonDoc);

			      }
			      iter.forEach(new Block<Document>() {
			         @Override
			         public void apply(Document doc) {
			            list.add(new Gson().fromJson(doc.toJson(), Candidate.class));
			         }
			      });
			      return list;
			   }
	
}
