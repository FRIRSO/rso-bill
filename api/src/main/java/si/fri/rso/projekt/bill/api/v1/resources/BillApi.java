package si.fri.rso.projekt.bill.api.v1.resources;

import si.fri.rso.projekt.bill.services.beans.BillBean;
import si.fri.rso.projekt.bill.models.Bill;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("bills")
public class BillApi {

    @Inject
    private BillBean billBean;


    //@GET
    //@Path("url")
    //public Response test() {
    //    return Response.status(Response.Status.OK).entity(billBean.getMessageDiscovery()).build();
    //
    //}

    @GET
    @Path("url2")
    public Response test2() {
        return Response.status(Response.Status.OK).entity(billBean.getMessageDiscovery2()).build();

    }

    @GET
    @Path("service")
    public Response service() {
        return Response.status(Response.Status.OK).entity(billBean.readConfig()).build();
    }

    @GET
    @Path("disable")
    public Response test4() {
        billBean.setConfig(false);
        String response = "OK";
        return Response.status(Response.Status.OK).entity(response).build();
    }

    @GET
    @Path("enable")
    public Response test5() {
        billBean.setConfig(true);
        String response = "OK";
        return Response.status(Response.Status.OK).entity(response).build();
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        return Response.ok(billBean.getBills()).build();
    }

    @GET
    @Path("/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrdersbyID(@PathParam("orderID") Integer orderID) {
        Bill bill = billBean.getBill(orderID);

        if(bill != null) {
            return Response.ok(bill).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/paid/{orderID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPaidStatus(@PathParam("orderID") Integer orderID) {
        boolean isPaid = billBean.getPaidStatus(orderID);

        return Response.ok(isPaid).build();
    }
}
