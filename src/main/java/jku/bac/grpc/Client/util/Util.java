package jku.bac.grpc.Client.util;

import jku.bac.grpc.Client.entity.Item;
import jku.bac.grpc.server.grpcserver.TransferItem;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Item> transferItemToItem(List<TransferItem> transferItemList){
        List<Item> itemList = new ArrayList<>();
        for(TransferItem transferItem : transferItemList){
            Item item = new Item(
                    transferItem.getId(),
                    transferItem.getLabel(),
                    transferItem.getPrize(),
                    transferItem.getAmount()
            );
            itemList.add(item);
        }
        return itemList;
    }
}
