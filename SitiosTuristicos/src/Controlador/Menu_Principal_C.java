// En MenuPrincipalControlador.java
package Controlador;


import Vista.Menu_Principal;
import Vista.Vista_Municipio_Florencia;
import Vista.VIsta_Municipio_Doncello;
import Vista.Vista_Municipio_Puerto_Rico;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Menu_Principal_C {
    private final Menu_Principal vista;
    private String municipioActual = null;
    private final Color HIGHLIGHT_COLOR = new Color(19, 144, 7);
    private BufferedImage mapImage;
    private BufferedImage bordersImage;
    private BufferedImage highlightedImage;
    private final Map<Point, String> puntosDeMunicipio = new HashMap<>();
    private int currentX, currentY;
    private boolean controlKeyPressed = false;
    
    // Panel flotante
    private JPanel panelFlotante;
    private JLabel labelNombreMunicipio;

    public Menu_Principal_C(Menu_Principal vista) {
        this.vista = vista;
        initializeMapa();
        setupKeyListener();
        crearPanelFlotante();
        setupEventListeners();
    }

    private void initializeMapa() {
        try {
            // Cargar la imagen del mapa
            mapImage = ImageIO.read(getClass().getResource("/img/mapa.png"));
            highlightedImage = new BufferedImage(mapImage.getWidth(), mapImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            
            // Procesar la imagen para detectar bordes
            bordersImage = detectarBordes(mapImage);
            
            // Definir los puntos de los municipios
            puntosDeMunicipio.put(new Point(785, 572), "Solano");
            puntosDeMunicipio.put(new Point(364, 570), "Solano");
            puntosDeMunicipio.put(new Point(207, 411), "Solano");

            puntosDeMunicipio.put(new Point(520, 372), "San Vicente del Caguán");
            puntosDeMunicipio.put(new Point(309, 135), "San Vicente del Caguán");

            puntosDeMunicipio.put(new Point(518, 509), "Cartagena del Chairá");
            puntosDeMunicipio.put(new Point(320, 350), "Cartagena del Chairá");

            puntosDeMunicipio.put(new Point(263, 207), "Puerto Rico");

            puntosDeMunicipio.put(new Point(228, 237), "El Doncello");
            puntosDeMunicipio.put(new Point(231, 152), "El Doncello");

            puntosDeMunicipio.put(new Point(219, 263), "El Paujil");
            puntosDeMunicipio.put(new Point(216, 168), "El Paujil");

            puntosDeMunicipio.put(new Point(219, 309), "La Montañita");
            puntosDeMunicipio.put(new Point(202, 191), "La Montañita");

            puntosDeMunicipio.put(new Point(166, 246), "Florencia");

            puntosDeMunicipio.put(new Point(109, 286), "Belén de los Andaquíes");

            puntosDeMunicipio.put(new Point(66, 332), "San José del Fragua");

            puntosDeMunicipio.put(new Point(95, 367), "Curillo");

            puntosDeMunicipio.put(new Point(113, 335), "Albania");

            puntosDeMunicipio.put(new Point(160, 359), "Valparaíso");

            puntosDeMunicipio.put(new Point(153, 310), "Morelia");

            puntosDeMunicipio.put(new Point(204, 353), "Milán");

            puntosDeMunicipio.put(new Point(148, 398), "Solita");
            
            // Configurar el JLabel para mostrar el mapa
            vista.jLabel6.setIcon(new ImageIcon(mapImage) {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    super.paintIcon(c, g, x, y);
                    if (highlightedImage != null) {
                        g.drawImage(highlightedImage, x, y, c);
                    }
                    
                    if (controlKeyPressed) {
                        g.setColor(Color.BLUE);
                        for (Point p : puntosDeMunicipio.keySet()) {
                            g.fillOval(p.x - 3, p.y - 3, 6, 6);
                        }
                    }
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupEventListeners() {
        // Mouse motion listener para el resaltado
        vista.jLabel6.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                currentX = e.getX();
                currentY = e.getY();
                updateHighlight();
            }
        });
        
        // Mouse listener para clicks
        vista.jLabel6.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isControlDown() && e.isShiftDown()) {
                    String nombre = JOptionPane.showInputDialog(
                        "Nombre del municipio para el punto (" + e.getX() + "," + e.getY() + "):");
                    if (nombre != null && !nombre.trim().isEmpty()) {
                        puntosDeMunicipio.put(new Point(e.getX(), e.getY()), nombre);
                        JOptionPane.showMessageDialog(null, "Punto para " + nombre + " registrado.");
                    }
                } else if (e.isAltDown()) {
                    StringBuilder info = new StringBuilder("Puntos registrados:\n");
                    for (Map.Entry<Point, String> entry : puntosDeMunicipio.entrySet()) {
                        Point p = entry.getKey();
                        info.append(String.format("%s: (%d, %d)\n", entry.getValue(), p.x, p.y));
                    }
                    JOptionPane.showMessageDialog(null, info.toString());
                } else {
                    String municipioSeleccionado = encontrarMunicipio(e.getX(), e.getY());
                    if (municipioSeleccionado != null) {
                        abrirVistaMunicipio(municipioSeleccionado);
                    }
                }
            }
        });
    }

    private void crearPanelFlotante() {
        panelFlotante = new JPanel();
        panelFlotante.setLayout(new java.awt.BorderLayout());
        panelFlotante.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 70, 200), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        panelFlotante.setBackground(new Color(0, 0, 0, 180));
        
        labelNombreMunicipio = new JLabel();
        labelNombreMunicipio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelNombreMunicipio.setForeground(Color.WHITE);
        
        panelFlotante.add(labelNombreMunicipio, java.awt.BorderLayout.CENTER);
        panelFlotante.setVisible(false);
        panelFlotante.setPreferredSize(new Dimension(150, 30));
        
        vista.getLayeredPane().add(panelFlotante, javax.swing.JLayeredPane.POPUP_LAYER);
    }

    private BufferedImage detectarBordes(BufferedImage imagen) {
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        int umbral = 50;
        int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int gx = 0;
                int gy = 0;
                
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = imagen.getRGB(x + i, y + j);
                        int gray = (int)(0.299 * ((rgb >> 16) & 0xFF) + 
                                     0.587 * ((rgb >> 8) & 0xFF) + 
                                     0.114 * (rgb & 0xFF));
                        
                        gx += gray * sobelX[i+1][j+1];
                        gy += gray * sobelY[i+1][j+1];
                    }
                }
                
                int magnitude = (int)Math.sqrt(gx * gx + gy * gy);
                magnitude = Math.min(255, magnitude);
                
                if (magnitude > umbral) {
                    result.setRGB(x, y, new Color(0, 0, 0).getRGB());
                } else {
                    result.setRGB(x, y, new Color(255, 255, 255, 0).getRGB());
                }
            }
        }
        
        return result;
    }

    private void updateHighlight() {
        if (mapImage != null && currentX >= 0 && currentY >= 0 &&
            currentX < mapImage.getWidth() && currentY < mapImage.getHeight()) {
            
            String newMunicipio = encontrarMunicipio(currentX, currentY);
            
            if ((newMunicipio == null && municipioActual != null) || 
                (newMunicipio != null && !newMunicipio.equals(municipioActual))) {
                municipioActual = newMunicipio;
                highlightRegion();
                vista.jLabel6.repaint();
                
                if (municipioActual != null) {
                    labelNombreMunicipio.setText(municipioActual);
                    panelFlotante.setVisible(true);
                    
                    Point labelPos = vista.jLabel6.getLocationOnScreen();
                    int panelX = currentX + 20;
                    int panelY = currentY + 20;
                    
                    Point convertedPoint = SwingUtilities.convertPoint(vista.jLabel6, panelX, panelY, vista.getLayeredPane());
                    
                    Dimension panelSize = panelFlotante.getPreferredSize();
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Point framePos = vista.getLocationOnScreen();
                    
                    if (labelPos.x + panelX + panelSize.width > framePos.x + vista.getWidth()) {
                        panelX = currentX - panelSize.width - 20;
                    }
                    if (labelPos.y + panelY + panelSize.height > framePos.y + vista.getHeight()) {
                        panelY = currentY - panelSize.height - 20;
                    }
                    
                    panelFlotante.setBounds(
                        convertedPoint.x, 
                        convertedPoint.y, 
                        panelSize.width, 
                        panelSize.height
                    );
                    
                    panelFlotante.revalidate();
                    panelFlotante.repaint();
                } else {
                    panelFlotante.setVisible(false);
                }
            }
        }
    }

    private String encontrarMunicipio(int x, int y) {
        String resultado = null;
        double distanciaMinima = Double.MAX_VALUE;
        
        for (Map.Entry<Point, String> entry : puntosDeMunicipio.entrySet()) {
            Point punto = entry.getKey();
            
            if (!hayBordeEntre(x, y, punto.x, punto.y)) {
                double distancia = punto.distance(x, y);
                if (distancia < distanciaMinima) {
                    distanciaMinima = distancia;
                    resultado = entry.getValue();
                }
            }
        }
        
        return resultado;
    }

    private boolean hayBordeEntre(int x1, int y1, int x2, int y2) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        int saltoPuntos = 2;
        int contador = 0;
        
        while (x1 != x2 || y1 != y2) {
            contador++;
            
            if (contador % saltoPuntos == 0) {
                if (x1 >= 0 && y1 >= 0 && x1 < bordersImage.getWidth() && y1 < bordersImage.getHeight()) {
                    int rgb = bordersImage.getRGB(x1, y1);
                    if (rgb == Color.BLACK.getRGB()) {
                        return true;
                    }
                }
            }
            
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
        
        return false;
    }

    private void highlightRegion() {
        Graphics2D g2d = highlightedImage.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, highlightedImage.getWidth(), highlightedImage.getHeight());
        g2d.dispose();
        
        if (municipioActual == null) {
            return;
        }
        
        for (Map.Entry<Point, String> entry : puntosDeMunicipio.entrySet()) {
            if (entry.getValue().equals(municipioActual)) {
                Point semilla = entry.getKey();
                floodFillRegion(semilla.x, semilla.y);
            }
        }
    }

    private void floodFillRegion(int x, int y) {
        int width = mapImage.getWidth();
        int height = mapImage.getHeight();
        boolean[][] visitado = new boolean[width][height];
        
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));
        visitado[x][y] = true;
        
        while (!queue.isEmpty()) {
            Point p = queue.remove();
            
            highlightedImage.setRGB(p.x, p.y, HIGHLIGHT_COLOR.getRGB());
            
            int[][] dirs = {
                {0, -1}, {1, -1}, {1, 0}, {1, 1}, 
                {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}
            };
            
            for (int[] dir : dirs) {
                int nx = p.x + dir[0];
                int ny = p.y + dir[1];
                
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visitado[nx][ny]) {
                    int rgb = bordersImage.getRGB(nx, ny);
                    if (rgb != Color.BLACK.getRGB()) {
                        queue.add(new Point(nx, ny));
                        visitado[nx][ny] = true;
                    }
                }
            }
        }
    }

    private void setupKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                controlKeyPressed = e.isControlDown();
                if (vista.jLabel6 != null) {
                    vista.jLabel6.repaint();
                }
                return false;
            }
        });
    }

    private void abrirVistaMunicipio(String nombreMunicipio) {
        vista.FondoDesenfoque.setVisible(true);
        
        WindowAdapter windowListener = new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                vista.FondoDesenfoque.setVisible(false);
            }
            
            @Override
            public void windowClosing(WindowEvent e) {
                vista.FondoDesenfoque.setVisible(false);
            }
        };
        
        switch (nombreMunicipio) {
            case "Florencia":
                Vista_Municipio_Florencia vistaFlorencia = new Vista_Municipio_Florencia();
                vistaFlorencia.addWindowListener(windowListener);
                vistaFlorencia.setVisible(true);
                break;
                
            case "El Doncello":
                VIsta_Municipio_Doncello vistaDoncello = new VIsta_Municipio_Doncello();
                vistaDoncello.addWindowListener(windowListener);
                vistaDoncello.setVisible(true);
                break;
                
            case "Puerto Rico":
                Vista_Municipio_Puerto_Rico vistaPuertoRico = new Vista_Municipio_Puerto_Rico();
                vistaPuertoRico.addWindowListener(windowListener);
                vistaPuertoRico.setVisible(true);
                break;
                
            default:
                JOptionPane.showMessageDialog(vista, 
                    "No hay información detallada disponible para " + nombreMunicipio, 
                    "Información no disponible", 
                    JOptionPane.INFORMATION_MESSAGE);
                vista.FondoDesenfoque.setVisible(false);
                break;
        }
    }
}