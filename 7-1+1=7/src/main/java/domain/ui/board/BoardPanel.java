/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.ui.board;

import domain.adapter.board.findBoardById.FindBoardByIdViewModel;
import domain.ui.MainFrame;

/**
 *
 * @author lab1321
 */
public class BoardPanel extends javax.swing.JPanel {
    private FindBoardByIdViewModel findBoardByIdViewModel;
    private MainFrame mainFrame;
    /**
     * Creates new form BoardPanel
     */
    public BoardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.setBounds(0, 0, 1024, 768);
        initComponents();
        this.setVisible(false);
    }
    
    public void setFindBoardByIdViewModel(FindBoardByIdViewModel findBoardByIdViewModel) {
        this.findBoardByIdViewModel = findBoardByIdViewModel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        backBtn = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Comic Sans MS", 0, 48)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("Title");

        backBtn.setFont(new java.awt.Font("Comic Sans MS", 0, 24)); // NOI18N
        backBtn.setText("Back");
        backBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(176, 176, 176)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(backBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(titleLabel))
                .addContainerGap(688, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        titleLabel.setText(findBoardByIdViewModel.getName());
    }//GEN-LAST:event_formComponentShown

    private void backBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backBtnActionPerformed
        // TODO add your handling code here:
        mainFrame.getContentPane().remove(this);
        mainFrame.getContentPane().add(mainFrame.getUserPanel());
        mainFrame.getUserPanel().setVisible(true);
        this.setVisible(false);
        mainFrame.revalidate();
        mainFrame.repaint();
    }//GEN-LAST:event_backBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backBtn;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}