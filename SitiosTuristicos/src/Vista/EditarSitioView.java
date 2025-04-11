package Vista;

import Controlador.ControladorTurismo;
import Vista.PanelAdminMunicipio;
import Modelo.Municipio;
import Modelo.SitioInteres;
import Modelo.TipoSitioInteres;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;


public class EditarSitioView extends JFrame {
    
    private final ControladorTurismo controlador;
    private SitioInteres sitioActual;
    private PanelAdminMunicipio panelPadre;

    
    // Componentes de la interfaz
    private JTextField txtNombre;
    private JTextArea txtDescripcion;
    private JTextField txtPrecio;
    private JTextField txtDistancia;
    private JComboBox<Municipio> cmbMunicipio;
    private JComboBox<TipoSitioInteres> cmbTipoSitio;
    private JCheckBox chkAlojamiento;
    private JCheckBox chkAlimentacion;
    private JCheckBox chkTransporte;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    // Colores para la interfaz
    private final Color COLOR_PRIMARIO = new Color(56, 142, 60);
    private final Color COLOR_SECUNDARIO = new Color(27, 94, 32);
    private final Color COLOR_FONDO = new Color(236, 240, 241);
    private final Color COLOR_TEXTO = new Color(44, 62, 80);
    private final Color COLOR_EXITO = new Color(44, 45, 44);


    
    /**
     * Constructor
     * @param controlador Controlador de turismo
     * @param idSitio ID del sitio a editar
     */
    public EditarSitioView(ControladorTurismo controlador, int idSitio ,PanelAdminMunicipio panelPadre) {
        super("Gestión de Sitios Turísticos");
        this.controlador = controlador;
        this.panelPadre = panelPadre;
        
        // Establecer look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al establecer look and feel: " + e.getMessage());
        }
        
