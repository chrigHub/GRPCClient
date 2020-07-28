package jku.bac.grpc.Client.grpcClient;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jku.bac.grpc.Client.entity.Item;
import jku.bac.grpc.Client.util.Util;
import jku.bac.grpc.server.grpcserver.ClientOfferingServiceGrpc;
import jku.bac.grpc.server.grpcserver.ClientRequest;
import jku.bac.grpc.server.grpcserver.OfferingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientOfferingClient {
    public static final int NR_OF_ENTRIES = 10;

    //Get from Offering-Service
    public List<Item> getOffering() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9091)
                .usePlaintext()
                .build();

        ClientOfferingServiceGrpc.ClientOfferingServiceBlockingStub stub = ClientOfferingServiceGrpc.newBlockingStub(channel);
        OfferingResponse offeringResponse = stub.getOffering(ClientRequest.newBuilder().setNrOfItems(NR_OF_ENTRIES).build());
        channel.shutdown();

        return Util.transferItemToItem(offeringResponse.getTransferItemsList());
    }

}
