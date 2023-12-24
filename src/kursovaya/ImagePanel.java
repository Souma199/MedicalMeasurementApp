package kursovaya;

import javax.swing.*;
import java.awt.*;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.beans.Transient;
import java.io.Serial;
import java.awt.Dimension;
import java.awt.geom.Line2D;
import java.awt.Dimension;
import java.awt.geom.Line2D;
import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;


public class ImagePanel extends JPanel {

    private Image image;
    private Point startPoint;
    private Point endPoint;
    private Point selectedPoint; // Добавляем переменную для хранения выбранной точки
    private JTextField scalingFactorField; // Добавить поле для коэффициента масштабирования

    private JPanel ImagePanel;

    Color startPointColor = Color.BLUE; // Цвет для начальной точки
    Color endPointColor = Color.BLUE; // Цвет для конечной точки
    Color lineColor = Color.RED; // Цвет для линии
    Color selectedPointColor = Color.GREEN; // Цвет выбранной точки


    private int clickCount = 0;

    public ImagePanel(JTextField scalingFactorField) {
        this.scalingFactorField = scalingFactorField;

        // обработчик событий мыши (реагирования на действия пользователя.)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickCount++;
                if (clickCount == 1) {
                    setStartPoint(e.getPoint());
                } else if (clickCount == 2) {
                    setEndPoint(e.getPoint());
                } else if (clickCount == 3) {
                    setStartPoint(null);
                    setEndPoint(null);
                    clickCount = 0;
                }
                repaint();
            }
        });
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
        repaint();
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
        repaint();
    }

    public void setSelectedPoint(Point selectedPoint) {
        this.selectedPoint = selectedPoint;
        repaint();
    }





    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int imageWidth = image.getWidth(this);
            int imageHeight = image.getHeight(this);

            double scale = Math.min((double) panelWidth / imageWidth, (double) panelHeight / imageHeight);
            int scaledWidth = (int) (scale * imageWidth);
            int scaledHeight = (int) (scale * imageHeight);

            int x = (panelWidth - scaledWidth) / 2;
            int y = (panelHeight - scaledHeight) / 2;

            // Draw the scaled image
            g.drawImage(image.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH), x, y, this);

            // Draw the line if start and end points are set
            if (startPoint != null && endPoint != null) {
                g.setColor(lineColor); // Установка цвета для линии
                g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);

                int pointSize = 5;
                g.setColor(startPointColor); // Установка цвета для начальной точки
                g.fillOval(startPoint.x - pointSize / 2, startPoint.y - pointSize / 2, pointSize, pointSize);
                g.setColor(endPointColor); // Установка цвета для конечной точки
                g.fillOval(endPoint.x - pointSize / 2, endPoint.y - pointSize / 2, pointSize, pointSize);

                // Calculate and display the length above the line
                double pixelLength = startPoint.distance(endPoint);
                double scalingFactor = 1.0; // Default value
                String scalingFactorText = scalingFactorField.getText().trim();
                if (!scalingFactorText.isEmpty()) {
                    try {
                        scalingFactor = Double.parseDouble(scalingFactorText);
                    } catch (NumberFormatException e) {
                        // Handle parsing error
                        scalingFactor = 1.0; // Use default value if parsing fails
                    }
                }
                double millimeterLength = pixelLength * scalingFactor;
                String lengthText = String.format("%.2f mm", millimeterLength);
                g.setColor(Color.RED);
                g.drawString(lengthText, (startPoint.x + endPoint.x) / 2, (startPoint.y + endPoint.y) / 2 - 10);

                // Draw selected point
            }    if (selectedPoint != null) {
                int pointSize = 5;
                g.setColor(selectedPointColor);
                g.fillOval(selectedPoint.x - pointSize / 2, selectedPoint.y - pointSize / 2, pointSize, pointSize);
            }
        } else if (startPoint != null) {
            // Draw only the start point if available
            int pointSize = 5;
            g.setColor(Color.RED);
            g.fillOval(startPoint.x - pointSize / 2, startPoint.y - pointSize / 2, pointSize, pointSize);
        }
    }
}





































/*
package kursovaya;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ImagePanel extends JPanel {

    private Image image;
    private Point startPoint;
    private Point endPoint;

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
        repaint();
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image != null) {
            // Draw the image
            g.drawImage(image, 0, 0, this);

            // Draw the line if start and end points are set
            if (startPoint != null && endPoint != null) {
                g.setColor(Color.RED);
                g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            }
        }
    }
}


*/
