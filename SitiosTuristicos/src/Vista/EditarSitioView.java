package Vista;

import Controlador.ControladorTurismo;
import Vista.PanelAdminMunicipio;
import Modelo.Municipio;
import Modelo.SitioInteres;
import Modelo.TipoSitioInteres;
import Modelo.modelo_sitios_turisticos;
import Controlador.controlador_sitios_turisticos;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.awt.geom.RoundRectangle2D;

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
    private JButton btnGestionarImagenes;

    private final modelo_sitios_turisticos modelo = new modelo_sitios_turisticos();
    
    // Colores para la interfaz de naturaleza
    private final Color COLOR_PRIMARIO = new Color(76, 175, 80);       // Verde claro
    private final Color COLOR_SECUNDARIO = new Color(46, 125, 50);     // Verde oscuro
    private final Color COLOR_ACENTO = new Color(129, 199, 132);       // Verde suave
    private final Color COLOR_FONDO = new Color(245, 249, 244);        // Blanco verdoso suave
    private final Color COLOR_TEXTO = new Color(33, 33, 33);           // Casi negro
    private final Color COLOR_EXITO = new Color(46, 125, 50);          // Verde oscuro para éxito
    private final Color COLOR_BOTON_HOVER = new Color(56, 142, 60);    // Verde medio para hover
    private final Color COLOR_CANCELAR = new Color(229, 115, 115);     // Rojo suave
    private final Color COLOR_BORDE = new Color(165, 214, 167);        // Verde muy claro para bordes
    
    // Recursos de imágenes
    private ImageIcon iconoHoja;
    private ImageIcon iconoGuardar;
    private ImageIcon iconoCancelar;
    private ImageIcon iconoImagenes;
    
    /**
     * Constructor
     * @param controlador Controlador de turismo
     * @param idSitio ID del sitio a editar
     * @param panelPadre Panel padre para actualización
     */
    public EditarSitioView(ControladorTurismo controlador, int idSitio, PanelAdminMunicipio panelPadre) {
        super("Gestión de Sitios Turísticos");
        this.controlador = controlador;
        this.panelPadre = panelPadre;
        
        // Cargar recursos de imágenes (se deberían agregar al proyecto)
        cargarRecursos();
        
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
        setSize(850, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(800, 700));
    }
    
    /**
     * Constructor alternativo sin panel padre
     */
    public EditarSitioView(ControladorTurismo controlador, int idSitio) {
        this(controlador, idSitio, null);
    }
    
    /**
     * Carga los recursos gráficos para la interfaz
     */
    private void cargarRecursos() {
        // Nota: Aquí debes cargar tus iconos personalizados
        // Puedes usar placeholders por ahora
        iconoHoja = new ImageIcon(getClass().getResource("/img/icons8-configuración-del-administrador-50.png"));
        iconoGuardar = new ImageIcon(getClass().getResource("/img/icons8-guardar-50.png"));
        iconoCancelar = new ImageIcon(getClass().getResource("/img/icons8-cancelar-64.png"));
        iconoImagenes = new ImageIcon(getClass().getResource("/img/icons8-imagen-48.png"));
        
        // Si los iconos no existen, usamos texto en su lugar
        if (iconoHoja == null) iconoHoja = new ImageIcon();
        if (iconoGuardar == null) iconoGuardar = new ImageIcon();
        if (iconoCancelar == null) iconoCancelar = new ImageIcon();
        if (iconoImagenes == null) iconoImagenes = new ImageIcon();
    }
    
    /**
     * Inicializa los componentes de la interfaz
     */
    private void inicializarComponentes() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // Configurar ventana principal con decoración personalizada
        setTitle("Editar Sitio de Interés Natural");
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(COLOR_FONDO);
        
        // Crear panel principal con degradado de fondo
        JPanel panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Crear un degradado del verde más claro al fondo
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(232, 245, 233), 
                    0, getHeight(), COLOR_FONDO);
                
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Dibujar hojas decorativas suaves en esquinas
                g2d.setColor(new Color(200, 230, 201, 60)); // Verde muy claro y transparente
                
                // Esquina superior izquierda
                g2d.fillArc(-50, -50, 150, 150, 0, 90);
                
                // Esquina inferior derecha
                g2d.fillArc(getWidth() - 100, getHeight() - 100, 150, 150, 180, 90);
                
                g2d.dispose();
            }
        };
        
        panelPrincipal.setLayout(new BorderLayout(15, 15));
        panelPrincipal.setBorder(new EmptyBorder(25, 25, 25, 25));
        
        // Panel decorativo superior con imagen de naturaleza
        JPanel panelDecorativo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Fondo degradado para el panel superior
                GradientPaint gradient = new GradientPaint(
                    0, 0, COLOR_SECUNDARIO, 
                    getWidth(), getHeight(), COLOR_ACENTO);
                
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Dibujar líneas onduladas estilizadas que evocan paisajes
                g2d.setColor(new Color(255, 255, 255, 80));
                g2d.setStroke(new BasicStroke(2f));
                
                g2d.dispose();
            }
        };
        panelDecorativo.setPreferredSize(new Dimension(800, 100));
        panelDecorativo.setLayout(new BorderLayout());
        
        // Título con estilo y posible icono
        JLabel lblTitulo = new JLabel("Editar Sitio Turístico Natural");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        lblTitulo.setBorder(new EmptyBorder(20, 0, 20, 0));
        
        // Si tenemos icono, lo agregamos
        if (iconoHoja.getIconWidth() > 0) {
            lblTitulo.setIcon(iconoHoja);
            lblTitulo.setIconTextGap(15);
        }
        
        panelDecorativo.add(lblTitulo, BorderLayout.CENTER);
        
        // Panel para el formulario con estilo redondeado
        JPanel panelFormulario = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Fondo blanco con bordes redondeados
                g2d.setColor(Color.WHITE);
                g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 20, 20));
                
                // Borde suave
                g2d.setColor(COLOR_BORDE);
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.draw(new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 19, 19));
                
                g2d.dispose();
            }
        };
        
        panelFormulario.setLayout(new GridBagLayout());
        panelFormulario.setOpaque(false);
        panelFormulario.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;
        
        // Crear campos de formulario con estilo de naturaleza
        txtNombre = crearCampoTexto(25);
        
        // MODIFICADO: Área de texto para la descripción con estilo natural y más espacio
        txtDescripcion = new JTextArea(5, 30); // Aumentado a 10 filas visibles, 30 columnas
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txtDescripcion.setBackground(COLOR_FONDO);
        txtDescripcion.setForeground(COLOR_TEXTO);
        txtDescripcion.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        // MODIFICADO: Scroll pane con tamaño claramente especificado
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setMinimumSize(new Dimension(400, 150));
        scrollDescripcion.setPreferredSize(new Dimension(500, 200)); // Significativamente más grande
        scrollDescripcion.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Siempre mostrar la barra vertical
        scrollDescripcion.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        txtPrecio = crearCampoTexto(10);
        txtDistancia = crearCampoTexto(10);
        
        // Combos con estilo natural
        cmbMunicipio = crearComboBox();
        cmbTipoSitio = crearComboBox();
        
        // Checkboxes con estilo natural
        chkAlojamiento = crearCheckBox("Alojamiento");
        chkAlimentacion = crearCheckBox("Alimentación");
        chkTransporte = crearCheckBox("Transporte");
        
        // Sección de Nombre
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weighty = 0.0;
        panelFormulario.add(crearEtiquetaDecorada("Nombre:"), constraints);
        
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        panelFormulario.add(txtNombre, constraints);
        constraints.gridwidth = 1;
        
        // MODIFICADO: Sección de Descripción con más espacio vertical
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weighty = 0.0;
        panelFormulario.add(crearEtiquetaDecorada("Descripción:"), constraints);
        
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH; // Permitir crecimiento en ambas direcciones
        constraints.weighty = 1.0; // Esto es clave - da peso a la descripción para crecer verticalmente
        panelFormulario.add(scrollDescripcion, constraints);
        constraints.weighty = 0.0;
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        
        // Sección de Precio con icono decorativo
        constraints.gridx = 0;
        constraints.gridy = 2;
        panelFormulario.add(crearEtiquetaDecorada("Precio:"), constraints);
        
        constraints.gridx = 1;
        panelFormulario.add(txtPrecio, constraints);
        
        // Sección de Distancia
        constraints.gridx = 0;
        constraints.gridy = 3;
        panelFormulario.add(crearEtiquetaDecorada("Distancia (km):"), constraints);
        
        constraints.gridx = 1;
        panelFormulario.add(txtDistancia, constraints);
        
        // Sección de Municipio
        constraints.gridx = 0;
        constraints.gridy = 4;
        panelFormulario.add(crearEtiquetaDecorada("Municipio:"), constraints);
        
        constraints.gridx = 1;
        panelFormulario.add(cmbMunicipio, constraints);
        
        // Sección de Tipo de sitio
        constraints.gridx = 0;
        constraints.gridy = 5;
        panelFormulario.add(crearEtiquetaDecorada("Tipo de sitio:"), constraints);
        
        constraints.gridx = 1;
        panelFormulario.add(cmbTipoSitio, constraints);
        
        // Panel decorativo para servicios
        JPanel panelServicios = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Fondo suave para sección de servicios
                g2d.setColor(new Color(232, 245, 233));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                g2d.dispose();
            }
        };
        panelServicios.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelServicios.setOpaque(false);
        panelServicios.add(chkAlojamiento);
        panelServicios.add(chkAlimentacion);
        panelServicios.add(chkTransporte);
        
        constraints.gridx = 0;
        constraints.gridy = 6;
        panelFormulario.add(crearEtiquetaDecorada("Servicios:"), constraints);
        
        constraints.gridx = 1;
        constraints.gridwidth = 2;
        panelFormulario.add(panelServicios, constraints);
        constraints.gridwidth = 1;
        
        // Panel para botones con estilo curvo
        JPanel panelBotones = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                
                // Fondo curvo suave para panel botones
                g2d.setColor(new Color(245, 250, 245));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                g2d.dispose();
            }
        };
        panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 12));
        panelBotones.setOpaque(false);
        
        // Botón para gestionar imágenes
        btnGestionarImagenes = crearBoton("Gestionar Imágenes", COLOR_ACENTO, COLOR_SECUNDARIO);
        if (iconoImagenes.getIconWidth() > 0) {
            btnGestionarImagenes.setIcon(iconoImagenes);
        }
        btnGestionarImagenes.addActionListener(e -> abrirGestionarImagenes());
        
        // Botón cancelar
        btnCancelar = crearBoton("Cancelar", COLOR_CANCELAR, new Color(198, 40, 40));
        if (iconoCancelar.getIconWidth() > 0) {
            btnCancelar.setIcon(iconoCancelar);
        }
        btnCancelar.addActionListener(e -> dispose());
        
        // Botón guardar
        btnGuardar = crearBoton("Guardar Cambios", COLOR_PRIMARIO, COLOR_SECUNDARIO);
        if (iconoGuardar.getIconWidth() > 0) {
            btnGuardar.setIcon(iconoGuardar);
        }
        btnGuardar.addActionListener(this::guardarCambios);
        
        panelBotones.add(btnGestionarImagenes);
        panelBotones.add(btnCancelar);
        panelBotones.add(btnGuardar);
        
        // Agregar todo al panel principal
        panelPrincipal.add(panelDecorativo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar al contenedor principal
        setContentPane(panelPrincipal);
        
        // Cargar datos
        cargarMunicipios();
        cargarTiposSitio();
    }
    
    /**
     * Crea una etiqueta decorada con estilo natural
     */
    private JLabel crearEtiquetaDecorada(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        label.setForeground(COLOR_SECUNDARIO);
        
        // Borde inferior suave
        Border bordeInferior = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(129, 199, 132, 100));
        label.setBorder(BorderFactory.createCompoundBorder(
            bordeInferior,
            BorderFactory.createEmptyBorder(0, 0, 3, 0)
        ));
        
        return label;
    }
    
    /**
     * Crea un campo de texto con estilo natural
     */
    private JTextField crearCampoTexto(int columnas) {
        JTextField textField = new JTextField(columnas) {
            @Override
            protected void paintComponent(Graphics g) {
                if (!isOpaque() && getBorder() instanceof LineBorder) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setPaint(getBackground());
                    g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
                    g2.dispose();
                }
                super.paintComponent(g);
            }
        };
        
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)));
        textField.setOpaque(false);
        textField.setBackground(new Color(250, 252, 250));
        
        return textField;
    }
    
    /**
     * Crea un combo box con estilo natural
     */
    private <T> JComboBox<T> crearComboBox() {
        JComboBox<T> comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(new Color(250, 252, 250));
        comboBox.setForeground(COLOR_TEXTO);
        
        // Bordes redondeados para el combobox
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(COLOR_BORDE, 1, true),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)));
        
        // Personalizar el renderer para que tenga relleno adecuado
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                
                if (isSelected) {
                    label.setBackground(COLOR_ACENTO);
                    label.setForeground(Color.WHITE);
                }
                
                return label;
            }
        });
        
        return comboBox;
    }
    
    /**
     * Crea un checkbox con estilo natural
     */
    private JCheckBox crearCheckBox(String texto) {
        JCheckBox checkBox = new JCheckBox(texto);
        checkBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        checkBox.setForeground(COLOR_TEXTO);
        checkBox.setOpaque(false);
        checkBox.setFocusPainted(false);
        
        // Cambiar icono del checkbox a algo más orgánico
        // Aquí se podría usar iconos personalizados si los tienes
        
        return checkBox;
    }
    
    /**
     * Crea un botón con estilo natural
     */
    private JButton crearBoton(String texto, Color colorNormal, Color colorHover) {
        JButton boton = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isPressed()) {
                    g2.setColor(colorHover.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(colorHover);
                } else {
                    g2.setColor(colorNormal);
                }
                
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Borde suave
                g2.setColor(new Color(255, 255, 255, 50));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, 14, 14);
                
                g2.dispose();
                
                super.paintComponent(g);
            }
        };
        
        boton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setOpaque(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Configurar efecto hover
        configurarEfectoHover(boton, colorNormal, colorHover);
        
        return boton;
    }

    
    /**
     * Configura el efecto hover para un botón
     */
    private void configurarEfectoHover(JButton boton, Color colorNormal, Color colorHover) {
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.repaint();
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.repaint();
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
                mostrarMensajeExito("¡Sitio Actualizado!", 
                    "La información del sitio turístico ha sido actualizada correctamente.");
                
                // Actualizar el panel padre si existe
                if (panelPadre != null) {
                    panelPadre.cargarDatos();
                }
                
                dispose();
            } else {
                mostrarMensajeError("Error", "No se pudo actualizar el sitio. Inténtelo de nuevo.");
            }
        } catch (Exception ex) {
            mostrarMensajeError("Error al guardar los cambios", ex.getMessage());
        }
    }
    
    /**
     * Muestra un mensaje de error con estilo personalizado
     */
    private void mostrarMensajeError(String titulo, String mensaje) {
        // Personalizar el aspecto del diálogo de error
        UIManager.put("OptionPane.background", COLOR_FONDO);
        UIManager.put("Panel.background", COLOR_FONDO);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.messageForeground", COLOR_TEXTO);
        UIManager.put("OptionPane.titleFont", new Font("Segoe UI", Font.BOLD, 16));
        
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            titulo, 
            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Muestra un mensaje de éxito con estilo personalizado
     */
    private void mostrarMensajeExito(String titulo, String mensaje) {
        // Personalizar el aspecto del diálogo de éxito
        UIManager.put("OptionPane.background", COLOR_FONDO);
        UIManager.put("Panel.background", COLOR_FONDO);
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.messageForeground", COLOR_EXITO);
        UIManager.put("OptionPane.titleFont", new Font("Segoe UI", Font.BOLD, 16));
        
        JOptionPane.showMessageDialog(this, 
            mensaje, 
            titulo, 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Método para abrir la vista de gestión de imágenes
     */
    private void abrirGestionarImagenes() {
        // Efecto de transición suave
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        // Crear y mostrar la ventana de gestión de imágenes
        SwingUtilities.invokeLater(() -> {
            try {
                GestionarImagenesView gestionarImagenesView = new GestionarImagenesView(modelo, sitioActual.getNombre());
                gestionarImagenesView.setVisible(true);
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    
    /**
     * Clase interna para crear un JPanel con fondo de degradado verde natural
     */
    private class PanelDegradado extends JPanel {
        private final Color colorInicio;
        private final Color colorFin;
        
        public PanelDegradado(Color colorInicio, Color colorFin) {
            this.colorInicio = colorInicio;
            this.colorFin = colorFin;
            setOpaque(false);
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Aplicar degradado de verde natural
            GradientPaint gradient = new GradientPaint(
                0, 0, colorInicio, 
                0, getHeight(), colorFin);
            
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            
            g2d.dispose();
        }
    }
    
    /**
     * Método principal para pruebas de la interfaz
     */
    public static void main(String[] args) {
        // Establecer look and feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        // Iniciar la aplicación
        SwingUtilities.invokeLater(() -> {
            try {
                ControladorTurismo controlador = new ControladorTurismo();
                // Usar un ID de sitio existente en tu base de datos para pruebas
                EditarSitioView vista = new EditarSitioView(controlador, 1);
                vista.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al iniciar la aplicación: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}