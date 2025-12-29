package models;

public class Empleado {

    private int idEmpleado;
    private String codigo;
    private String nombre;
    private String puesto;
    private double salario;

    public Empleado() {}

    public Empleado(String codigo, String nombre, String puesto, double salario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
    }

    public Empleado(int idEmpleado, String codigo, String nombre, String puesto, double salario) {
        this(codigo, nombre, puesto, salario);
        this.idEmpleado = idEmpleado;
    }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPuesto() { return puesto; }
    public void setPuesto(String puesto) { this.puesto = puesto; }

    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }

    @Override
    public String toString() {
        return String.format(
            "Empleado{idEmpleado=%d, codigo='%s', nombre='%s', puesto='%s', salario=%.2f}",
            idEmpleado, codigo, nombre, puesto, salario
        );
    }

    public String toComboString() { return nombre; }
}
