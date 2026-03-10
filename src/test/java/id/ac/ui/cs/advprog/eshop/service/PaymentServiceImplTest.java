package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    Order order;
    Map<String, String> paymentDataBankTransfer;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order("13652556-012a-4c07-b546-54eb1396d79b", products, 1708560000L, "Safira Sudrajat");

        paymentDataBankTransfer = new HashMap<>();
        paymentDataBankTransfer.put("bankName", "BCA");
        paymentDataBankTransfer.put("referenceCode", "REF12345");
    }

    @Test
    void testAddPaymentSuccess() {
        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Payment payment = paymentService.addPayment(order, "BANK_TRANSFER", paymentDataBankTransfer);

        assertNotNull(payment);
        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(order.getId(), payment.getPaymentData().get("orderId"));
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    void testSetStatusSuccess() {
        paymentDataBankTransfer.put("orderId", order.getId());
        Payment payment = new Payment("1", "BANK_TRANSFER", paymentDataBankTransfer);

        when(orderRepository.findById(order.getId())).thenReturn(order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusRejected() {
        paymentDataBankTransfer.put("orderId", order.getId());
        Payment payment = new Payment("1", "BANK_TRANSFER", paymentDataBankTransfer);

        when(orderRepository.findById(order.getId())).thenReturn(order);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = new Payment("1", "BANK_TRANSFER", paymentDataBankTransfer);
        assertThrows(IllegalArgumentException.class, () -> paymentService.setStatus(payment, "MEOW"));
        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    void testSetStatusOrderNotFoundOrNoOrderId() {
        Payment payment = new Payment("1", "BANK_TRANSFER", paymentDataBankTransfer);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, never()).findById(anyString());
        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void testGetPaymentIfFound() {
        Payment payment = new Payment("1", "BANK_TRANSFER", paymentDataBankTransfer);
        when(paymentRepository.findById("1")).thenReturn(payment);

        Payment result = paymentService.getPayment("1");

        assertNotNull(result);
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {
        when(paymentRepository.findById("zczc")).thenReturn(null);
        Payment result = paymentService.getPayment("zczc");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        Payment payment = new Payment("1", "BANK_TRANSFER", paymentDataBankTransfer);
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);

        when(paymentRepository.findAll()).thenReturn(paymentList);

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
        assertEquals(payment.getId(), result.get(0).getId());
    }

    @Test
    void testSetStatusOrderIsNull() {
        paymentDataBankTransfer.put("orderId", "invalid-id");
        Payment payment = new Payment("1", "BANK_TRANSFER", paymentDataBankTransfer);

        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(orderRepository.findById("invalid-id")).thenReturn(null);

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        verify(paymentRepository, times(1)).save(payment);
        verify(orderRepository, times(1)).findById("invalid-id");
        verify(orderRepository, never()).save(any(Order.class));
    }
}