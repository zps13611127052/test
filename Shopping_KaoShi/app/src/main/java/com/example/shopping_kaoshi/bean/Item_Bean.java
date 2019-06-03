package com.example.shopping_kaoshi.bean;

/**
 * Created by 张十八 on 2019/5/24.
 */

public class Item_Bean {
    private int id;
    private String name;
    private int price;
    private boolean istrue;
    private int count;

    public Item_Bean() {
    }

    public Item_Bean(int id, String name, int price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Item_Bean(int id, String name, int price, boolean istrue, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.istrue = istrue;
        this.count = count;
    }


    @Override
    public String toString() {
        return "Item_Bean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", istrue=" + istrue +
                ", count=" + count +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isIstrue() {
        return istrue;
    }

    public void setIstrue(boolean istrue) {
        this.istrue = istrue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
