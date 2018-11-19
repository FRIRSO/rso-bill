package si.fri.rso.projekt.bill.api.v1.resources;

import si.fri.rso.projekt.bill.services.beans.BillBean;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("bill")
public class BillApi {

    @Inject
    private BillBean billBean;

    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello from kumuluze. its working!";
    }

    @GET
    @Path("discovery")
    public Response disc() {

        String returnMsg = billBean.getMessage();
        return Response.status(Response.Status.OK).entity(returnMsg).build();
    }

    @GET
    @Path("url")
    public Response test() {
        String response = billBean.getMessageDiscovery();
        return Response.status(Response.Status.OK).entity(response).build();

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
}
