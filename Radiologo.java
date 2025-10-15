public class Radiologo extends Medico {

    private String tipo;
    private double tarifa;

    public Radiologo(int id, String nombre, String departamento, int experiencia, double salarioBase, String tipo, double tarifa) {
        super(id, nombre, departamento, experiencia, salarioBase);
        this.tipo = tipo;
        this.tarifa = tarifa;
    }
    public String getTipo(){
        return tipo;
    }
    public double getTarifa() {
        return tarifa;
    }
    @Override
    public double calcularSalario(double a) {
        double salarioFinal = this.salarioBase + this.tarifa;
        return salarioFinal;
    }
}