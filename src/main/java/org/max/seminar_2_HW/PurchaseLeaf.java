package org.max.seminar_2_HW;

public class PurchaseLeaf implements PackageComponent{

    private int price;

    public PurchaseLeaf(int price) {
        this.price = price;
    }

    @Override
    public int countPrice() {
        return price;
    }
}
