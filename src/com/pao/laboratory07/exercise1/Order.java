package com.pao.laboratory07.exercise1;

import com.pao.laboratory07.exercise1.exceptions.CannotCancelFinalOrderException;
import com.pao.laboratory07.exercise1.exceptions.CannotRevertInitialOrderStateException;
import com.pao.laboratory07.exercise1.exceptions.OrderIsAlreadyFinalException;

import java.util.Stack;

public class Order {
    private OrderState actuala;
    private final Stack<OrderState> istoric = new Stack<>();
    public Order(OrderState initial){
        this.actuala = initial;
    }
    public void nextState() throws OrderIsAlreadyFinalException {
        if (actuala.isFinal())
            throw new OrderIsAlreadyFinalException("Esti intr-o stare finala");
        OrderState next = actuala.next();
        if (next != null) {
            istoric.push(actuala);
            actuala = next;
            System.out.println("Order state updated to: "+actuala);
        }
    }
    public void cancel() throws CannotCancelFinalOrderException {
        if (actuala.isFinal())
            throw new CannotCancelFinalOrderException("Nu mai poti sa anulezi ultima comanda");
        istoric.push(actuala);
        actuala = OrderState.CANCELED;
        System.out.println("Order has been canceled.");
    }
    public void undoState() throws CannotRevertInitialOrderStateException {
        if (istoric.isEmpty())
            throw new CannotRevertInitialOrderStateException("Ups, ceva a mers super prost");
        actuala = istoric.pop();
        System.out.println("Order state reverted to: " + actuala);
    }
}
