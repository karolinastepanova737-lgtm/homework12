import java.util.Arrays;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        // 1. Тестируем класс Product
        Product p1 = new Product(101, "Смартфон Galaxy", 55000, "Электроника");
        Product p2 = new Product(101, "Смартфон Galaxy (Уценка)", 45000, "Электроника");
        Product p3 = new Product(102, "Наушники Air", 12000, "Аксессуары");
        Product p4 = new Product(101, "Смартфон", 55000, null);

        System.out.println("=== Товары ===");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

        System.out.println("\n=== Сравнение товаров ===");
        System.out.println("p1 и p2 (одинаковые id и category): " + p1.equals(p2)); // true
        System.out.println("p1 и p3 (разные id и category): " + p1.equals(p3));    // false
        System.out.println("p1 и p4 (category = null): " + p1.equals(p4));        // false

        // 2. Тестируем класс Order
        Product[] basket1 = {p1, p3};
        Product[] basket2 = {p2, p3}; // p1.equals(p2) == true, порядок совпадает
        Product[] basket3 = {p3, p1}; // порядок отличается

        Order order1 = new Order("Иван Иванов", basket1);
        Order order2 = new Order("Иван Иванов", basket2);
        Order order3 = new Order("Иван Иванов", basket3);
        Order order4 = new Order("Пётр Петров", basket1);

        System.out.println("\n=== Заказы ===");
        System.out.println(order1);
        System.out.println(order2);
        System.out.println(order3);
        System.out.println(order4);

        System.out.println("\n=== Сравнение заказов ===");
        System.out.println("order1 и order2 (одинаковые покупатель и состав): " + order1.equals(order2)); // true
        System.out.println("order1 и order3 (нарушен порядок товаров): " + order1.equals(order3));         // false
        System.out.println("order1 и order4 (разные покупатели): " + order1.equals(order4));              // false
    }
}

class Product {
    private int id;
    private String name;
    private int price;
    private String category;

    public Product(int id, String name, int price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    @Override
    public String toString() {
        return "Товар[артикул=" + id + ", название=" + name + ", цена=" + price + ", категория=" + category + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }
}

class Order {
    private String customer;
    private Product[] basket;

    public Order(String customer, Product[] basket) {
        this.customer = customer;
        this.basket = basket;
    }

    @Override
    public String toString() {
        return "Заказ[покупатель=" + customer + ", корзина=" + Arrays.toString(basket) + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;

        if (!Objects.equals(customer, order.customer)) {
            return false;
        }

        if (basket == order.basket) {
            return true;
        }
        if (basket == null || order.basket == null || basket.length != order.basket.length) {
            return false;
        }

        boolean isBasketsEqual = true;
        for (int i = 0; i < basket.length; i++) {
            Product p1 = basket[i];
            Product p2 = order.basket[i];

            if (p1 == null) {
                if (p2 != null) {
                    isBasketsEqual = false;
                    break;
                }
            } else if (!p1.equals(p2)) {
                isBasketsEqual = false;
                break;
            }
        }

        return isBasketsEqual;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(customer);
        result = 31 * result + Arrays.hashCode(basket);
        return result;
    }
}
