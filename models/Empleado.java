package models;

public class Empleado {

    // Atributos
    private int idEmpleado;
    private String codigo;   // Código interno o identificador
    private String nombre;
    private String puesto;
    private double salario;

    // Constructor vacío
    public Empleado() {}

    // Constructor sin ID (útil para inserciones)
    public Empleado(String codigo, String nombre, String puesto, double salario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
    }

    // Constructor completo
    public Empleado(int idEmpleado, String codigo, String nombre, String puesto, double salario) {
        this(codigo, nombre, puesto, salario);
        this.idEmpleado = idEmpleado;
    }

    // Getters y Setters
    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(final int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getCodigo() { return codigo; }
    public void setCodigo(final String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(final String nombre) { this.nombre = nombre; }

    public String getPuesto() { return puesto; }
    public void setPuesto(final String puesto) { this.puesto = puesto; }

    public double getSalario() { return salario; }
    public void setSalario(final double salario) { this.salario = salario; }

    @Override
    public String toString() {
        return String.format(
            "Empleado{idEmpleado=%d, codigo='%s', nombre='%s', puesto='%s', salario=%.2f}",
            idEmpleado, codigo, nombre, puesto, salario
        );
    }

    // Método usado para mostrar empleados en JComboBox (facturación)
    public String toComboString() {
        return nombre;
    }
}
