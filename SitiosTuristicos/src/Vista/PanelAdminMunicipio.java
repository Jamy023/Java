/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author User
 */
import Controlador.ControladorTurismo;
import Modelo.SitioInteres;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import Vista.EditarSitioView;
/**
 * Panel de administración de municipios que se muestra como un JFrame independiente.
 */
public class PanelAdminMunicipio extends JFrame {
    
    private final Color COLOR_PRIMARY = new Color(56, 142, 60);
    private final Color COLOR_PRIMARY_DARK = new Color(27, 94, 32);
    private final Color COLOR_PRIMARY_LIGHT = new Color(129, 199, 132);
    private final Color COLOR_TEXT_LIGHT = new Color(0,0,0);
    private final Color COLOR_BACKGROUND = new Color(245, 245, 245);
    private final Color COLOR_CARD = Color.WHITE;
    private final Color COLOR_HOVER = new Color(200, 230, 201);
    
    private String nombreMunicipio;
    private ControladorTurismo controlador;
    private JTabbedPane tabbedPane;
    
    /**
     * Constructor para crear el panel como un JFrame independiente
     * @param nombreMunicipio Nombre del municipio a administrar
     */
    public PanelAdminMunicipio(String nombreMunicipio ) {
        this.nombreMunicipio = nombreMunicipio;


        this.controlador = new ControladorTurismo();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Configuración del JFrame
        setTitle("Administración de " + nombreMunicipio);
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(COLOR_BACKGROUND);
        
        initComponents();
        cargarDatos();
    }
    
    private void initComponents() {
        cargarDatos();
        setUndecorated(true);
        // Panel superior con título
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_PRIMARY_DARK);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("SITIOS TURÍSTICOS DE " + nombreMunicipio.toUpperCase());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(COLOR_TEXT_LIGHT);
        
