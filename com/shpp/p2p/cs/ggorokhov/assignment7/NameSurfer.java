package com.shpp.p2p.cs.ggorokhov.assignment7;

/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import com.shpp.cs.a.simple.SimpleProgram;

import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends SimpleProgram implements NameSurferConstants {

    private JTextField textField;
    private NameSurferDataBase nameSurferDataBase;
    private NameSurferGraph graph;

	/* Method: init() */

    /**
     * This method has the responsibility for reading in the data base
     * and initializing the interactors at the top of the window.
     */
    public void init() {
        JButton clear;

        this.add(new JLabel("Name: "), "North");
        this.add(textField = new JTextField("", 25), "North");
        this.add(new JButton("Graph"), "North");
        this.add(clear = new JButton("Clear"), "North");
        this.addActionListeners();

        // creating new database class obhect
        nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);

        // creating animation class object
        graph = new NameSurferGraph();
        add(graph);

        // for correct "Enter" press functionality
        clear.doClick();
    }

	/* Method: actionPerformed(e) */

    /**
     * This class is responsible for detecting when the buttons are
     * clicked, so you will have to define a method to respond to
     * button actions.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Clear")) {
            graph.clear();
            textField.setText("");
        } else if (e.getActionCommand().equals("Graph")) {
            getNameSurfer();
        }

        // for correct "Enter" press functionality
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    getNameSurfer();
                }
            }
        });
    }

    /**
     * method for work with the user, which clicked or pressed button
     */
    private void getNameSurfer() {
        NameSurferEntry nameSurferEntry = nameSurferDataBase.findEntry(textField.getText());

        if (nameSurferEntry != null) {
            graph.addEntry(nameSurferEntry);
            graph.update();
        }

        textField.setText("");
    }
}
