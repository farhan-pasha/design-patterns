package main.java.solid_principles;
//Open for extension, closed for modification

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

enum Color {
    RED, GREEN, BLUE
}
enum Size {
    SHORT, MEDIUM, LONG
}
enum Type {
    TSHIRT, PANT, BELT
}

class Product {
    public String name;
    public Color color;
    public Size size;
    public Type type;

    public Product(String name, Color color, Size size, Type type) {
        this.name = name;
        this.color = color;
        this.size = size;
        this.type = type;
    }
}

class ProductFilter {

    public Stream<Product> filterByColor(List<Product> products, Color color) {
        return products.stream().filter(p -> p.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products, Size size) {
        return products.stream().filter(p -> p.size == size);
    }

    public Stream<Product> filterByColorAndSize(List<Product> products, Size size, Color color) {
        return products.stream().filter(p -> p.color == color && p.size == size);
    }
}

interface Specification<T> {
    boolean isSatisfied(T item);
}

interface filter<T> {
    Stream<T> filter(List<T> item, Specification<T> spec);
}

class ColorSpecification implements Specification<Product> {
    private Color color;
    ColorSpecification(Color color) {
        this.color = color;
    }
    @Override
    public boolean isSatisfied(Product p) {
        return p.color == color;
    }
}

class SizeSpecification implements Specification<Product>{

    private Size size;
    SizeSpecification(Size size){
        this.size = size;
    }
    @Override
    public boolean isSatisfied(Product p){
        return size == p.size;
    }
}

class TypeSpecification implements Specification<Product>{

    private Type type;
    TypeSpecification(Type type){
        this.type = type;
    }
    @Override
    public boolean isSatisfied(Product p){
        return type == p.type;
    }
}

class AndSpecification implements Specification<Product> {
    Specification spec1, spec2;

    AndSpecification(Specification spec1, Specification spec2) {
        this.spec1 = spec1;
        this.spec2 = spec2;
    }

    @Override
    public boolean isSatisfied(Product p) {
        return spec1.isSatisfied(p) && spec2.isSatisfied(p);
    }
}

class MultiSpecification implements Specification <Product> {
    Specification specs[];
    MultiSpecification(Specification ... specs){
        this.specs = specs;
    }

    @Override
    public boolean isSatisfied(Product p) {

        for(Specification specification : specs){
            if (!specification.isSatisfied(p)){
                return false;
            }
        }

        return true;
    }
}


class BetterFilter implements filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> products, Specification<Product> spec){
        return products.stream().filter(p -> spec.isSatisfied(p));
    }
}

class OpenClosedPrinciple {

    public static void main(String[] args) {
        Product p1 = new Product("P1", Color.BLUE, Size.SHORT, Type.PANT);
        Product p2 = new Product("P2", Color.RED, Size.SHORT,Type.BELT);
        Product p3 = new Product("P3", Color.BLUE, Size.LONG,Type.BELT);
        Product p4 = new Product("P4", Color.GREEN, Size.SHORT,Type.BELT);
        Product p5 = new Product("P5", Color.BLUE, Size.SHORT,Type.BELT);

        List<Product> products = new ArrayList<Product>(Arrays.asList(p1,p2,p3,p4,p5));
        //List<Product> products = List.of(p1,p2,p3,p4,p5); //Java 9

        ProductFilter pf = new ProductFilter();
        pf.filterByColor(products, Color.BLUE).forEach(p -> System.out.println("Filter By Color Blue - " + p.name));
        /*  If I need a new filter, suppose filterByColor I will need to modify ProductFilter class which is already
            written and published. This will break the OCP as already written class(software entities) should'nt be
            modified but should be open for extension.
        */

        pf.filterBySize(products, Size.SHORT).forEach(p -> {System.out.println("Filter By Size SHORT - " + p.name);});
        pf.filterByColorAndSize(products, Size.SHORT, Color.RED).forEach(p -> {
            System.out.println("Filter By Size SHORT and Color RED- " + p.name);
        });

        //The number of filter will rise based on the attributes of the products, NOT A GOOD DESIGN!
        //Lets try to mitigate the above issue using a better design pattern, specification design pattern
        System.out.println("\nBetter Solution ....");

        BetterFilter bf = new BetterFilter();
        bf.filter(products,new ColorSpecification(Color.BLUE)).forEach(p -> System.out.println("BetterFilter By Color Blue - " + p.name));
        bf.filter(products,new SizeSpecification(Size.SHORT)).forEach(p -> System.out.println("BetterFilter By Size Short - " + p.name));
        bf.filter(products,new AndSpecification(
                new ColorSpecification(Color.RED),
                new SizeSpecification(Size.SHORT)
        )).forEach(p -> System.out.println("BetterFilter(AndSpecification) By Size SHORT and Color RED- " + p.name));
        bf.filter(products,new MultiSpecification(
                new ColorSpecification(Color.RED),
                new SizeSpecification(Size.SHORT)
        )).forEach(p -> System.out.println("BetterFilter(MultiSpecification) By Size SHORT and Color RED- " + p.name));
        bf.filter(products,new MultiSpecification(
                new ColorSpecification(Color.BLUE),
                new SizeSpecification(Size.SHORT),
                new TypeSpecification(Type.BELT)
        )).forEach(p -> System.out.println("BetterFilter(MultiSpecification) By Size SHORT, Color RED and Type Belt- " + p.name));
    }
}