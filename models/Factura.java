package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {

    private int idFactura;
    private int numeroFactura;
    private int idCliente;
    private int idEmpleado;
    private Date fecha;
    private double total;
    private int numeroCaja;
    private List<DetalleFactura> detalles;

    public Factura() { this.detalles = new ArrayList<>(); }

    public Factura(int numeroFactura, int idCliente, int idEmpleado, Date fecha, double total, int numeroCaja) {
        this.numeroFactura = numeroFactura;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
        this.total = total;
        this.numeroCaja = numeroCaja;
        this.detalles = new ArrayList<>();
    }

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
    public void setDetalles(List<DetalleFactura> detalles) { this.detalles = detalles != null ? detalles : new ArrayList<>(); }

    @Override
    public String toString() {
        return String.format(
            "Factura{idFactura=%d, numeroFactura=%d, idCliente=%d, idEmpleado=%d, fecha=%s, total=%.2f, numeroCaja=%d}",
            idFactura, numeroFactura, idCliente, idEmpleado, fecha, total, numeroCaja
        );
    }
}
