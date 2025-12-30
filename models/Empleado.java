package models;

/**
 * Representa un empleado en el sistema, con atributos básicos como código,
 * nombre, puesto y salario. Incluye métodos para mostrarlo en la interfaz gráfica.
 */
public class Empleado {

    // Identificador único del empleado
    private int idEmpleado;

    // Código interno del empleado
    private String codigo;

    // Nombre completo del empleado
    private String nombre;

    // Puesto o cargo del empleado
    private String puesto;

    // Salario del empleado
    private double salario;

    // Constructor vacío
    public Empleado() {}

    /**
     * Constructor para crear un empleado sin ID.
     *
     * @param codigo Código interno
     * @param nombre Nombre completo
     * @param puesto Puesto o cargo
     * @param salario Salario
     */
    public Empleado(String codigo, String nombre, String puesto, double salario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.puesto = puesto;
        this.salario = salario;
    }

    /**
     * Constructor completo, incluyendo ID.
     *
     * @param idEmpleado ID del empleado
     * @param codigo Código interno
     * @param nombre Nombre completo
     * @param puesto Puesto o cargo
     * @param salario Salario
     */
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

    /**
     * Representación completa del empleado para depuración o logs.
     */
    @Override
    public String toString() {
        return String.format(
            "Empleado{idEmpleado=%d, codigo='%s', nombre='%s', puesto='%s', salario=%.2f}",
            idEmpleado, codigo, nombre, puesto, salario
        );
    }

    /**
     * Representación simplificada del empleado para JComboBox u otras listas.
     *
     * @return Nombre del empleado
     */
    public String toComboString() {
        return nombre;
    }
}
