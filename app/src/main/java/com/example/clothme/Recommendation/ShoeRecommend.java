package com.example.clothme.Recommendation;
import java.util.Objects;
public class ShoeRecommend {
    public String shoesSuggest(String occasion, String gender) {
        String shoes = null;
        if (Objects.equals(gender, "Male")) {
            switch (occasion) {
                case "religious event":
                case "Wedding":
                case "cocktail party":
                case "job interview":
                case "business dinner":
                    shoes = "Formal Shoes";
                    break;
                case "casual/travel":
                case "picnic":
                case "barbeque":
                    shoes = "Flip Flops, Sneakers or Sandals";
                    break;
                case "beach party":
                    shoes="Flip Flops";
                    break;
                case "dinner party":
                case "Smart Casual":
                    shoes = "Sneakers or Formal Shoes";
                    break;
                case "sports":
                    shoes = "Sports Shoes";
                    break;
            }
        } else if (Objects.equals(gender, "Female")) {
            switch (occasion) {
                case "religious event":
                    shoes = "Sandals";
                    break;
                case "Wedding":
                case "job interview":
                case "business dinner":
                    shoes = "Formal Shoes";
                    break;
                case "cocktail party":
                    shoes = "Sandals or Boots";
                    break;
                case "casual/travel":
                case "picnic":
                case "barbeque":
                    shoes = "Flip Flops, Sneakers or Sandals";
                    break;
                case "beach party":
                    shoes="Flip Flops";
                    break;
                case "dinner party":
                case "Smart Casual":
                    shoes = "Sneakers or Sandals";
                    break;
                case "sports":
                    shoes = "Sports Shoes";
                    break;
            }
        }
        return shoes;
    }
}
