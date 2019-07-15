package cashregister;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class CashRegisterTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister(printer);
        Item item1 = new Item("item1",2);
        Item[] items = new Item[]{item1};
        Purchase purchase = new Purchase(items);
        cashRegister.process(purchase);

        Assertions.assertEquals("item1\t2.0\n",systemOut());

    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {
        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister(printer);
        Purchase mockPurchase = mock(Purchase.class);

        when(mockPurchase.asString()).thenReturn("鸡蛋两只");
        cashRegister.process(mockPurchase);

        Assertions.assertEquals("鸡蛋两只",systemOut());
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        Printer mockPrinter = mock(Printer.class);
        CashRegister cashRegister = new CashRegister(mockPrinter);
        Purchase purchase = mock(Purchase.class);

        cashRegister.process(purchase);
        verify(mockPrinter).print(purchase.asString());
    }

}
