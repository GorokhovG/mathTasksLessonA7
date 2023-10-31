package com.shpp.p2p.cs.ggorokhov.assignment7;

/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
        implements NameSurferConstants, ComponentListener {

    private ArrayList<ArrayList<String>> graphData = new ArrayList<>();
    private Color[] colors = {Color.BLUE, Color.RED, Color.MAGENTA, Color.BLACK};

    /**
     * Creates a new NameSurferGraph object that displays the data.
     */
    public NameSurferGraph() {
        addComponentListener(this);
        // You fill in the rest //
        createGrid(APPLICATION_WIDTH, APPLICATION_HEIGHT);
    }

    /**
     * method for drawing grid
     * @param width Screen width
     * @param height Screen height
     */
    private void createGrid(int width, int height) {
        int distance = width / NDECADES;

        // main grid
        for (int i = 0; i < NDECADES; i++) {
            GLine line = new GLine(
                    distance * i + 2,
                    0,
                    distance * i + 2,
                    height
            );
            add(line);
            add(createText(
                    Integer.toString(START_DECADE + i * 10),
                    distance * i + 2,
                    height
            ));
        }

        // two separators for top and bottom
        for (int i = 0; i < 2; i++)
            add(new GLine(
                    0,
                    i == 0? GRAPH_MARGIN_SIZE : height - GRAPH_MARGIN_SIZE,
                    width,
                    i == 0? GRAPH_MARGIN_SIZE : height - GRAPH_MARGIN_SIZE
            ));
    }

    /**
     * method for drawing text on screen
     * @param text decades
     * @param x distance between lines
     * @param y text height position
     * @return GLabel
     */
    private GLabel createText(String text, int x, int y) {
        GLabel result = new GLabel(text, x ,y);

        result.setFont("Comic Sans-" + (GRAPH_MARGIN_SIZE - 2));

        return result;
    }

    /**
     * Clears the list of name surfer entries stored inside this class.
     */
    public void clear() {
        removeAll();
        graphData = new ArrayList<>();
        createGrid(getWidth(), getHeight());
    }

	
	/* Method: addEntry(entry) */

    /**
     * Adds a new NameSurferEntry to the list of entries on the display.
     * Note that this method does not actually draw the graph, but
     * simply stores the entry; the graph is drawn by calling update.
     */
    public void addEntry(NameSurferEntry entry) {
        ArrayList<String> singleData = new ArrayList<>();

        for (int i = 0; i < NDECADES; i++)
            singleData.add(Integer.toString(entry.getRank(i)));

        singleData.add(entry.getName());

        if (!graphData.contains(singleData))
            graphData.add(singleData);
    }


    /**
     * Updates the display image by deleting all the graphical objects
     * from the canvas and then reassembling the display according to
     * the list of entries. Your application must call update after
     * calling either clear or addEntry; update is also called whenever
     * the size of the canvas changes.
     */
    public void update() {
        removeAll();
        createGrid(getWidth(), getHeight());
        createGraph(getWidth(), getHeight());
    }

    /**
     * method for graph drawing
     * @param width screen width
     * @param height screen height
     */
    private void createGraph(int width, int height) {
        Color color = colors[0];

        if (!graphData.isEmpty()) {
            for (ArrayList<String> graphDatum : graphData) {
                drawGraphLine(graphDatum, width, height, color);
                color = getNextColor(color);
            }
        }
    }

    /**
     * method for changing color
     * @param color income color
     * @return next color
     */
    private Color getNextColor(Color color) {
        if (color == colors[0])
            color = colors[1];
        else if (color == colors[1])
            color = colors[2];
        else if (color == colors[2])
            color = colors[3];
        else if (color == colors[3])
            color = colors[0];

        return color;
    }

    /**
     * method for drawing simple part (graph / NDCADES) lines
     * @param dataArray array with data
     * @param width screen width
     * @param height screen height
     * @param color line color
     */
    private void drawGraphLine(ArrayList<String> dataArray, int width, int height, Color color) {
        int distanceX = width / NDECADES;
        double distanceY = (double)(height - GRAPH_MARGIN_SIZE * 2) / MAX_RANK;

        // building main graph lines
        for (int i = 0; i < dataArray.size() - 1; i++) {
            if (i != dataArray.size() - 2) {
                GLine line = new GLine(
                        distanceX * (i) + 2,
                        distanceY * Integer.parseInt(dataArray.get(i)) + GRAPH_MARGIN_SIZE,
                        distanceX * (i + 1) + 2,
                        distanceY * Integer.parseInt(dataArray.get(i + 1)) + GRAPH_MARGIN_SIZE
                );
                line.setColor(color);
                add(line);
            }

            // creating text for every dots
            GLabel text = new GLabel(
                    dataArray.get(dataArray.size() - 1) + " " + (
                            dataArray.get(i).equals("1001")? "0" : dataArray.get(i)),
                    distanceX * (i) + 2 ,
                    distanceY * Integer.parseInt(dataArray.get(i)) + GRAPH_MARGIN_SIZE
            );
            text.setColor(color);
            add(text);
        }
    }


    /* Implementation of the ComponentListener interface */
    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        update();
    }

    public void componentShown(ComponentEvent e) {
    }
}
