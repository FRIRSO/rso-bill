package si.fri.rso.projekt.bill.services.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kumuluz.ee.discovery.annotations.DiscoverService;
//import si.fri.rso.projekt.buyers.models.Buyer;
import si.fri.rso.projekt.bill.services.configuration.AppProperties;
import si.fri.rso.projekt.bill.models.MongoBill;
import si.fri.rso.projekt.bill.models.Bill;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ApplicationScoped
public class BillBean {

    private Logger log = Logger.getLogger(BillBean.class.getName());

    private Client httpClient;

    private ObjectMapper objectMapper;

    @Inject
    private AppProperties appProperties;

    @Inject
    @DiscoverService("rso-bill")
    private Optional<String> url;

    @Inject
    @DiscoverService("rso-buyer")
    private Optional<String> containerUrl;

    @PostConstruct
    private void init() {
        httpClient = ClientBuilder.newClient();
        objectMapper = new ObjectMapper();
    }

    public String readConfig() {
        if (appProperties.isExternalServicesEnabled())
            return "ext service enabled!";
        return "ext service disabled";
    }


    public void setConfig(boolean config) {
        appProperties.setExternalServicesEnabled(config);
    }


    //private List<Buyer> getObjects(String json) throws IOException {
    //    return json == null ? new ArrayList<>() : objectMapper.readValue(json,
    //            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).getTypeFactory().constructCollectionType(List.class, Buyer.class));
    //}
    /*public List<Buyer> getMessageDiscovery(){
        if(url.isPresent()) {
            try {
                return httpClient
                        .target(url.get() + "/v1/buyers")
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Buyer>>() {
                        });
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("errror: " + url.get() + "\t " + e.getMessage());
                //throw new InternalServerErrorException(e.getMessage());
                return null;
            }
        }
        return null;
    }*/


    public String getMessageDiscovery2(){
        if(containerUrl.isPresent()) {
            try {
                return httpClient
                        .target(containerUrl.get() + "/v1/buyers/test")
                        .request()
                        .get(String.class);
            }
            catch (WebApplicationException | ProcessingException e) {
                System.out.println("errror: " + containerUrl.get() + "\t " + e.getMessage());
                return "Sth went wrong!";
            }
        }
        System.out.println("errror: sth went wring!");
        return "Sth went wrong!";
    }

    public List<Bill> getBills() {
        MongoBill mb = new MongoBill();

        return mb.getAllBills();
    }

    public Bill getBill(Integer orderID) {
        MongoBill mb = new MongoBill();

        Bill bill = mb.getBill(orderID);

        if(orderID == null) {
            return null;
        }

        return bill;
    }

    public boolean getPaidStatus(Integer orderID) {
        Bill bill = getBill(orderID);

        if(bill == null) {
            return false;
        }

        return bill.isPaid();
    }

    public void updatePaid(Integer orderID) {
        MongoBill mb = new MongoBill();

        mb.updatePaidStatus(orderID);
    }
}
