// Farmaceutico hereda de Medico
public class Farmaceutico extends Medico {

    // Atributos específicos
    private String licencia;
    private int prescripcionesPorDia;

    // Constructor
    public Farmaceutico(int id, String nombre, String departamento, int experiencia, double salarioBase, String licencia, int prescripcionesPorDia) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.licencia = licencia;
        this.prescripcionesPorDia = prescripcionesPorDia;
    }

    // Getters y otros métodos
    public String getTipo(){
        return licencia;
    }
    public int getPrescripcionesPorDia() {
        return prescripcionesPorDia;
    }
    // Polimorfismo
    @Override
    public double calcularSalario(double comision) {
        double salarioFinal = this.salarioBase + comision;
        return salarioFinal;
    }
}