        // Obtener el sitio a editar
        sitioActual = controlador.obtenerSitioPorId(idSitio);
        if (sitioActual == null) {
            JOptionPane.showMessageDialog(this, 
                "No se encontró el sitio con ID: " + idSitio, 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }
        
        inicializarComponentes();
        cargarDatosSitio();
        
        // Configurar ventana con mayor tamaño
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(750, 650));
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
  private void inicializarComponentes() {
    // Configurar ventana principal
    setTitle("Editar Sitio de Interés");
    setSize(700, 600);
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    getContentPane().setBackground(COLOR_FONDO);
    
    // Crear panel principal con margen
    JPanel panelPrincipal = new JPanel(new BorderLayout(15, 15));
    panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
    panelPrincipal.setBackground(COLOR_FONDO);
    
    // Crear título con estilo
    JLabel lblTitulo = new JLabel("Editar Sitio Turístico");
    lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
    lblTitulo.setForeground(COLOR_SECUNDARIO);
    lblTitulo.setHorizontalAlignment(JLabel.CENTER);
    lblTitulo.setBorder(new EmptyBorder(0, 0, 20, 0));
    
    // Panel para el formulario con GridBagLayout para más control
    JPanel panelFormulario = new JPanel(new GridBagLayout());
    panelFormulario.setBackground(COLOR_FONDO);
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.insets = new Insets(8, 8, 8, 8);
    constraints.anchor = GridBagConstraints.WEST;
    
    // Crear campos de texto
    txtNombre = crearCampoTexto(25);
    
    // Crear el área de texto para la descripción con mayor tamaño
    txtDescripcion = new JTextArea(3, 30); // 8 filas, 30 columnas
    txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    txtDescripcion.setLineWrap(true);
    txtDescripcion.setWrapStyleWord(true);
    txtDescripcion.setForeground(COLOR_TEXTO);
    txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(COLOR_PRIMARIO, 1, true),
        BorderFactory.createEmptyBorder(8, 8, 8, 8)
    ));
    
    // Agregar barras de desplazamiento al área de texto
    JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
    scrollDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollDescripcion.setBorder(null); // Quitar borde del scroll para mantener solo el del textarea
    scrollDescripcion.setPreferredSize(new Dimension(400, 150)); // Dimensiones sugeridas
    
    txtPrecio = crearCampoTexto(10);
    txtDistancia = crearCampoTexto(10);
    
    // Crear combos
    cmbMunicipio = crearComboBox();
    cmbTipoSitio = crearComboBox();
    
    // Crear checkboxes para servicios
    chkAlojamiento = crearCheckBox("Alojamiento");
    chkAlimentacion = crearCheckBox("Alimentación");
    chkTransporte = crearCheckBox("Transporte");
    
    // Agregar componentes al panel con GridBagLayout
    // Fila 1: Nombre
    constraints.gridx = 0;
    constraints.gridy = 0;
    panelFormulario.add(crearEtiqueta("Nombre:"), constraints);
    
    constraints.gridx = 1;
    constraints.gridwidth = 2;
    panelFormulario.add(txtNombre, constraints);
    constraints.gridwidth = 1;
    
    // Fila 2: Descripción
    constraints.gridx = 0;
    constraints.gridy = 1;
    panelFormulario.add(crearEtiqueta("Descripción:"), constraints);
    
    constraints.gridx = 1;
    constraints.gridwidth = 2;
    constraints.fill = GridBagConstraints.BOTH;
    constraints.weighty = 1.0; // Dar peso vertical para que se expanda verticalmente
    panelFormulario.add(scrollDescripcion, constraints);
    constraints.weighty = 0.0; // Restaurar el peso vertical predeterminado
    constraints.gridwidth = 1;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    
    // Fila 3: Precio
    constraints.gridx = 0;
    constraints.gridy = 2;
    panelFormulario.add(crearEtiqueta("Precio:"), constraints);
    
    constraints.gridx = 1;
    panelFormulario.add(txtPrecio, constraints);
    
    // Fila 4: Distancia
    constraints.gridx = 0;
    constraints.gridy = 3;
    panelFormulario.add(crearEtiqueta("Distancia (km):"), constraints);
    
    constraints.gridx = 1;
    panelFormulario.add(txtDistancia, constraints);
    
    // Fila 5: Municipio
    constraints.gridx = 0;
    constraints.gridy = 4;
    panelFormulario.add(crearEtiqueta("Municipio:"), constraints);
    
    constraints.gridx = 1;
    panelFormulario.add(cmbMunicipio, constraints);
    
    // Fila 6: Tipo de sitio
    constraints.gridx = 0;
    constraints.gridy = 5;
    panelFormulario.add(crearEtiqueta("Tipo de sitio:"), constraints);
    
    constraints.gridx = 1;
    panelFormulario.add(cmbTipoSitio, constraints);
    
    // Fila 7: Servicios (checkboxes)
    constraints.gridx = 0;
    constraints.gridy = 6;
    panelFormulario.add(crearEtiqueta("Servicios:"), constraints);
    
    JPanel panelServicios = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    panelServicios.setBackground(COLOR_FONDO);
    panelServicios.add(chkAlojamiento);
    panelServicios.add(chkAlimentacion);
    panelServicios.add(chkTransporte);
    
    constraints.gridx = 1;
    constraints.gridwidth = 2;
    panelFormulario.add(panelServicios, constraints);
    constraints.gridwidth = 1;
    
    // Panel para botones
    JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    panelBotones.setBackground(COLOR_FONDO);
    
    // Botón Cancelar
    btnCancelar = new JButton("Cancelar");
    btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btnCancelar.setForeground(Color.WHITE);
    btnCancelar.setBackground(new Color(231, 76, 60));
    btnCancelar.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(new Color(192, 57, 43), 1, true),
        new EmptyBorder(8, 15, 8, 15)
    ));
    btnCancelar.setFocusPainted(false);
    btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnCancelar.addActionListener(e -> dispose());
    configurarEfectoHover(btnCancelar, new Color(231, 76, 60), new Color(192, 57, 43));
    
    // Botón Guardar
    btnGuardar = new JButton("Guardar Cambios");
    btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 14));
    btnGuardar.setForeground(Color.WHITE);
    btnGuardar.setBackground(COLOR_PRIMARIO);
    btnGuardar.setBorder(BorderFactory.createCompoundBorder(
        new LineBorder(COLOR_SECUNDARIO, 1, true),
        new EmptyBorder(8, 15, 8, 15)
    ));
    btnGuardar.setFocusPainted(false);
    btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
    btnGuardar.addActionListener(this::guardarCambios);
    configurarEfectoHover(btnGuardar, COLOR_PRIMARIO, COLOR_SECUNDARIO);
    
    panelBotones.add(btnCancelar);
    panelBotones.add(btnGuardar);
    
    // Agregar todo al panel principal
    panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
    panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
    panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
    
    // Agregar al contenedor principal
    setContentPane(panelPrincipal);
    
    // Cargar datos
    cargarMunicipios();
    cargarTiposSitio();
}
    
    /**
     * Crea una etiqueta con estilo personalizado
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(COLOR_TEXTO);
        return label;
    }
    
    /**
     * Crea un campo de texto con estilo personalizado
     */
    private JTextField crearCampoTexto(int columnas) {
        JTextField textField = new JTextField(columnas);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        return textField;
    }
    
    /**
     * Crea un combo box con estilo personalizado
     */
    private <T> JComboBox<T> crearComboBox() {
        JComboBox<T> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(COLOR_SECUNDARIO, 1, true));
        comboBox.setForeground(COLOR_TEXTO);
        return comboBox;
    }
    
    /**
     * Crea un checkbox con estilo personalizado
     */
    private JCheckBox crearCheckBox(String texto) {
        JCheckBox checkBox = new JCheckBox(texto);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setForeground(COLOR_TEXTO);
        checkBox.setOpaque(false);
        checkBox.setFocusPainted(false);
        return checkBox;
    }
    
    /**
     * Configura el efecto hover para un botón
     */
    private void configurarEfectoHover(JButton boton, Color colorNormal, Color colorHover) {
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorHover);
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(colorNormal);
            }
        });
    }
    
    /**
     * Carga la lista de municipios en el combo box
     */
    private void cargarMunicipios() {
        try {
            List<Municipio> municipios = controlador.obtenerMunicipios();
            for (Municipio municipio : municipios) {
                cmbMunicipio.addItem(municipio);
            }
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar municipios", e.getMessage());
        }
    }
    
    /**
     * Carga la lista de tipos de sitio en el combo box
     */
    private void cargarTiposSitio() {
        try {
            List<TipoSitioInteres> tipos = controlador.obtenerTiposSitioInteres();
            for (TipoSitioInteres tipo : tipos) {
                cmbTipoSitio.addItem(tipo);
            }
        } catch (Exception e) {
            mostrarMensajeError("Error al cargar tipos de sitio", e.getMessage());
        }
    }
    
    /**
     * Carga los datos del sitio actual en los campos del formulario
     */
    private void cargarDatosSitio() {
        txtNombre.setText(sitioActual.getNombreSitio());
        txtDescripcion.setText(sitioActual.getDescripcion());
        txtPrecio.setText(String.valueOf(sitioActual.getPrecio()));
        txtDistancia.setText(String.valueOf(sitioActual.getDistancia()));
        
        // Seleccionar municipio
        for (int i = 0; i < cmbMunicipio.getItemCount(); i++) {
            Municipio municipio = cmbMunicipio.getItemAt(i);
            if (municipio.getId() == sitioActual.getIdMunicipioFk()) {
                cmbMunicipio.setSelectedIndex(i);
                break;
            }
        }
        
        // Seleccionar tipo de sitio
        for (int i = 0; i < cmbTipoSitio.getItemCount(); i++) {
            TipoSitioInteres tipo = cmbTipoSitio.getItemAt(i);
            if (tipo.getId() == sitioActual.getIdTipoSitioFk()) {
                cmbTipoSitio.setSelectedIndex(i);
                break;
            }
        }
        
        // Marcar checkboxes según los servicios
        chkAlojamiento.setSelected(sitioActual.isTieneAlojamiento());
        chkAlimentacion.setSelected(sitioActual.isTieneAlimentacion());
        chkTransporte.setSelected(sitioActual.isTieneTransporte());
    }
    
    /**
     * Guarda los cambios realizados en el sitio
     */
    private void guardarCambios(ActionEvent e) {
        try {
            // Validar datos
            if (txtNombre.getText().trim().isEmpty()) {
                mostrarMensajeError("Error de validación", "El nombre del sitio no puede estar vacío");
                txtNombre.requestFocus();
                return;
            }
            
            double precio;
            try {
                precio = Double.parseDouble(txtPrecio.getText().trim());
                if (precio < 0) {
                    throw new NumberFormatException("El precio debe ser un número positivo");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError("Error de validación", "El precio debe ser un número válido");
                txtPrecio.requestFocus();
                return;
            }
            
            float distancia;
            try {
                distancia = Float.parseFloat(txtDistancia.getText().trim());
                if (distancia < 0) {
                    throw new NumberFormatException("La distancia debe ser un número positivo");
                }
            } catch (NumberFormatException ex) {
                mostrarMensajeError("Error de validación", "La distancia debe ser un número válido");
                txtDistancia.requestFocus();
                return;
            }
            
            // Actualizar datos del sitio
            sitioActual.setNombreSitio(txtNombre.getText().trim());
            sitioActual.setDescripcion(txtDescripcion.getText().trim());
            sitioActual.setPrecio(precio);
            sitioActual.setDistancia(distancia);
            
            Municipio municipioSeleccionado = (Municipio) cmbMunicipio.getSelectedItem();
            sitioActual.setIdMunicipioFk(municipioSeleccionado.getId());
            
            TipoSitioInteres tipoSeleccionado = (TipoSitioInteres) cmbTipoSitio.getSelectedItem();
            sitioActual.setIdTipoSitioFk(tipoSeleccionado.getId());
            
            sitioActual.setTieneAlojamiento(chkAlojamiento.isSelected());
            sitioActual.setTieneAlimentacion(chkAlimentacion.isSelected());
            sitioActual.setTieneTransporte(chkTransporte.isSelected());
            
            // Guardar cambios
            boolean resultado = controlador.actualizarSitio(sitioActual);
            
            if (resultado) {
                mostrarMensajeExito("Sitio actualizado correctamente", 
                    "La información del sitio turístico ha sido actualizada en el sistema.");
             panelPadre.cargarDatos();

                
                dispose();
            } else {
                mostrarMensajeError("Error", "No se pudo actualizar el sitio");
            }
        } catch (Exception ex) {
            mostrarMensajeError("Error al guardar los cambios", ex.getMessage());
        }
    }
    
    /**
     * Muestra un mensaje de error personalizado
     */
    private void mostrarMensajeError(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            titulo, 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de éxito personalizado
     */
    private void mostrarMensajeExito(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            titulo, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Método principal para pruebas
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorTurismo controlador = new ControladorTurismo();
            // Usar un ID de sitio existente en tu base de datos para pruebas
            EditarSitioView vista = new EditarSitioView(controlador, 1);
            vista.setVisible(true);
        });
    }
}