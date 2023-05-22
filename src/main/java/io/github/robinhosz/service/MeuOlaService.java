package io.github.robinhosz.service;

import io.github.robinhosz.proto.MutinyOlaServiceGrpc;
import io.github.robinhosz.proto.OlaRequest;
import io.github.robinhosz.proto.OlaResponse;

import io.github.robinhosz.proto.OlaServiceGrpc;
import io.grpc.stub.StreamObserver;
import io.smallrye.mutiny.Uni;

import javax.inject.Singleton;
import java.util.concurrent.atomic.AtomicInteger;
/*
@Singleton
public class MeuOlaService extends OlaServiceGrpc.OlaServiceImplBase {

    //FORMA SEM SER REATIVO
    @Override
     public void digaOla(OlaRequest request, StreamObserver<OlaResponse> responseObserver) {
         String nome = request.getNome();

         responseObserver.onNext(
             OlaResponse.newBuilder()
             .setMensagem("Ola "+nome)
             .setQuantidadeDeChamadas(5).build());
         responseObserver.onCompleted();
     }


}*/

//Trabalhando com Mutiny, Programação reativa

@Singleton
public class MeuOlaService extends MutinyOlaServiceGrpc.OlaServiceImplBase {

    AtomicInteger inteiro = new AtomicInteger();

    @Override
    public Uni<OlaResponse> digaOla(OlaRequest request) {
        OlaResponse response = OlaResponse.newBuilder()
                .setMensagem("Ola2 " + request.getNome())
                .setQuantidadeDeChamadas(inteiro.getAndIncrement()).build();
        return Uni.createFrom().item(response);
    }

}