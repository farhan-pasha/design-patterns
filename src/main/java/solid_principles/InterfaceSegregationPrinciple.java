package main.java.solid_principles;
/*
    -You shouldn't put into your interface more than what the client is expected to implement.
    -YAGNI = You ain't gonna need it
    -A recommendation of how to split interface into smaller interface.
    -Design patten used: Decorator
 */

import javax.print.Doc;

interface Machine {
    void print(Document D);
    void fax(Document D);
    void scan(Document D);
}

class Document {
}

class MultiFunctionPrinter implements Machine {

    @Override
    public void print(Document D) {

    }

    @Override
    public void fax(Document D) {

    }

    @Override
    public void scan(Document D) {

    }
}

/*
    Suppose we also have an old fashioned Printer which can only print! If we implement Machine interface,
    how to implement the fax/scan methods?
    option1: implement Machine and keep the fax/scan methods empty - This can confuse the caller
    option2: throw exception, but then we will need to change the method signature
    eg: public void fax(Document D) throws Exception
    Plus, we will need to propagate this change up to interface
    eg: In interface Machine:-  void fax(Document D) throws Exception;
    What happens if we do not own the source code?
*/


class oldFashionPrinter implements Machine {
    @Override
    public void print(Document D) {

    }

    @Override
    public void fax(Document D) {
        //??
    }

    @Override
    public void scan(Document D) {
        //??
    }
}

/*
    Better Design:
    By breaking the interfaces into smaller interfaces
    option1: implement multiple interfaces whenever needed (see PhotoCopier class)
    option2: create an interface which extends multiple interfaces
    option3: Use Decorator design pattern
 */



interface Printer {
    void print (Document D);
}

interface Scanner {
    void scan (Document D);
}

class SimplePrinter implements Printer{

    @Override
    public void print(Document D) {

    }
}

class PhotoCopier implements Printer, Scanner {

    @Override
    public void print(Document D) {

    }

    @Override
    public void scan(Document D) {

    }
}

interface MultiFunctionalMachine extends Printer, Scanner {

}

class MultiFunctionalDevice implements MultiFunctionalMachine {

    @Override
    public void print(Document D) {

    }

    @Override
    public void scan(Document D) {

    }
}

//Decorator Design Pattern
public class InterfaceSegregationPrinciple implements MultiFunctionalMachine{

    Printer printer;
    Scanner scanner;

    public InterfaceSegregationPrinciple(Printer printer, Scanner scanner) {
        this.printer = printer;
        this.scanner = scanner;
    }

    @Override
    public void print(Document D) {
        printer.print(D);
    }

    @Override
    public void scan(Document D) {
        scanner.scan(D);
    }
}

