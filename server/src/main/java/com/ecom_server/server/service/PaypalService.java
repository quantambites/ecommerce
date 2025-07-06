package com.ecom_server.server.service;

import com.ecom_server.server.model.submodel.CartItem_orders;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PaypalService {

    @Value("${spring.frontend.origin}")
    private String frontendOrigin;

    private final APIContext apiContext;

    public String createPaypalPayment(List<CartItem_orders> cartItems, Double totalAmount) throws PayPalRESTException {
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.format("%.2f", totalAmount));

        List<Item> items = new ArrayList<>();
        for (CartItem_orders cartItem : cartItems) {
            Item item = new Item();
            item.setName(cartItem.getTitle());
            item.setCurrency("USD");

            double parsedPrice = Double.parseDouble(cartItem.getPrice());
            item.setPrice(String.format("%.2f", parsedPrice));

            item.setQuantity(String.valueOf(cartItem.getQuantity()));
            item.setSku(cartItem.getProductId());
            items.add(item);
        }

        ItemList itemList = new ItemList();
        itemList.setItems(items);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setItemList(itemList);
        transaction.setDescription("Purchase from YourShop");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl(frontendOrigin + "/shop/paypal-return");
        redirectUrls.setCancelUrl(frontendOrigin + "/shop/paypal-cancel");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        Payment createdPayment = payment.create(apiContext);

        // Extract the approval URL
        return createdPayment.getLinks()
                .stream()
                .filter(link -> "approval_url".equalsIgnoreCase(link.getRel()))
                .map(Links::getHref)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Approval URL not found"));
    }
}
