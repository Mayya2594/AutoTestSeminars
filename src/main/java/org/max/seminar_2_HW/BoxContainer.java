package org.max.seminar_2_HW;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BoxContainer implements PackageComponent{

    public List<PackageComponent> childComponents = new ArrayList<>();

    public void addToBox(PackageComponent packageComponent) {
        childComponents.add(packageComponent);
    }

    public void removeFromBox(int index) {
        childComponents.remove(index);
    }

    public List<PackageComponent> getChildren() {
        return childComponents;
    }

    @Override
    public int countPrice() {
        AtomicInteger result = new AtomicInteger();
        childComponents.forEach(v -> {
            result.addAndGet(v.countPrice());
        });
        return result.get();
    }
}
