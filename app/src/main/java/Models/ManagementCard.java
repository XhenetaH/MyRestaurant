package Models;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class ManagementCard {
    private Context context;


    public ManagementCard(Context context) {
        this.context = context;
    }

   /* public void insertFood(MenuItemDetails item) {
        boolean existAlready = false;
        int n = 0;
        for (int i = 0; i < MyBag.menuItemDetails.size(); i++) {
            if (MyBag.menuItemDetails.get(i).getID().equals(item.getID())) {
                existAlready = true;
                n = i;
                break;
            }
        }

        if (existAlready) {
            MyBag.menuItemDetails.get(n).setQuantity(item.getQuantity()+MyBag.menuItemDetails.get(n).getQuantity());
        } else {
            MyBag.menuItemDetails.add(item);
        }

        Toast.makeText((), "Added To Your Card", Toast.LENGTH_SHORT).show();

    }*/
    public void plusNumberFood(ArrayList<MenuItemDetails> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listfood.get(position).setQuantity(listfood.get(position).getQuantity() + 1);
        changeNumberItemsListener.changed();
    }

    public void MinusNumerFood(ArrayList<MenuItemDetails> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getQuantity() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setQuantity(listfood.get(position).getQuantity() - 1);
        }
        changeNumberItemsListener.changed();
    }
    public Double getTotalFee() {
        ArrayList<MenuItemDetails> listFood2 = MyBag.menuItemDetails;
        double fee = 0;
        for (int i = 0; i < listFood2.size(); i++) {
            fee = fee + (listFood2.get(i).getTotalPrice() * listFood2.get(i).getQuantity());
        }
        return fee;
    }
}
