public class Farmaceutico extends Medico {

    private String licencia;
    private int preescripcionesPorDia;

    public Farmaceutico(int id, String nombre, String departamento, int experiencia, int salarioBase, String licencia, int preescripcionesPorDia) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.licencia = licencia;
        this.preescripcionesPorDia = preescripcionesPorDia;
    }
    public String getTipo(){
        return licencia;
    }
    public int getPreescricpionesPorDia() {
        return preescripcionesPorDia;
    }
    @Override
    public double calcularSalario(double comision) {
        double salarioFinal = this.salarioBase + comision;
        return salarioFinal;
    }
}