        JButton closeButton = new JButton("✖");
        closeButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setForeground(COLOR_TEXT_LIGHT);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout(0, 20));
        mainPanel.setBackground(COLOR_BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel resumen
        JPanel resumenPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        resumenPanel.setOpaque(false);
        
        // Las tarjetas de resumen se llenarán con datos en cargarDatos()
        JPanel card1 = createSummaryCard("Sitios sin servicios", "0", COLOR_PRIMARY);
        resumenPanel.add(card1);
        
        JPanel card2 = createSummaryCard("Sitios con alojamiento", "0", new Color(33, 150, 243));
        resumenPanel.add(card2);
        
        JPanel card3 = createSummaryCard("Sitios con todo", "0", new Color(230, 74, 25));
        resumenPanel.add(card3);
        
        mainPanel.add(resumenPanel, BorderLayout.NORTH);
        
        // Panel de pestañas para los diferentes municipios
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(COLOR_BACKGROUND);
        tabbedPane.setForeground(COLOR_PRIMARY_DARK);
        
        // Primero mostramos la pestaña del municipio actual
        JPanel tabMunicipio = createTablePanel(nombreMunicipio);
        tabbedPane.addTab(nombreMunicipio, tabMunicipio);
        
        // Luego agregamos las pestañas para los otros municipios
        for (String otroMunicipio : new String[]{"Florencia", "El Doncello", "Puerto rico"}) {
            if (!otroMunicipio.equals(nombreMunicipio)) {
                JPanel tabOtroMunicipio = createTablePanel(otroMunicipio);
                tabbedPane.addTab(otroMunicipio, tabOtroMunicipio);
            }
        }
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Panel de acciones inferior
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.setBackground(COLOR_BACKGROUND);
        
        JButton closeBtn = new JButton("Cerrar");
        closeBtn.setBackground(COLOR_PRIMARY);
        closeBtn.setForeground(COLOR_TEXT_LIGHT);
        closeBtn.setBorderPainted(false);
        closeBtn.setFocusPainted(false);
        closeBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        closeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeBtn.addActionListener(e -> dispose());
        
        actionPanel.add(closeBtn);
        
        // Añadir componentes al frame principal
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }
    
    public void cargarDatos() {
        try {
            // Cargar estadísticas del municipio actual
            int[] estadisticas = controlador.obtenerEstadisticasMunicipio(nombreMunicipio);
            
            // Actualizar tarjetas de resumen
            JPanel resumenPanel = (JPanel) ((JPanel) getContentPane().getComponent(1)).getComponent(0);
            
            updateSummaryCard((JPanel) resumenPanel.getComponent(0), "Sitios sin servicios", 
                    String.valueOf(estadisticas[0]), COLOR_PRIMARY);
            
            updateSummaryCard((JPanel) resumenPanel.getComponent(1), "Sitios con alojamiento", 
                    String.valueOf(estadisticas[1]), new Color(33, 150, 243));
            
            updateSummaryCard((JPanel) resumenPanel.getComponent(2), "Sitios con todo", 
                    String.valueOf(estadisticas[2]), new Color(230, 74, 25));
            
            // Cargar datos para cada pestaña
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                String municipioName = tabbedPane.getTitleAt(i);
                Component tabComponent = tabbedPane.getComponentAt(i);
                
                if (tabComponent instanceof JPanel) {
                    JPanel tabPanel = (JPanel) tabComponent;
                    Component centerComponent = tabPanel.getComponent(1);
                    
                    if (centerComponent instanceof JScrollPane) {
                        JScrollPane scrollPane = (JScrollPane) centerComponent;
                        JTable tabla = (JTable) scrollPane.getViewport().getView();
                        DefaultTableModel model = (DefaultTableModel) tabla.getModel();
                        
                        // Limpiar tabla
                        model.setRowCount(0);
                        
                        // Cargar datos desde el controlador
                        List<SitioInteres> sitios = controlador.obtenerSitiosPorMunicipio(municipioName);
                        for (SitioInteres sitio : sitios) {
                            model.addRow(new Object[]{
                                String.valueOf(sitio.getId()),
                                sitio.getNombreSitio(),
                                sitio.getTipoSitio(),
                                sitio.getDistancia() + " km",
                                "Editar"
                            });
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private JPanel createSummaryCard(String title, String count, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(COLOR_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, COLOR_CARD),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(color);
        
        JLabel countLabel = new JLabel(count);
        countLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        countLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.setOpaque(false);
        innerPanel.add(titleLabel, BorderLayout.NORTH);
        innerPanel.add(countLabel, BorderLayout.EAST);
        
        card.add(innerPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private void updateSummaryCard(JPanel card, String title, String count, Color color) {
        JPanel innerPanel = (JPanel) card.getComponent(0);
        JLabel titleLabel = (JLabel) innerPanel.getComponent(0);
        JLabel countLabel = (JLabel) innerPanel.getComponent(1);
        
        titleLabel.setText(title);
        titleLabel.setForeground(color);
        countLabel.setText(count);
    }
    
    private JPanel createTablePanel(String municipioNombre) {
        JPanel tablePanel = new JPanel(new BorderLayout(0, 0));
        tablePanel.setBackground(COLOR_CARD);
        tablePanel.setBorder(new RoundedBorder(10, COLOR_CARD));
        
        // Crear modelo de tabla
        String[] columnNames = {"ID", "Nombre del Sitio", "Tipo", "Distancia", "Acciones"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Solo la columna de acciones es editable
            }
        };
        
        // Crear tabla con estilo mejorado
        JTable tablaSitios = new JTable(tableModel);
        tablaSitios.setRowHeight(40);
        tablaSitios.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaSitios.setSelectionBackground(COLOR_PRIMARY_LIGHT);
        tablaSitios.setSelectionForeground(COLOR_TEXT_LIGHT);
        tablaSitios.setShowGrid(false);
        tablaSitios.setIntercellSpacing(new Dimension(0, 0));
        tablaSitios.setFillsViewportHeight(true);
        tablaSitios.setBackground(COLOR_CARD);
        
        // Personalizar encabezado de tabla
        JTableHeader header = tablaSitios.getTableHeader();
        header.setBackground(COLOR_PRIMARY);
        header.setForeground(COLOR_TEXT_LIGHT);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBorder(BorderFactory.createEmptyBorder());
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        // Personalizar celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        
        tablaSitios.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tablaSitios.getColumnModel().getColumn(0).setMaxWidth(50);
        tablaSitios.getColumnModel().getColumn(0).setPreferredWidth(50);
        
        tablaSitios.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tablaSitios.getColumnModel().getColumn(2).setPreferredWidth(100);
        
        tablaSitios.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tablaSitios.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        tablaSitios.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tablaSitios.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
        tablaSitios.getColumnModel().getColumn(4).setMaxWidth(100);
        tablaSitios.getColumnModel().getColumn(4).setPreferredWidth(100);
        
        // Scroll pane con borde
        JScrollPane scrollPane = new JScrollPane(tablaSitios);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(COLOR_CARD);
        scrollPane.getViewport().setBackground(COLOR_CARD);
        
        // Panel de título de la tabla
        JPanel tableHeaderPanel = new JPanel(new BorderLayout());
        tableHeaderPanel.setBackground(COLOR_CARD);
        tableHeaderPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        
        JLabel tableTitle = new JLabel("Sitios Turísticos de " + municipioNombre);
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(COLOR_PRIMARY_DARK);
        
        // Botón para agregar nuevo sitio
        JButton addButton = new JButton("");
        addButton.setBackground(COLOR_PRIMARY);
        addButton.setForeground(COLOR_TEXT_LIGHT);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        addButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(e -> {
            // Implementar lógica para agregar nuevo sitio
            System.out.println("Agregar nuevo sitio a " + municipioNombre);
            // Aquí iría la lógica para mostrar un formulario de nuevo sitio
        });
        
        
        tableHeaderPanel.add(tableTitle, BorderLayout.WEST);
     
        
        // Añadir a panel principal
        tablePanel.add(tableHeaderPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        return tablePanel;
    }
    
    // Clase para renderizar botones en la tabla
    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            setBorderPainted(false);
            setBackground(COLOR_PRIMARY_LIGHT);
            setForeground(COLOR_PRIMARY_DARK);
            setFocusPainted(false);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }
    
    // Editor para los botones en la tabla
// Editor para los botones en la tabla
private class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JTable table;
    
    public ButtonEditor(JCheckBox checkBox) {
        super(checkBox);
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> {
            fireEditingStopped();
        });
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.table = table;
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        button.setBackground(COLOR_PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        isPushed = true;
        return button;
    }
    
   @Override
public Object getCellEditorValue() {
    if (isPushed) {
        try {
            // Obtener el ID del sitio de la fila seleccionada
            int row = table.getSelectedRow();
            // Obtener el valor como String y convertirlo a Integer
            String idStr = table.getValueAt(row, 0).toString();
            int idSitio = Integer.parseInt(idStr);
            String nombreMunicipio = ""; // Reemplaza esto con el código para obtener el municipio del usuario
            
            // Crear y mostrar la ventana de edición
            EditarSitioView ventanaEdicion = new EditarSitioView(controlador, idSitio , PanelAdminMunicipio.this
                                                                                                                    );
            ventanaEdicion.setVisible(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(button, 
                "Error: El ID del sitio no es un número válido.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(button, 
                "Error al abrir la ventana de edición: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    isPushed = false;
    return label;
}

    
@Override
public boolean stopCellEditing() {
    isPushed = false;
    if ("Editar".equals(label)) {
        // Obtener el ID del sitio desde la tabla
        int row = table.getSelectedRow();
        int sitioId = (int) table.getValueAt(row, 0); // Asumiendo que el ID está en la columna 0
        
        // Abrir la ventana de ediciónsitioId
        EditarSitioView editarView = newEditarSitioView(controlador, sitioId , PanelAdminMunicipio.this);
                                                                                                                  
        editarView.setVisible(true);
    } else if ("Eliminar".equals(label)) {
        // Código para eliminar
    }
    return super.stopCellEditing();
}
}
    // Clase para bordes redondeados
    private class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;
        
        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius / 3, this.radius / 3, this.radius / 3, this.radius / 3);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }
    
    /**
     * Método main para probar el frame de manera independiente
     */
    public static void main(String[] args) {
        // Configuración del look and feel (opcional)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Crear y mostrar el frame
        java.awt.EventQueue.invokeLater(() -> {
            PanelAdminMunicipio panel = new PanelAdminMunicipio("Florencia");
            panel.setVisible(true);
        });
    }
}