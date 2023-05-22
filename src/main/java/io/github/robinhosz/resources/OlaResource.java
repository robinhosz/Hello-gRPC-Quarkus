package io.github.robinhosz.resources;

import io.github.robinhosz.proto.MutinyOlaServiceGrpc;
import io.github.robinhosz.proto.OlaResponse;
import io.github.robinhosz.proto.OlaServiceGrpc;
import io.quarkus.grpc.runtime.annotations.GrpcService;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.security.SecureRandom;

@Path("/ola")
public class OlaResource {


    @Inject
    @GrpcService("ola-service")
    OlaServiceGrpc.OlaServiceBlockingStub olaServiceBlockingStub;


    @Inject
    @GrpcService("ola-service")
    MutinyOlaServiceGrpc.MutinyOlaServiceStub mutinyOlaServiceStub;


    @GET
    @Path("/block/{nome}")
    public String getBlock(@PathParam("nome") String nome) {
        io.github.robinhosz.proto.OlaRequest olaRequest = io.github.robinhosz.proto.OlaRequest.newBuilder().setNome(nome).build();
        io.github.robinhosz.proto.OlaResponse response = olaServiceBlockingStub.digaOla(olaRequest);
        return response.getMensagem() + ", Quantidade " + response.getQuantidadeDeChamadas();
    }

    @GET
    @Path("/reativo/{nome}")
    public String getReativo(@PathParam("nome") String nome) {
        io.github.robinhosz.proto.OlaRequest olaRequest = io.github.robinhosz.proto.OlaRequest.newBuilder().setNome(nome).build();
        Uni<OlaResponse> response = mutinyOlaServiceStub.digaOla(olaRequest);
        return response.onItem().apply(i -> i.getMensagem() + ", Quantidade " + i.getQuantidadeDeChamadas()).toString();
    }
}
