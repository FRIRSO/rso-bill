package si.fri.rso.projekt.bill.models;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoBill {


    private final String DBUser       = "root";
    private final String DBPassword   = "13tpxnxJTwUScc3V";
    private final String DBName       = "db-bill";
    private final String DBCollection = "bills";

    private MongoClient connectDB() {
        MongoClientURI uri = new MongoClientURI("mongodb://"+ DBUser +":"+ DBPassword +"@gsascluster-shard-00-00-ocnkx.azure.mongodb.net:27017," +
                "gsascluster-shard-00-01-ocnkx.azure.mongodb.net:27017,gsascluster-shard-00-02-ocnkx.azure.mongodb.net:27017/test?" +
                "ssl=true&replicaSet=gsasCluster-shard-0&authSource=admin&retryWrites=true");

        return new MongoClient(uri);
    }

    public List<Bill> getAllBills() {
        MongoClient client = connectDB();

        MongoDatabase db = client.getDatabase(DBName);

        MongoCollection<Document> queueCollection = db.getCollection(DBCollection);

        List<Bill> results = new ArrayList<>();

        for(Document curr : queueCollection.find()) {

            Bill bill = new Bill(curr.getInteger("orderID"),
                                    curr.getInteger("menuID"),
                                    curr.getDouble("price"),
                                    curr.getString("date"),
                                    curr.getBoolean("paid"));

            results.add(bill);
        }

        return results;
    }

    public Bill getBill(Integer orderID) {
        MongoClient client = connectDB();
        MongoDatabase db = client.getDatabase(DBName);
        MongoCollection<Document> bc = db.getCollection(DBCollection);

        Bson filter = Filters.eq("orderID", orderID);

        Document result = bc.find(filter).first();

        if(result == null) {
            return null;
        }


        return new Bill(result.getInteger("orderID"),
                result.getInteger("menuID"),
                result.getDouble("price"),
                result.getString("date"),
                result.getBoolean("paid"));
    }

    public void updatePaidStatus(Integer orderID) {
        MongoClient client = connectDB();
        MongoDatabase db = client.getDatabase(DBName);
        MongoCollection<Document> bc = db.getCollection(DBCollection);

//        Bill bill =  getBill(orderID);


        BasicDBObject doc = new BasicDBObject();
        doc.append("$set", new BasicDBObject().append("paid", true));

        BasicDBObject filter = new BasicDBObject().append("orderID", orderID);

        bc.updateOne(filter, doc);
    }
}
