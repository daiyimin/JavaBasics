package com.ericsson.uml.ui;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;

import javax.swing.*;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;

public class SVGDialog {

    public static void main(String[] args) throws IOException {
    	File f = new File("c:\\Ericsson\\test.svg");
    	SVGDialog app = new SVGDialog(f);
    	app.show();
    }

    // The frame.
    protected JFrame frame = null;
    protected File file;

    // The SVG canvas.
    protected JSVGCanvas svgCanvas = new JSVGCanvas();

    public SVGDialog(File file) {
        this.file = file;
    }

    public SVGDialog(String filename) {
        this.file = new File(filename);
    }

    public JComponent createComponents() throws IOException {
        // Create a panel and add the button, status label and the SVG canvas.
        final JPanel panel = new JPanel(new BorderLayout());

        panel.add("Center", svgCanvas);

        // set URI of svg file
        svgCanvas.setURI(file.toURL().toString());

        svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
            public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
                frame.pack();
            }
        });

        return panel;
    }
    
    public void show() throws IOException {

        // create the GUI for viewing the image if needed
        if (frame == null) {
            frame = new JFrame();
            frame.getContentPane().add(createComponents());

            double lx = Toolkit.getDefaultToolkit().getScreenSize().getWidth();  
            double ly = Toolkit.getDefaultToolkit().getScreenSize().getHeight();  
            frame.setLocation(new Point((int) (lx / 2) - 225, (int) (ly / 2) - 200));

            // Display the frame.
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setTitle(file.getName());
            frame.pack();
            frame.setVisible(true);
        }

        // draw
        frame.repaint();
    }

}