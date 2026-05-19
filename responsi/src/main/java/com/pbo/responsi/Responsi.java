/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.pbo.responsi;

import com.pbo.responsi.controller.CartController;
import com.pbo.responsi.model.CartRepository;
import com.pbo.responsi.model.MysqlCartRepository;
import com.pbo.responsi.service.DiscountStrategy;
import com.pbo.responsi.service.Event1212DiscountStrategy;
import com.pbo.responsi.view.CartView;
import javax.swing.SwingUtilities;

/**
 *
 * @author farhannivta
 */
public class Responsi {
        
    public static void main(String[]args){
        
        SwingUtilities.invokeLater(()->{
            CartRepository repository = new MysqlCartRepository();
            
            DiscountStrategy discountStrategy = new Event1212DiscountStrategy();
            
            CartView view = new CartView();
            
            new CartController(repository, discountStrategy, view);
        });
    }
}
