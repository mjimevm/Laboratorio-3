public abstract class Medico {
    // Atributos
    protected int id;
    protected String nombre;
    protected String departamento;
    protected int experiencia;
    protected double salarioBase;
    // Constructor
    public Medico (int id, String nombre, String departamento, int experiencia, double salarioBase) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.experiencia = experiencia;
        this.salarioBase = salarioBase;
    }
    // Gets
    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public int getExperiencia() {
        return experiencia;
    }
    public String getDepartamento() {
        return departamento;
    }
    public double getSalarioBase() {
        return salarioBase;
    }
    public abstract double calcularSalario(double a);

    @Override
    public String toString() {
        return "[ID: " + id + ", Nombre: " + nombre + ", Departamento: " + departamento + ", Años de Experiencia: " + experiencia + ", Salario Base: " + salarioBase + "]";
    }
}