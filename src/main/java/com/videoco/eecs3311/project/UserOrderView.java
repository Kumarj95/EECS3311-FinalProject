/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.videoco.eecs3311.project;

import java.awt.Cursor;
import java.awt.event.WindowEvent;
import java.util.UUID;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author kvjha
 */
public class UserOrderView extends javax.swing.JFrame {

    /**
     * Creates new form UserOrderView
     */
    UserOrder order;
    public UserOrderView() {
        initComponents();
    }
    
    public void initOrder(UserOrder o){
        order=o;
        jLabel1.setText(order.getOrderID().toString());
//        jLabel3.setText(order.getOrderStatus().toString());
           jTextField1.setText(order.getOrderStatus().toString());
           jTextField1.setEditable(false);
           String[] col= {"Movie-ID"};
           DefaultTableModel tableModel= new DefaultTableModel(col,0);
           for(Movie movie:o.getMovies()){
               String[] row= {movie.getId().toString()};
               tableModel.addRow(row);
           }
           movies.setModel(tableModel);
           movies.getColumnModel().getColumn(0).setMinWidth(300);
           
           if(!order.getOrderStatus().equals(OrderStatus.Creating)){
               jButton1.setEnabled(false);
               jTextField2.setEditable(false);
               
               jButton6.setEnabled(false);
               jTextField6.setEditable(false);
               
               jTextField3.setEditable(false);
               jButton2.setEnabled(false);
               
               jTextField4.setEditable(false);
               jTextField5.setEditable(false);
               jButton3.setEnabled(false);
               
               if(order.getOrderStatus().equals(OrderStatus.Processed) ||order.getOrderStatus().equals(OrderStatus.Delivering) ){
                jButton5.setEnabled(true);   
               }else{
                   jButton5.setEnabled(false);
               }
               jButton4.setEnabled(false);
               
            }else{
               jButton1.setEnabled(true);
               jTextField2.setEditable(true);
               jButton6.setEnabled(true);
               jTextField6.setEditable(true);   
               
               jTextField3.setEditable(true);
               jButton2.setEnabled(true);
               jTextField4.setEditable(true);
               jTextField5.setEditable(true);
               jButton3.setEnabled(true);
               jButton4.setEnabled(true);
               jButton5.setEnabled(false);

               
           }
           jLabel5.setText(order.getOrderPrice()+"");
           jCheckBox1.setSelected(order.isPayWithPoints());
           
           jTextField3.setText(order.getShippingAddress());
           if(order.getPaymentInfo()!=null){
           jTextField4.setText(order.getPaymentInfo().getBillingAddress());           
           jTextField5.setText(order.getPaymentInfo().getCreditCardNumber());
           }else{
           jTextField4.setText("");           
           jTextField5.setText("");
               
           }
           if(order.getOrderStatus().equals(OrderStatus.Delivered) || order.getOrderStatus().equals(OrderStatus.Overdue)){
               ReturnOrder.setEnabled(true);
           }else{
               ReturnOrder.setEnabled(false);
               
           }
         
           
           
                      

           
           
           
           
    }
    
    
    public void refreshTable(){
           String[] col= {"Movie-ID"};        
           DefaultTableModel tableModel= new DefaultTableModel(col,0);
           for(Movie movie:order.getMovies()){
               String[] row= {movie.getId().toString()};
               tableModel.addRow(row);
           }
           jTextField1.setText(order.getOrderStatus().toString());
           
           movies.setModel(tableModel);
          jLabel5.setText(order.getOrderPrice()+"");
          if(order.isPayWithPoints()==false){
            jCheckBox1.setSelected(false);             
          }else{
            jCheckBox1.setSelected(order.isPayWithPoints());
          }
           jTextField3.setText(order.getShippingAddress());
           
           if(order.getPaymentInfo()!=null){
           jTextField4.setText(order.getPaymentInfo().getBillingAddress());           
           jTextField5.setText(order.getPaymentInfo().getCreditCardNumber());
           }else{
           jTextField4.setText("");           
           jTextField5.setText("");
               
           }
                     if(!order.getOrderStatus().equals(OrderStatus.Creating)){
               jButton1.setEnabled(false);
               jTextField2.setEditable(false);
               
               jButton6.setEnabled(false);
               jTextField6.setEditable(false);
               
               jTextField3.setEditable(false);
               jButton2.setEnabled(false);
               
               jTextField4.setEditable(false);
               jTextField5.setEditable(false);
               jButton3.setEnabled(false);
               
               if(order.getOrderStatus().equals(OrderStatus.Processed) ||order.getOrderStatus().equals(OrderStatus.Delivering) ){
                jButton5.setEnabled(true);   
               }else{
                   jButton5.setEnabled(false);
               }
               jButton4.setEnabled(false);
               
            }else{
               jButton1.setEnabled(true);
               jTextField2.setEditable(true);
               jButton6.setEnabled(true);
               jTextField6.setEditable(true);   
               
               jTextField3.setEditable(true);
               jButton2.setEnabled(true);
               jTextField4.setEditable(true);
               jTextField5.setEditable(true);
               jButton3.setEnabled(true);
               jButton4.setEnabled(true);
               jButton5.setEnabled(false);

               
           }
           if(order.getOrderStatus().equals(OrderStatus.Delivered) || order.getOrderStatus().equals(OrderStatus.Overdue)){
               ReturnOrder.setEnabled(true);
           }else{
               ReturnOrder.setEnabled(false);
               
           }
                     
 
          
          
           
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        movies = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        ReturnOrder = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("jLabel1");

        jLabel2.setText("OrderStatus: ");

        jTextField1.setText("jTextField1");

        movies.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        movies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                moviesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(movies);

        jLabel4.setText("Movies");

        jButton1.setText("Add Movie To Order By UUID");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("OrderPrice:");

        jLabel5.setText("jLabel5");

        jLabel6.setText("Shipping Address: ");

        jButton2.setText("Save Shipping");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("payWithPoints");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jLabel7.setText("Billing Address:");

        jLabel8.setText("Credit Card Number: ");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jButton3.setText("Set Payment Info");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(102, 255, 102));
        jButton4.setText("Place Order");
        jButton4.setBorderPainted(false);
        jButton4.setOpaque(true);
        jButton4.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton4MouseMoved(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 102, 102));
        jButton5.setText("Cancel Order");
        jButton5.setBorderPainted(false);
        jButton5.setOpaque(true);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });

        jButton6.setText("Remove Movie from Order By UUID");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        ReturnOrder.setText("Return");
        ReturnOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReturnOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(344, 344, 344)
                .addComponent(jButton2)
                .addGap(224, 224, 224))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(ReturnOrder)
                                .addGap(61, 61, 61))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(210, 210, 210)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(263, 263, 263)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jButton6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBox1))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(ReturnOrder)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jLabel7)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void moviesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moviesMouseClicked
        // TODO add your handling code here:
        int index=movies.getSelectedRow();
        TableModel model=movies.getModel();
        String id= model.getValueAt(index, 0).toString();
        UUID movID=UUID.fromString(id);
        SystemV sys= SystemV.getInstance();
        Movie mov= sys.getMovie(movID);
        UserMovieFrame frame= new UserMovieFrame();
        frame.initMovie(mov);
        NormalUser user= sys.getNormalUsersMap().get(order.getUserID());
        frame.initUser(user);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
       
    }//GEN-LAST:event_moviesMouseClicked

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton4MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String idS=jTextField2.getText();
        if(!idS.isBlank()){
            try{
                UUID id=UUID.fromString(idS);
                if(order.addToOrder(id)){
                    refreshTable();
                }else{
                    JOptionPane.showMessageDialog(this, "Could not add movie with that UUID!");                                                       
                }
            }catch(IllegalArgumentException e){
                JOptionPane.showMessageDialog(this, "Enter A Valid UUID!");                                   
            }
        }else{
                JOptionPane.showMessageDialog(this, "Enter A UUID!");                   
            
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        String idS=jTextField6.getText();
        if(!idS.isBlank()){
            try{
                UUID id=UUID.fromString(idS);
                if(order.deleteFromOrder(id)){
                    refreshTable();
                }else{
                    JOptionPane.showMessageDialog(this, "Could not delete movie with that UUID!");                                                       
                }
            }catch(IllegalArgumentException e){
                JOptionPane.showMessageDialog(this, "Enter A Valid UUID!");                                   
            }
        }else{
                JOptionPane.showMessageDialog(this, "Enter A UUID!");                   
            
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField6ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        String shipping=jTextField3.getText();
        if(shipping.isBlank()){
            JOptionPane.showMessageDialog(this, "Please enter a shipping address!");                                                                   
        }else{
            order.setShippingAddress(shipping);
            refreshTable();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        String billing=jTextField4.getText();
        String creditCard= jTextField5.getText();
        if(billing.isBlank() || creditCard.isBlank()){
            JOptionPane.showMessageDialog(this, "Please enter both a billing address and a shipping address!");                                                                   
            
        }else{
            PaymentInfo info= new PaymentInfo(creditCard,billing);
            order.setPaymentInfo(info);
            refreshTable();
            
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if(order.getShippingAddress()==null || order.getPaymentInfo()==null){
            JOptionPane.showMessageDialog(this, "Order is missing some fields!");                                                                   
         }else{
            order.setPayWithPoints(jCheckBox1.isSelected());
               if(order.placeOrder()){
                   refreshTable();
               }else{
                   JOptionPane.showMessageDialog(this, "Invalid Order!");                                                                                      
               }
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        boolean cancelOrder = order.cancelOrder();
        if(cancelOrder){
              JOptionPane.showMessageDialog(this, "Order Cancelled Successfully!!");                                                                                      
         }else{
              JOptionPane.showMessageDialog(this, "Could not cancel order");
              this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void ReturnOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReturnOrderActionPerformed
        // TODO add your handling code here:
        if(order.returnOrder()){
              JOptionPane.showMessageDialog(this, "Order Returned!!");                                                                                      
        }else{
            JOptionPane.showMessageDialog(this, "Return Failed!!");                                                                                      
        }
        
    }//GEN-LAST:event_ReturnOrderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserOrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserOrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserOrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserOrderView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserOrderView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ReturnOrder;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTable movies;
    // End of variables declaration//GEN-END:variables
}
