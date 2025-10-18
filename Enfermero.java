// Enfermero hereda de Medico
public class Enfermero extends Medico {

    // Atributos específicos
    private String horario;
    private String certificacion;

    // Constructor
    public Enfermero(int id, String nombre, String departamento, int experiencia, double salarioBase, String horario, String certificacion) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.horario = horario;
        this.certificacion = certificacion;
    }
    
    // Getters y otros métodos
    public String getHorario() {
        return horario;
    }
    public String getCertificacion() {
        return certificacion;
    }
    @Override
    public double calcularSalario(double bono) {
        double salarioFinal = this.salarioBase + bono;
        return salarioFinal;
    }
}