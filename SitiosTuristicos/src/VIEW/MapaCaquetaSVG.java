import org.apache.batik.anim.dom.SAXSVGDocumentFactory;
import org.apache.batik.bridge.BridgeContext;
import org.apache.batik.bridge.DocumentLoader;
import org.apache.batik.bridge.GVTBuilder;
import org.apache.batik.bridge.UserAgentAdapter;
import org.apache.batik.dom.svg.SVGDocumentFactory;
import org.apache.batik.gvt.GraphicsNode;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.svg.SVGDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MapaCaquetaSVG extends JFrame {
    
    private JSVGCanvas svgCanvas;
    private SVGDocument svgDocument;
    private Map<String, String> idToMunicipio = new HashMap<>();
    private JLabel statusLabel;
    private Element highlightedElement = null;
    private String originalFill = null;
    
    public MapaCaquetaSVG(String svgFilePath) {
        super("Mapa Interactivo del Caquetá - SVG");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Inicializar el mapa de IDs a nombres de municipios
        initMunicipioMap();
        
        // Configurar el lienzo SVG
        svgCanvas = new JSVGCanvas();
        svgCanvas.setDocumentState(JSVGCanvas.ALWAYS_DYNAMIC);
        
        // Configurar la etiqueta de estado
        statusLabel = new JLabel("Municipio: ");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Añadir componentes al frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(svgCanvas, BorderLayout.CENTER);
        getContentPane().add(statusLabel, BorderLayout.SOUTH);
        
        // Evento para saber cuando se ha cargado el SVG
        svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            @Override
            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
                // SVG completamente cargado y renderizado
                setupInteractions();
            }
        });
        
        // Cargar el documento SVG
        try {
            loadSVG(svgFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                    "Error al cargar el archivo SVG: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Configurar el tamaño y posición de la ventana
        setSize(800, 600);
        setLocationRelativeTo(null);
    }
    
    private void initMunicipioMap() {
        // Mapear los IDs del SVG a nombres de municipios
        // Necesitarás ajustar estos IDs según la estructura de tu SVG
        idToMunicipio.put("solano", "Solano");
        idToMunicipio.put("cartagena", "Cartagena del Chairá");
        idToMunicipio.put("sanvicente", "San Vicente del Caguán");
        idToMunicipio.put("puertorico", "Puerto Rico");
        idToMunicipio.put("florencia", "Florencia");
        idToMunicipio.put("montanita", "Montañita");
        idToMunicipio.put("sanjose", "San José del Fragua");
        idToMunicipio.put("morelia", "Morelia");
        // Añade más municipios según necesites
    }
    
    private void loadSVG(String svgFilePath) throws IOException {
        // Configurar el parser para SVG
        String parser = XMLResourceDescriptor.getXMLParserClassName();
        SVGDocumentFactory factory = new SAXSVGDocumentFactory(parser);
        
        // Cargar el documento SVG
        URI uri = new File(svgFilePath).toURI();
        svgDocument = (SVGDocument) factory.createDocument(uri.toString());
        
        // Establecer el documento en el canvas
        svgCanvas.setSVGDocument(svgDocument);
    }
    
    private void setupInteractions() {
        svgCanvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleMouseClick(e.getX(), e.getY());
            }
        });
        
        svgCanvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMove(e.getX(), e.getY());
            }
        });
    }
    
    private void handleMouseMove(int x, int y) {
        GraphicsNode node = svgCanvas.pickClosest(x, y, null);
        if (node != null) {
            Object obj = node.getUserData();
            if (obj instanceof Element) {
                Element element = (Element) obj;
                String id = element.getAttribute("id");
                String municipio = idToMunicipio.get(id);
                
                if (municipio != null) {
                    // Resaltar este elemento
                    highlightElement(element);
                    statusLabel.setText("Municipio: " + municipio);
                    return;
                }
            }
        }
        
        // Si llegamos aquí, no estamos sobre un municipio
        if (highlightedElement != null) {
            restoreOriginalStyle();
        }
        statusLabel.setText("Municipio: ");
    }
    
    private void handleMouseClick(int x, int y) {
        GraphicsNode node = svgCanvas.pickClosest(x, y, null);
        if (node != null) {
            Object obj = node.getUserData();
            if (obj instanceof Element) {
                Element element = (Element) obj;
                String id = element.getAttribute("id");
                // Si necesitas hacer algo al hacer clic, como mostrar información adicional
                System.out.println("Elemento clickeado: " + id);
            }
        }
    }
    
    private void highlightElement(Element element) {
        // Si ya hay un elemento resaltado, restaurar su estilo original
        if (highlightedElement != null && highlightedElement != element) {
            restoreOriginalStyle();
        }
        
        // Resaltar el nuevo elemento
        if (highlightedElement != element) {
            highlightedElement = element;
            originalFill = element.getAttribute("fill");
            element.setAttribute("fill", "#FF6464"); // Color de resaltado
            svgCanvas.repaint();
        }
    }
    
    private void restoreOriginalStyle() {
        if (highlightedElement != null && originalFill != null) {
            highlightedElement.setAttribute("fill", originalFill);
            highlightedElement = null;
            originalFill = null;
            svgCanvas.repaint();
        }
    }
    
    public static void main(String[] args) {
        // Asegúrate de cambiar la ruta al archivo SVG
        String svgFilePath = "C:\\Users\\User\\Documents\\NetBeansProjects\\SitiosTuristicos\\src\\img\\mapa_caqueta.svg";
        
        SwingUtilities.invokeLater(() -> {
            try {
                // Establecer el look and feel del sistema
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            MapaCaquetaSVG app = new MapaCaquetaSVG(svgFilePath);
            app.setVisible(true);
        });
    }
} 