package jp.co.ogis_ri.nautible.app.order.domain;

public interface MessageSender {

    void reserveAllocateStock(Order order);

    void approveAllocateStock(Order order);

    void rejectAllocateStock(Order order);

    void createPayment(Order order);

    void rejectCreatePayment(Order order);

}
