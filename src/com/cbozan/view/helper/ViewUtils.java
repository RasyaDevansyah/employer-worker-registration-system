package com.cbozan.view.helper;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class ViewUtils {
    public static int showConfirmationDialog(Component parent, String title, Object[] content) {
        return JOptionPane.showOptionDialog(parent, content, title, 1, 1,
                new ImageIcon("src\\icon\\accounting_icon_1_32.png"),
                new Object[] {"SAVE", "CANCEL"}, "CANCEL");
    }
    
    public static void showErrorMessage(Component parent, String message) {
		JOptionPane.showMessageDialog(parent, message, "ERROR", JOptionPane.ERROR_MESSAGE);
	}
    
}
