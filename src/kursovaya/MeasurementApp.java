package kursovaya;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Line2D;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ArrayList;


public class MeasurementApp extends JFrame {

    private JButton loadImageButton; // Добавляем кнопку для загрузки изображения
    private JButton clearButton; // Добавляем кнопку для сброса измерения
    private JTextField scalingFactorField;
    private JLabel resultLabel;
    private ImagePanel imagePanel;


    private Point startPoint;
    private Point endPoint;

    public MeasurementApp() {
        setTitle("Medical Image Measurement App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Инициализация UI компонентов
        loadImageButton = new JButton("Load Image"); // Создание кнопки для загрузки изображения
        clearButton = new JButton("Clear Measurement"); // Создание кнопки для сброса измерения
        scalingFactorField = new JTextField(10);
        resultLabel = new JLabel("Measured length: ");
        imagePanel = new ImagePanel(scalingFactorField);


        // Layout
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Scaling Factor (pixels to mm):"));
        controlPanel.add(scalingFactorField);
        controlPanel.add(loadImageButton); // кнопка для загрузки изображения
        controlPanel.add(clearButton); // кнопка для сброса измерения
        controlPanel.add(resultLabel);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(controlPanel, BorderLayout.NORTH);
        contentPane.add(imagePanel, BorderLayout.CENTER);

        // Attach event listeners (для реагирования на действия пользователя.)
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMeasurement();
            }
        });

        // Прикрепить мышь к панели изображений(JFrame), чтобы получить начальную и конечную точки.
        // Добавление MouseAdapter к JFrame
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
//            Когда происходит клик мыши, будет вызван метод mouseClicked из MouseAdapter.
            public void mouseClicked(MouseEvent e) {
                if (startPoint == null) {
                    startPoint = e.getPoint();
                    resultLabel.setText("Start point selected.");
                } else if (endPoint == null) {
                    endPoint = e.getPoint();
                    resultLabel.setText("Measurement complete. Length: " + getMeasurementResult() + " mm");
                }
            }
        });
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Загрузка изображения из выбранного файла
                Image loadedImage = ImageIO.read(selectedFile);

                if (loadedImage != null) {
                    // Изображение успешно загружено
                    imagePanel.setImage(loadedImage);
                    imagePanel.setStartPoint(null);
                    imagePanel.setEndPoint(null);
                    resultLabel.setText("Measured length: ");
                } else {
                    // Изображение загружено неуспешно
                    JOptionPane.showMessageDialog(this, "Failed to load the image.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                // Исключение во время загрузки изображения (Exception occurred during image loading)
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clearMeasurement() {
        startPoint = null;
        endPoint = null;
        imagePanel.setImage(null);
        scalingFactorField.setText("");
        resultLabel.setText("Measured length: ");
        imagePanel.repaint();
    }


    private double getMeasurementResult() {
        if (startPoint != null && endPoint != null) {
            String scalingFactorText = scalingFactorField.getText().trim();

            if (!scalingFactorText.isEmpty()) {
                try {
                    double scalingFactor = Double.parseDouble(scalingFactorText);
                    double pixelLength = startPoint.distance(endPoint);
                    double millimeterLength = pixelLength * scalingFactor;
                    return millimeterLength;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid scaling factor. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0.0;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a scaling factor.", "Error", JOptionPane.ERROR_MESSAGE);
                return 0.0;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select both start and end points.", "Error", JOptionPane.ERROR_MESSAGE);
            return 0.0;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MeasurementApp app = new MeasurementApp();
                app.setVisible(true);
            }
        });
    }
}









































/*package kursovaya;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.imageio.ImageIO;

public class MeasurementApp extends JFrame {

    private JButton loadImageButton;
    private JTextField scalingFactorField;
    private JLabel resultLabel;
    private ImagePanel imagePanel;

    private Point startPoint;
    private Point endPoint;

    public MeasurementApp() {
        setTitle("Medical Image Measurement App");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize UI components
        loadImageButton = new JButton("Load Image");
        scalingFactorField = new JTextField(10);
        resultLabel = new JLabel("Measured length: ");
        imagePanel = new ImagePanel();

        // Layout
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Scaling Factor (pixels to mm):"));
        controlPanel.add(scalingFactorField);
        controlPanel.add(loadImageButton);
        controlPanel.add(resultLabel);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(controlPanel, BorderLayout.NORTH);
        contentPane.add(imagePanel, BorderLayout.CENTER);

        // Attach event listeners
        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage();
            }
        });

        // Attach mouse listener for image panel to get the start and end points
        imagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (startPoint == null) {
                    startPoint = e.getPoint();
                    resultLabel.setText("Start point selected.");
                } else if (endPoint == null) {
                    endPoint = e.getPoint();
                    resultLabel.setText("Measurement complete. Length: " + getMeasurementResult() + " mm");
                }
            }
        });
    }

    private void loadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                // Load the image from the selected file
                Image loadedImage = ImageIO.read(selectedFile);
                imagePanel.setImage(loadedImage);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double getMeasurementResult() {
        if (startPoint != null && endPoint != null) {
            String scalingFactorText = scalingFactorField.getText().trim();

            if (!scalingFactorText.isEmpty()) {
                try {
                    double scalingFactor = Double.parseDouble(scalingFactorText);
                    double pixelLength = startPoint.distance(endPoint);
                    double millimeterLength = pixelLength * scalingFactor;
                    return millimeterLength;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "Invalid scaling factor. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0.0;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a scaling factor.", "Error", JOptionPane.ERROR_MESSAGE);
                return 0.0;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select both start and end points.", "Error", JOptionPane.ERROR_MESSAGE);
            return 0.0;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MeasurementApp app = new MeasurementApp();
                app.setVisible(true);
            }
        });
    }
}*/
