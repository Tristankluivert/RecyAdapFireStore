package com.hybrid.recyadapfirestore;

public class DealModel {
    public String name, price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String image;

    public DealModel() {
    }

    public DealModel(String name, String price, String image) {
        this.name = name;
        this.price = price;
      //  this.dimage = dimage;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }



    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
