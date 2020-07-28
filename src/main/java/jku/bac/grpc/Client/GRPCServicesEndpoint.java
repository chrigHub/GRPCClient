package jku.bac.grpc.Client;

import jku.bac.grpc.Client.entity.Item;
import jku.bac.grpc.Client.grpcClient.ClientCartClient;
import jku.bac.grpc.Client.grpcClient.ClientOfferingClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GRPCServicesEndpoint {
    ClientOfferingClient clientOfferingClient;
    ClientCartClient clientCartClient;
    List<Item> itemList;

    public GRPCServicesEndpoint(ClientOfferingClient clientOfferingClient, ClientCartClient clientCartClient) {
        this.clientOfferingClient = clientOfferingClient;
        this.clientCartClient = clientCartClient;
        this.itemList = new ArrayList<>();
    }

    @GetMapping("/offering")
    public String getOffering(){
        this.itemList = new ArrayList<>();
        List<Item> items = clientOfferingClient.getOffering();
        System.out.println("Client received!");
        StringBuilder stringBuilder = new StringBuilder();
        for(Item item : items){
            this.itemList.add(item);
            stringBuilder.append("ID: "+ item.getId() +" Label: "+ item.getLabel() +" Prize: "+ item.getPrize() +" Amount: "+ item.getAmount() + " || ");
        }
        return stringBuilder.toString();
    }

    @GetMapping("/toCart")
    public String sendToCart(@RequestParam String label, @RequestParam int amount){
        for(Item item : this.itemList){
            if(item.getLabel().equals(label)){
                clientCartClient.toCart(item, amount);
            }
        }
        return "toCart done!";
    }

    @GetMapping("/checkout")
    public String checkout(){
        clientCartClient.checkout();
        return "checkout done!";
    }
}
