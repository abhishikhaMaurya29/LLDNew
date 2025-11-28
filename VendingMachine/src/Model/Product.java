package Model;

public class Product {
    private final String name;
    private final String code;
    private final Money money;

    public Product(String name, String code, Money money) {
        this.name = name;
        this.code = code;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Money getMoney() {
        return money;
    }
}
