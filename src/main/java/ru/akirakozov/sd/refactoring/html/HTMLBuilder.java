package ru.akirakozov.sd.refactoring.html;

import ru.akirakozov.sd.refactoring.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public class HTMLBuilder {
    private static final String EOL = System.lineSeparator();

    public static String bodyView(String body) {
        return "<html><body>" + EOL + body + EOL + "</body></html>";
    }

    public static String singleProductView(Product p) {
        return p.name + "\t" + p.price + "</br>";
    }

    public static String productsView(List<Product> products) {
        return products.stream().map(HTMLBuilder::singleProductView).collect(Collectors.joining(EOL));
    }

    public static final String maxPriceDescription = "<h1>Product with max price: </h1>" + EOL;
    public static String minPriceDescription = "<h1>Product with min price: </h1>" + EOL;
    public static String sumPriceDescription = "Summary price: " + EOL;
    public static String productsCountedDescription = "Number of products: " + EOL;
}
