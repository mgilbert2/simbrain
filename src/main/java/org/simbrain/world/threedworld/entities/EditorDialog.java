package org.simbrain.world.threedworld.entities;

import org.simbrain.world.threedworld.ThreeDDesktopComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;

public class EditorDialog {
    public interface Editor {
        JComponent layoutFields();

        void readValues();

        void writeValues();

        void close();
    }

    private static ThreeDDesktopComponent owner;
    public static DecimalFormat integerFormat = new DecimalFormat();
    public static DecimalFormat floatFormat = new DecimalFormat();

    static {
        integerFormat.setParseIntegerOnly(true);
        floatFormat.setMaximumFractionDigits(3);
        floatFormat.setMinimumFractionDigits(1);
    }

    public static void setOwner(ThreeDDesktopComponent value) {
        owner = value;
    }

    private JDialog dialog;
    private Editor editor;

    private Action okAction = new AbstractAction("Ok") {
        @Override
        public void actionPerformed(ActionEvent event) {
            editor.writeValues();
            closeEditor();
        }
    };
    private JButton okButton = new JButton(okAction);
    private Action applyAction = new AbstractAction("Apply") {
        @Override
        public void actionPerformed(ActionEvent event) {
            editor.writeValues();
        }
    };
    private JButton applyButton = new JButton(applyAction);
    private Action cancelAction = new AbstractAction("Cancel") {
        @Override
        public void actionPerformed(ActionEvent event) {
            closeEditor();
        }
    };
    private JButton cancelButton = new JButton(cancelAction);

    public EditorDialog() {
    }

    public void showEditor(Editor editor) {
        if (dialog != null)
            return;
        dialog = new JDialog(owner.desktop.getFrame());
        dialog.setTitle("ThreeDWorld Editor");
        Container contentPane = dialog.getContentPane();
        contentPane.setLayout(new BorderLayout());
        this.editor = editor;
        contentPane.add(editor.layoutFields(), "Center");
        editor.readValues();
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(okButton);
        buttonPane.add(applyButton);
        buttonPane.add(cancelButton);
        contentPane.add(buttonPane, "South");
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closeEditor();
            }
        });
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    public void closeEditor() {
        if (dialog != null) {
            editor.close();
            dialog.setVisible(false);
            dialog = null;
        }
    }
}
