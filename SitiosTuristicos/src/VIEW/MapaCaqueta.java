import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class MapaCaqueta extends JPanel {
    
    private BufferedImage mapImage;
    private BufferedImage bordersImage; // Imagen procesada para detectar bordes
    private Map<Point, String> puntosDeMunicipio = new HashMap<>();
    private String municipioActual = null;
    private BufferedImage highlightedImage;
    private final Color HIGHLIGHT_COLOR = new Color(255, 0, 0, 100); // Rojo semitransparente
    
    public MapaCaqueta() {
        try {
            // Carga la imagen del mapa
            mapImage = ImageIO.read(new File("C:\\Users\\User\\Documents\\NetBeansProjects\\SitiosTuristicos\\src\\img\\MUNICIPIOS (1).png"));
            highlightedImage = new BufferedImage(mapImage.getWidth(), mapImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
            
            // Procesa la imagen para detectar bordes
            bordersImage = detectarBordes(mapImage);
            
            setPreferredSize(new Dimension(mapImage.getWidth(), mapImage.getHeight()));
            
            // Inicializa los puntos de referencia para cada municipio
            // MEJORA: Agregar más puntos por municipio para regiones delgadas
           puntosDeMunicipio.put(new Point(289, 139), "El Doncello");
            puntosDeMunicipio.put(new Point(295, 239), "El Doncello"); // Punto adicional
            puntosDeMunicipio.put(new Point(297, 178), "El Doncello"); // Punto adicional
            
            puntosDeMunicipio.put(new Point(257, 223), "Paujil");
            puntosDeMunicipio.put(new Point(240, 220), "Paujil"); // Punto adicional
            puntosDeMunicipio.put(new Point(265, 225), "Paujil"); // Punto adicional
            
            puntosDeMunicipio.put(new Point(312, 241), "Florencia");
            puntosDeMunicipio.put(new Point(273, 280), "Belen");
            puntosDeMunicipio.put(new Point(329, 322), "Milan");
            puntosDeMunicipio.put(new Point(418, 331), "Cartagena del Chaira");
            
            puntosDeMunicipio.put(new Point(225, 210), "La Montañita"); // Agregar La Montañita
            puntosDeMunicipio.put(new Point(235, 205), "La Montañita"); // Punto adicional
            
            // Añade más puntos según necesites
            
            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    updateHighlight(e.getX(), e.getY());
                }
            });
            
            // Herramienta para definir puntos de referencia
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.isControlDown() && e.isShiftDown()) {
                        String nombre = JOptionPane.showInputDialog(
                            "Nombre del municipio para el punto (" + e.getX() + "," + e.getY() + "):");
                        if (nombre != null && !nombre.trim().isEmpty()) {
                            puntosDeMunicipio.put(new Point(e.getX(), e.getY()), nombre);
                            JOptionPane.showMessageDialog(null, "Punto para " + nombre + " registrado.");
                        }
                        // MEJORA: Agregar opción para ver puntos actuales
                    } else if (e.isAltDown()) {
                        // Mostrar todos los puntos registrados
                        StringBuilder info = new StringBuilder("Puntos registrados:\n");
                        for (Map.Entry<Point, String> entry : puntosDeMunicipio.entrySet()) {
                            Point p = entry.getKey();
                            info.append(String.format("%s: (%d, %d)\n", entry.getValue(), p.x, p.y));
                        }
                        JOptionPane.showMessageDialog(null, info.toString());
                    }
                }
            });
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private BufferedImage detectarBordes(BufferedImage imagen) {
        int width = imagen.getWidth();
        int height = imagen.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // MEJORA: Reducir el umbral para detectar bordes más suavemente
        int umbral = 60; // Valor original: 80
        
        // Matriz para el operador de Sobel (detección de bordes)
        int[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                // Aplicar operador Sobel
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
                
                // Magnitud del gradiente
                int magnitude = (int)Math.sqrt(gx * gx + gy * gy);
                magnitude = Math.min(255, magnitude);
                
                // Umbral para detectar bordes
                if (magnitude > umbral) { // Umbral ajustado
                    result.setRGB(x, y, new Color(0, 0, 0).getRGB());
                } else {
                    result.setRGB(x, y, new Color(255, 255, 255, 0).getRGB()); // Transparente
                }
            }
        }
        
        return result;
    }
    
    private void updateHighlight(int x, int y) {
        if (mapImage != null && x >= 0 && y >= 0 && x < mapImage.getWidth() && y < mapImage.getHeight()) {
            // Comprueba si el punto está dentro de un municipio mediante la detección de bordes
            String newMunicipio = encontrarMunicipio(x, y);
        
        if ((newMunicipio == null && municipioActual != null) || 
            (newMunicipio != null && !newMunicipio.equals(municipioActual))) {
            municipioActual = newMunicipio;
            highlightRegion();
            repaint();
            }
        }
    }
    
    private String encontrarMunicipio(int x, int y) {
        // Busca el punto de referencia más cercano que no esté separado por un borde
        String resultado = null;
        double distanciaMinima = Double.MAX_VALUE;
        
        for (Map.Entry<Point, String> entry : puntosDeMunicipio.entrySet()) {
            Point punto = entry.getKey();
            
            // Verifica si hay un borde entre el punto actual y el punto de referencia
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
        // Algoritmo de Bresenham para trazar una línea entre dos puntos
        // y verificar si cruza algún borde
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        
        // MEJORA: Verificar menos puntos en la línea para permitir "saltar" pequeños bordes
        int saltoPuntos = 2; // Verificar cada 2 puntos en lugar de cada punto
        int contador = 0;
        
        while (x1 != x2 || y1 != y2) {
            contador++;
            
            // Solo verificar cada 'saltoPuntos'
            if (contador % saltoPuntos == 0) {
                // Verifica si este punto es un borde
                if (x1 >= 0 && y1 >= 0 && x1 < bordersImage.getWidth() && y1 < bordersImage.getHeight()) {
                    int rgb = bordersImage.getRGB(x1, y1);
                    if (rgb == Color.BLACK.getRGB()) {
                        return true; // Encontró un borde
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
        
        return false; // No hay borde entre los puntos
    }
    
    private void highlightRegion() {
        // Limpia la imagen de resaltado
        Graphics2D g2d = highlightedImage.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);
        g2d.fillRect(0, 0, highlightedImage.getWidth(), highlightedImage.getHeight());
        g2d.dispose();
        
        if (municipioActual == null) {
            return;
        }
        
        // Encuentra todos los puntos de referencia para el municipio actual
        // MEJORA: Usar todos los puntos del mismo municipio como semillas
        for (Map.Entry<Point, String> entry : puntosDeMunicipio.entrySet()) {
            if (entry.getValue().equals(municipioActual)) {
                Point semilla = entry.getKey();
                // Usa flood fill para rellenar la región delimitada por bordes
                floodFillRegion(semilla.x, semilla.y);
            }
        }
    }
    
    private void floodFillRegion(int x, int y) {
        int width = mapImage.getWidth();
        int height = mapImage.getHeight();
        boolean[][] visitado = new boolean[width][height];
        
        // Cola para el algoritmo de flood fill
        java.util.Queue<Point> queue = new java.util.LinkedList<>();
        queue.add(new Point(x, y));
        visitado[x][y] = true;
        
        while (!queue.isEmpty()) {
            Point p = queue.remove();
            
            // Colorea este punto
            highlightedImage.setRGB(p.x, p.y, HIGHLIGHT_COLOR.getRGB());
            
            // MEJORA: Verificar 8 direcciones en lugar de 4 para mejor relleno
            int[][] dirs = {
                {0, -1}, {1, -1}, {1, 0}, {1, 1}, 
                {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}
            };
            
            for (int[] dir : dirs) {
                int nx = p.x + dir[0];
                int ny = p.y + dir[1];
                
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && !visitado[nx][ny]) {
                    // Si este punto no es un borde, añádelo a la cola
                    int rgb = bordersImage.getRGB(nx, ny);
                    if (rgb != Color.BLACK.getRGB()) {
                        queue.add(new Point(nx, ny));
                        visitado[nx][ny] = true;
                    }
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mapImage != null) {
            g.drawImage(mapImage, 0, 0, this);
            
            // Opcionalmente, muestra la imagen de bordes para depuración
            // g.drawImage(bordersImage, 0, 0, this);
            
            g.drawImage(highlightedImage, 0, 0, this);
            
            if (municipioActual != null) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 14));
                g.drawString("Municipio: " + municipioActual, 20, 30);
            }
            
            // Para depuración: mostrar los puntos de referencia (comentar en producción)
            /*
            g.setColor(Color.BLUE);
            for (Point p : puntosDeMunicipio.keySet()) {
                g.fillOval(p.x - 3, p.y - 3, 6, 6);
            }
            */
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Mapa Interactivo del Caquetá");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MapaCaqueta mapa = new MapaCaqueta();
            frame.getContentPane().add(mapa);
            frame.pack();
            frame.setLocationRelativeTo(null);
            
            // Añade instrucciones en el título
            JLabel instrucciones = new JLabel(
                "<html>Ctrl+Shift+Clic para definir puntos de municipios<br>" + 
                "Alt+Clic para ver puntos registrados<br>" +
                "Mueva el cursor sobre el mapa para resaltar municipios</html>");
            instrucciones.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            frame.getContentPane().add(instrucciones, BorderLayout.NORTH);
            
            frame.setVisible(true);
        });
    }
}

