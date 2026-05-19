/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pbo.responsi.controller;

import com.pbo.responsi.dto.CartItemDTO;
import com.pbo.responsi.model.CartRepository;
import com.pbo.responsi.service.DiscountStrategy;
import com.pbo.responsi.view.CartView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Lab Informatika
 */
public class CartController {
    
    private final CartRepository repository;
    private final DiscountStrategy discountStrategy;
    private final CartView view;
    
    public CartController(
            CartRepository repository,
            DiscountStrategy discountStrategy,
            CartView view
    ){
        this.repository = repository;
        this.discountStrategy = discountStrategy;
        this.view = view;
        
        init();
    }
    
    private void init(){
        refreshView();
        
        view.onAdd(e->addItem());
        view.onUpdate(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }

            private void updateItem() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        view.onDelete(e->deleteItem());
    };
    
    private void refreshView(){
        List<CartItemDTO> items = repository.findAll();
        
        double subtotal = items.stream().mapToDouble(i -> i.getPrice()* i.getQuantity()).sum();
        
        double discount = discountStrategy.calculateDiscount(subtotal);
        
        double total = subtotal - discount;
        
        view.showCartItems(items, subtotal, discount, total, discountStrategy.getDiscountName());
    }
    
    private void addItem(){
        String name = view.getNameInput();
        String priceStr = view.getPriceInput();
        String qtyStr = view.getQtyInput();
        
        if (name.isEmpty() || priceStr.isEmpty() || qtyStr.isEmpty()) {
            view.showMessage("semua field wajib diisi!");
            return;
        }
        try {
            double price = Double.parseDouble(priceStr);
            int qty = Integer.parseInt(qtyStr);
            
            repository.save(new CartItemDTO(name, price, qty));
            
            refreshView();
            view.clearForm();
            
        } catch (NumberFormatException ex) {
            view.showMessage("input angka tidak valid!");
}
        }

    private void updateItem(){
        String selectedName = 
                view.getSelectedRowItemName();
        
        if (selectedName == null) {
            view.showMessage("Pilih item terlebih dahulu!");
            return;
        }
        String inputStr = JOptionPane.showInputDialog(view, "Masukkan Qty baru untuk " + selectedName);
        if (inputStr != null) {
            try {
                int qty = Integer.parseInt(inputStr);
                
                repository.updateQuantity(selectedName, qty);
                refreshView();
                view.clearForm();
                
            } catch (NumberFormatException ex) {
                view.showMessage("Qty harus angka!");
            }
        }
    }
    
    private void deleteItem(){
        String selectedName = view.getSelectedRowItemName();
        
        if (selectedName == null) {
            view.showMessage("Pilih item terlebih dahulu!");
            return;
        }
        repository.delete(selectedName);
        
        refreshView();
        view.clearForm();
    }
}
