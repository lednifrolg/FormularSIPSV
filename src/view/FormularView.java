package view;

import controller.Controller;
import model.Variant;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Nolofinwe on 10/22/2016.
 */
public class FormularView extends JFrame {
    private JTextField mNameTextField;
    private JTextField mPriceTextField;
    private JTextField mIDTextField;
    private JList<Variant> mVariantsList;
    private JButton mAddVariantButton;
    private JButton mRemoveVariantButton;
    private JButton mValidateButton;
    private JButton mSaveXMLButton;
    private JButton mSaveXSLTButton;
    private JButton signButton;
    private JButton mCloseButton;
    private JTextField mAvailTextField;
    private JTextField mSizeTextField;
    private JPanel MainPanel;
    private JButton signTSButton;

    private Controller mController;


    public void init() {
        mController = new Controller(this);

        DefaultListModel<Variant> model = new DefaultListModel<Variant>();


        mVariantsList.setModel(model);

        mRemoveVariantButton.addActionListener(evt -> mRemoveVariantButtonActionPerformed(evt));

        mAddVariantButton.addActionListener(evt -> mAddVariantButtonActionPerformed(evt));

        mValidateButton.addActionListener(evt -> mValidateButtonActionPerformed(evt));

        mSaveXMLButton.addActionListener(evt -> mSaveXMLButtonActionPerformed(evt));

        mCloseButton.addActionListener(evt -> jButton3ActionPerformed(evt));

        mSaveXSLTButton.addActionListener(evt -> mSaveXSLTButtonActionPerformed(evt));

        signButton.addActionListener(evt -> mSignDocButtonActionPerformed(evt));

        signTSButton.addActionListener(evt -> signTSButtonActionPerformed(evt));

        this.setContentPane(MainPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void signTSButtonActionPerformed(ActionEvent evt) {
        mController.signDocTS();
    }

    public JTextField getAvailTextField() {
        return mAvailTextField;
    }

    public JTextField getIDTextField() {
        return mIDTextField;
    }

    public JTextField getNameTextField() {
        return mNameTextField;
    }

    public JTextField getPriceTextField() {
        return mPriceTextField;
    }

    public JTextField getSizeTextField() {
        return mSizeTextField;
    }

    public JList<Variant> getVariantsList() {
        return mVariantsList;
    }

    private void mAddVariantButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mAddVariantButtonActionPerformed
        mController.addVariant();
    }//GEN-LAST:event_mAddVariantButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void mRemoveVariantButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mRemoveVariantButtonActionPerformed
        mController.removeVariant();
    }//GEN-LAST:event_mRemoveVariantButtonActionPerformed

    private void mValidateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mValidateButtonActionPerformed
        mController.validateXML();
    }//GEN-LAST:event_mValidateButtonActionPerformed

    private void mSaveXMLButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSaveXMLButtonActionPerformed
        mController.saveAsXML();
    }//GEN-LAST:event_mSaveXMLButtonActionPerformed

    private void mSaveXSLTButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSaveXSLTButtonActionPerformed
        mController.saveAsXSLT();
    }//GEN-LAST:event_mSaveXSLTButtonActionPerformed

    private void mSignDocButtonActionPerformed(java.awt.event.ActionEvent evt) {
        mController.signDoc();
    }

}
