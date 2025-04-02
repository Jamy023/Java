package Vista;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Utilidad para extraer información de regiones de un archivo SVG.
 * Esta clase te ayudará a identificar los IDs y otros atributos de los paths del SVG.
 */
public class SVGPathExtractor {

    public static void main(String[] args) {
        String svgFilePath = "C:\\Users\\User\\Documents\\NetBeansProjects\\SitiosTuristicos\\src\\img\\mapa_caqueta.svg";
        
        try {
            extractSVGPaths(svgFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void extractSVGPaths(String svgFilePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(svgFilePath));
        
        // Normalizar el documento
        document.getDocumentElement().normalize();
        
        // Buscar elementos de tipo path, polygon, o rect (comunes en mapas SVG)
        extractElements(document, "path");
        extractElements(document, "polygon");
        extractElements(document, "rect");
        extractElements(document, "g"); // Grupos que pueden contener regiones
    }
    
    private static void extractElements(Document document, String tagName) {
        NodeList nodeList = document.getElementsByTagName(tagName);
        System.out.println("\nElementos tipo '" + tagName + "' encontrados: " + nodeList.getLength());
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element element = (Element) nodeList.item(i);
            String id = element.getAttribute("id");
            String className = element.getAttribute("class");
            
            // Mostrar información del elemento
            System.out.println("Elemento #" + i);
            if (!id.isEmpty()) System.out.println("  ID: " + id);
            if (!className.isEmpty()) System.out.println("  Clase: " + className);
            
            // Si es un grupo, buscar hijos
            if (tagName.equals("g")) {
                NodeList children = element.getChildNodes();
                int childCount = 0;
                for (int j = 0; j < children.getLength(); j++) {
                    if (children.item(j) instanceof Element) {
                        childCount++;
                    }
                }
                System.out.println("  Contiene " + childCount + " elementos hijos");
            }
            
            System.out.println("---------------------------");
        }
    }
} 