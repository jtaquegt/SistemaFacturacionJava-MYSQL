package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa una Factura en el sistema.
 * Contiene información general de la venta, cliente, empleado,
 * número de caja, fecha, total y los detalles de los artículos vendidos.
 */
public class Factura {

    private int idFactura;                  // Identificador único de la factura
    private int numeroFactura;              // Número correlativo de la factura
    private int idCliente;                  // Identificador del cliente
    private int idEmpleado;                 // Identificador del empleado que atendió la venta
    private Date fecha;                     // Fecha de la factura
    private double total;                   // Total de la factura
    private int numeroCaja;                 // Número de caja donde se realizó la venta
    private List<DetalleFactura> detalles;  // Lista de detalles de la factura

    /**
     * Constructor por defecto.
     * Inicializa la lista de detalles vacía.
     */
    public Factura() {
        this.detalles = new ArrayList<>();
    }

    /**
     * Constructor sin ID, útil al crear una nueva factura antes de guardarla en BD.
     *
     * @param numeroFactura Número correlativo de la factura
     * @param idCliente     ID del cliente
     * @param idEmpleado    ID del empleado
     * @param fecha         Fecha de la factura
     * @param total         Total de la factura
     * @param numeroCaja    Número de caja
     */
    public Factura(int numeroFactura, int idCliente, int idEmpleado, Date fecha, double total, int numeroCaja) {
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.total = total;
        this.numeroCaja = numeroCaja;
        this.detalles = new ArrayList<>();
    }

    /**
     * Constructor completo con ID y lista de detalles.
     *
     * @param idFactura     Identificador único
     * @param numeroFactura Número correlativo
     * @param idCliente     ID del cliente
     * @param idEmpleado    ID del empleado
     * @param fecha         Fecha de la factura
     * @param total         Total
     * @param numeroCaja    Número de caja
     * @param detalles      Lista de DetalleFactura
     */
    public Factura(int idFactura, int numeroFactura, int idCliente, int idEmpleado,
                   Date fecha, double total, int numeroCaja, List<DetalleFactura> detalles) {
        this.idFactura = idFactura;
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.total = total;
        this.numeroCaja = numeroCaja;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    // Getters y setters
    public int getIdFactura() { return idFactura; }
    public void setIdFactura(int idFactura) { this.idFactura = idFactura; }

    public int getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(int numeroFactura) { this.numeroFactura = numeroFactura; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getNumeroCaja() { return numeroCaja; }
    public void setNumeroCaja(int numeroCaja) { this.numeroCaja = numeroCaja; }

    public List<DetalleFactura> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleFactura> detalles) {
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format(
            "Factura{idFactura=%d, numeroFactura=%d, idCliente=%d, idEmpleado=%d, fecha=%s, total=%.2f, numeroCaja=%d}",
            idFactura, numeroFactura, idCliente, idEmpleado, fecha, total, numeroCaja
        );
    }
}
