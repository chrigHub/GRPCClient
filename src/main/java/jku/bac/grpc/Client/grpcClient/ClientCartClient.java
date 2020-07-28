package jku.bac.grpc.Client.grpcClient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import jku.bac.grpc.Client.entity.Item;
import jku.bac.grpc.server.grpcserver.ClcaItem;
import jku.bac.grpc.server.grpcserver.ClientCartServiceGrpc;
import jku.bac.grpc.server.grpcserver.Empty;
import jku.bac.grpc.server.grpcserver.Notification;
import org.springframework.stereotype.Service;

@Service
public class ClientCartClient {

    private static ManagedChannel channel;

    StreamObserver<Empty> toCartObserver = new StreamObserver<Empty>() {
        @Override
        public void onNext(Empty empty) {
            System.out.println("toCart onNext");
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("toCart throws Error");
            channel.shutdown();
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
            System.out.println("toCart finished");
            channel.shutdown();
        }
    };

    StreamObserver<Empty> checkoutObserver = new StreamObserver<Empty>() {
        @Override
        public void onNext(Empty empty) {
            System.out.println("checkout onNext");
        }

        @Override
        public void onError(Throwable throwable) {
            System.out.println("checkout throws Error");
            channel.shutdown();
            throwable.printStackTrace();
        }

        @Override
        public void onCompleted() {
            System.out.println("checkout finished");
            channel.shutdown();
        }
    };

    public void toCart(Item item, int amount){
        System.out.println("Starting toCart method!");
        item.setAmount(amount);
        ClcaItem clcaItem = ClcaItem
                .newBuilder()
                .setId(item.getId())
                .setLabel(item.getLabel())
                .setPrize(item.getPrize())
                .setAmount(amount)
                .build();
        this.channel = initChannel();
        ClientCartServiceGrpc.ClientCartServiceStub stub = ClientCartServiceGrpc.newStub(channel);
        stub.toCart(clcaItem, toCartObserver);
    }

    public void checkout(){
        System.out.println("Stating checkout method!");
        channel = initChannel();
        ClientCartServiceGrpc.ClientCartServiceStub stub = ClientCartServiceGrpc.newStub(channel);
        Notification notification = Notification.newBuilder()
                .setName("checkout")
                .build();
        stub.checkout(notification, checkoutObserver);
    }

    private static ManagedChannel initChannel(){
        return ManagedChannelBuilder.forAddress("localhost", 9092)
                .usePlaintext()
                .build();
    }
}
