import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de gestión de médicos.");
        Controlador controlador = new Controlador();
        Manager manager = new Manager(controlador);
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;
        ArrayList<String> departamentos = new ArrayList<>();
        departamentos.add("Cardiologia");
        departamentos.add("Neurologia");
        departamentos.add("Pediatria");
        departamentos.add("Oncologia");
        departamentos.add("Urgencias");
        departamentos.add("Farmacia");
        departamentos.add("Radiologia");
        departamentos.add("Enfermeria");
        departamentos.add("Medicina General");
        departamentos.add("Cirugia");
        departamentos.add("Ginecologia");
        departamentos.add("Dermatologia");
        departamentos.add("Psiquiatria");
        departamentos.add("Oftalmologia");
        departamentos.add("Otorrinolaringologia");
        departamentos.add("Traumatologia");
        departamentos.add("Urologia");
        departamentos.add("Endocrinologia");

        while (opcion != 8) {
            System.out.println("\n--- Menú Principal ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar Médico");
            System.out.println("2. Mostrar Personal Médico");
            System.out.println("3. Calcular Salario");
            System.out.println("4. Agendar Cita");
            System.out.println("5. Mostrar Citas");
            System.out.println("6. Cambiar Cita");
            System.out.println("7. Operaciones de manager");
            System.out.println("8. Salir");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    String respuesta = "si";
                    while (respuesta.equalsIgnoreCase("si")) {
                        System.out.print("Ingrese el ID del médico: ");
                        int id = teclado.nextInt();
                        while (!controlador.verificarExistencia(id)) {
                            System.out.println("El ID ya existe. Ingrese un ID diferente.");
                            id = teclado.nextInt();
                        }
                        System.out.print("Ingrese el nombre del médico: ");
                        String nombre = teclado.next();
                        System.out.print("Ingrese el departamento del médico: ");
                        for (String depto : departamentos) {
                            System.out.println("- " + depto);
                        }
                        System.out.println("Seleccione un departamento de la lista anterior.");
                        String d = teclado.next();
                        ArrayList<String> departamentosLower = new ArrayList<>();
                        for (String depto : departamentos) {
                            departamentosLower.add(depto.toLowerCase());
                        }
                        while (!departamentosLower.contains(d.trim().toLowerCase())) {
                            System.out.println("Departamento no válido. Seleccione un departamento de la lista.");
                            d = teclado.next();
                        }
                        System.out.print("Ingrese los años de experiencia del médico: ");
                        int experiencia = teclado.nextInt();
                        System.out.print("Ingrese el salario base del médico: ");
                        double salarioBase = teclado.nextDouble();
                        System.out.print("Ingrese el tipo de médico (General, enfermero, radiologo, farmaceutico): ");
                        String tipoMedico = teclado.next();
                        Medico medico;
                        switch (tipoMedico.toLowerCase()) {
                            case "general":
                                System.out.print("Ingrese la especialización del médico general: ");
                                String especializacion = teclado.next();
                                System.out.print("Ingrese la capacidad de pacientes por día: ");
                                int capacidadPacientesPorDia = teclado.nextInt();
                                System.out.print("Ingrese la tarifa por consulta: ");
                                double tarifa = teclado.nextDouble();
                                medico = new DoctorGeneral(id, nombre, d, experiencia, salarioBase, especializacion, capacidadPacientesPorDia, tarifa);
                                break;
                            case "enfermero":
                                System.out.print("Ingrese el horario del enfermero (diurno/nocturno): ");
                                String horario = teclado.next().toLowerCase().trim();
                                while (horario.equals("diurno") == false && horario.equals("nocturno") == false) {
                                    System.out.println("Horario no válido. Ingrese 'diurno' o 'nocturno': ");
                                    horario = teclado.next().toLowerCase().trim();
                                }
                                System.out.print("Ingrese la certificacion del enfermero: ");
                                String certificacion = teclado.next();
                                medico = new Enfermero(id, nombre, d, experiencia, salarioBase, horario, certificacion);
                                break;
                            case "radiólogo":
                                System.out.print("Ingrese el tipo de radiólogo: ");
                                String tipo1 = teclado.next();
                                System.out.print("Ingrese la tarifa del radiólogo: ");
                                double tarifa1 = teclado.nextDouble();
                                medico = new Radiologo(id, nombre, d, experiencia, salarioBase, tipo1, tarifa1);
                                break;

                            case "farmaceutico":
                                System.out.print("Ingrese la licencia del farmacéutico: ");
                                String licencia = teclado.next();
                                System.out.print("Ingrese el número de prescripciones por día: ");
                                int preescripcionesPorDia = teclado.nextInt();
                                medico = new Farmaceutico(id, nombre, d, experiencia, salarioBase, licencia, preescripcionesPorDia);
                                break;
                            default:
                                System.out.println("Tipo de médico no válido. Intente de nuevo.");
                                continue;
                        }
                        controlador.contratarMedico(medico);
                        System.out.print("¿Desea agregar otro médico? (si/no): ");
                        respuesta = teclado.next();
                        while (!respuesta.equalsIgnoreCase("si") && !respuesta.equalsIgnoreCase("no")) {
                            System.out.println("Respuesta no válida. Inténtelo de nuevo (si/no): ");
                            respuesta = teclado.next();
                        }
                    }
                    break;
                case 2:
                    System.out.println("Mostrando todo el personal médico:");
                    if (controlador.getMedicos().isEmpty()) {
                        System.out.println("No hay médicos registrados.");
                        break;
                    }
                    for (Medico m : controlador.getMedicos()) {
                        System.out.println(m);
                    }
                    break;
                case 3:
                    System.out.println("Calcular salario por médico");
                    for (Medico m : controlador.getMedicos()) {
                        System.out.println(m.getId() + ": " + m.getNombre());
                    }
                    System.out.print("Ingrese el ID del médico para calcular su salario: ");
                    int identificador = teclado.nextInt();
                    Medico medico = null;
                    for (Medico m : controlador.getMedicos()) {
                        if (m.getId() == identificador) {
                            medico = m;
                            break;
                        }
                    }
                    if (medico != null) {
                        double var = 0;
                        double salario = medico.calcularSalario(var);
                        System.out.println("El salario del médico " + medico.getNombre() + " es: " + salario);
                    } else {
                        System.out.println("Médico no encontrado.");
                    }

                    break;

                case 4:
                    if (controlador.getMedicos().isEmpty()) {
                        System.out.println("Primero debes agregar médicos.");
                        break;
                    }
                    System.out.print("ID de cita: ");
                    int idCita = teclado.nextInt();
                    for (Cita c : controlador.getCitas()) {
                        while (c.getId() == idCita) {
                            System.out.println("El ID de cita ya existe. Ingrese un ID diferente.");
                            idCita = teclado.nextInt();
                        }
                    }
                    System.out.print("Nombre del paciente: ");
                    String paciente = teclado.next();
                    System.out.print("ID del médico: ");
                    int idMed = teclado.nextInt();
                    Medico medicoElegido = null;
                    for (Medico m : controlador.getMedicos()) {
                        if (m.getId() == idMed) {
                            medicoElegido = m;
                            break;
                        }
                    }
                    if (medicoElegido == null) {
                        System.out.println("Médico no encontrado.");
                        break;
                    }
                    System.out.print("Fecha (YYYY-MM-DD): ");
                    String fechaStr = teclado.next();
                    LocalDate fecha = null;
                    try {
                        fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } catch (Exception e) {
                        System.out.println("Formato de fecha no válido.");
                        break;
                    }
                    System.out.print("Hora (HH:mm): ");
                    String horaStr = teclado.next();
                    LocalTime hora;
                    try {
                        hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
                    } catch (Exception e) {
                        System.out.println("Formato de hora no válido.");
                        break;
                    }
                    System.out.print("Tipo de cita: ");
                    String tipoCita = teclado.next();

                    
                    if (medicoElegido.getClass() == DoctorGeneral.class) {
                        int citasEnFecha = 0;
                        for (Cita c : controlador.getCitas()) {
                            if (c.getMedico().equals(medicoElegido) && c.getFecha().equals(fecha) && !c.getEstado().equals("CANCELADA")) {
                                citasEnFecha++;
                            }
                        }
                        int capacidad = ((DoctorGeneral) medicoElegido).getPacientesPorDia();
                        if (citasEnFecha >= capacidad) {
                            System.out.println("El médico " + medicoElegido.getNombre() + " ya alcanzó su capacidad de " + capacidad + " pacientes para la fecha " + fecha + ". No se puede agendar la cita.");
                            break;
                        }
                    }
                    if (medicoElegido.getClass() == Farmaceutico.class) {
                        int citasEnFecha = 0;
                        for (Cita c : controlador.getCitas()) {
                            if (c.getMedico().equals(medicoElegido) && c.getFecha().equals(fecha) && !c.getEstado().equals("CANCELADA")) {
                                citasEnFecha++;
                            }
                        }
                        int capacidad = ((Farmaceutico) medicoElegido).getPrescripcionesPorDia();
                        if (citasEnFecha >= capacidad) {
                            System.out.println("El farmacéutico " + medicoElegido.getNombre() + " ya alcanzó su capacidad de " + capacidad + " prescripciones para la fecha " + fecha + ". No se puede agendar la cita.");
                            break;
                        }
                    }

                    Cita cita = new Cita(idCita, paciente, medicoElegido, fecha, hora, tipoCita, "PROGRAMADA");
                    controlador.agregarCita(cita);
                    System.out.println("¡Cita agendada!");

                    System.out.println("\nCitas programadas:");
                    for (Cita c : controlador.listarCitasPorEstado("PROGRAMADA")) {
                        System.out.println(c);
                    }

                    break;

                case 5:
                    System.out.print("¿Desea filtrar por estado? (si/no): ");
                    String filtrar = teclado.next();
                    if (filtrar.equalsIgnoreCase("si")) {
                        System.out.print("Ingrese el estado a filtrar: ");
                        String estadoFiltro = teclado.next();
                        while (!estadoFiltro.equalsIgnoreCase("PROGRAMADA") && !estadoFiltro.equalsIgnoreCase("COMPLETADA") && !estadoFiltro.equalsIgnoreCase("CANCELADA") && !estadoFiltro.equalsIgnoreCase("EN PROGRESO")) {
                            System.out.println("Estado no válido. Ingrese 'PROGRAMADA', 'COMPLETADA', 'CANCELADA' o 'EN PROGRESO': ");
                            estadoFiltro = teclado.next();
                        }
                        for (Cita c : controlador.listarCitasPorEstado(estadoFiltro)) {
                            System.out.println(c);
                        }
                    } else {
                        for (Cita c : controlador.getCitas()) {
                            System.out.println(c);
                        }
                    }
                    break;

                case 6:
                    System.out.print("Ingrese el ID de la cita a reagendar: ");
                    int idCitaCambiar = teclado.nextInt();
                    boolean resultado = controlador.reagendarCita(idCitaCambiar);
                    if (resultado) {
                        System.out.println("Cita reagendada exitosamente.");
                        System.out.println(controlador.obtenerHistorialReagendamientos(idCitaCambiar));
                    } else {
                        System.out.println("No se pudo reagendar la cita (no existe o sin disponibilidad).");
                    }
                    break;
                case 7:
                    System.out.println("\nOperaciones de manager:");
                    System.out.println("Seleccione una opción:");
                    System.out.println("1. Encontrar personal disponible");
                    System.out.println("2. Reportes de Nómina por departamento");
                    System.out.println("3. Gestión de Conflictos de Horarios");
                    System.out.println("4. Análisis de Utilización");
                    System.out.println("5. Reportes de personal");
                    System.out.println("6. Reporte de Citas");
                    System.out.println("7. Análisis financiero");
                    System.out.println("8. Historial de Reagendamientos");
                    System.out.println("9. Volver al menú principal");
                    System.out.println("Seleccione una opción:");
                    int opcionManager = teclado.nextInt();

                    switch (opcionManager) {
                        case 1:
                        // Encontrar personal Disponible por tipo y horario
                            System.out.print("Tipo de médico (General, enfermero, radiólogo, farmacéutico): ");
                            String tipoMed = teclado.next();
                            System.out.print("Fecha (YYYY-MM-DD): ");
                            LocalDate fechaDisp = LocalDate.parse(teclado.next());
                            System.out.print("Hora (HH:mm): ");
                            LocalTime horaDisp = LocalTime.parse(teclado.next());
                            for (Medico m : controlador.getMedicos()) {
                                if (m.getClass().getSimpleName().equalsIgnoreCase(tipoMed)) {
                                    Medico disponible = controlador.buscarMedicoDisponible(m.getClass(), fechaDisp, horaDisp);
                                    if (disponible != null) {
                                        System.out.println("Médico disponible encontrado: " + disponible);
                                    } else {
                                        System.out.println("No hay médicos disponibles de ese tipo en la fecha y hora indicadas.");
                                    }
                                    break;
                                }
                            }
                            break;
                        case 2:
                        // Reportes de Nómina por departamento
                            System.out.println("\nNómina por departamento:");
                            manager.mostrarNominaTotalPorDepartamento(departamentos);
                            break;
                        case 3:
                        // Gestión de Conflictos de Horarios
                            System.out.println("\nGestión de conflictos de horarios: ");
                            manager.detectarConflictos();
                            break;
                        case 4:
                        // Análisis de Utilización
                            System.out.println("\nAnálisis de utilización del personal médico:");
                            manager.analizarUtilizacionPersonal();
                            break;
                        case 5:
                        // Reportes de personal
                            System.out.println("\nReporte de personal médico:");
                            for (Medico m : controlador.getMedicos()) {
                                System.out.println("Cantidad de citas para " + m.getNombre() + ": " + controlador.getCitas().stream().filter(c -> c.getMedico().equals(m)).count());
                            }
                            break;
                        case 6:
                        // Reporte de Citas
                            System.out.println("CITAS POR CATEGORIA:");
                            System.out.println("PROGRAMADAS");
                            for (Cita c : controlador.listarCitasPorEstado("PROGRAMADA")) {
                                System.out.println(c);
                            }
                            System.out.println("COMPLETADAS");
                            if (controlador.listarCitasPorEstado("COMPLETADAS").isEmpty()) {
                                System.out.println("No hay citas completadas.");
                            }
                            for (Cita c : controlador.listarCitasPorEstado("COMPLETADA")) {
                                System.out.println(c);
                            }
                            System.out.println("CANCELADAS");
                            if (controlador.listarCitasPorEstado("CANCELADA").isEmpty()) {
                                System.out.println("No hay citas canceladas.");
                            }
                            for (Cita c : controlador.listarCitasPorEstado("CANCELADA")) {
                                System.out.println(c);
                            }
                            System.out.println("EN PROGRESO");
                            for (Cita c: controlador.getCitas()) {
                                controlador.citaEnProgreso(c);
                            }
                            if (controlador.listarCitasPorEstado("EN PROGRESO").isEmpty()) {
                                System.out.println("No hay citas en progreso.");
                            }
                            for (Cita c : controlador.listarCitasPorEstado("EN PROGRESO")) {
                                System.out.println(c);
                            }
                            break;

                        case 7:
                        // Análisis financiero
                            System.out.println("\nAnálisis financiero:");
                            manager.mostrarNominaTotalPorDepartamento(departamentos);
                            break;
                        case 8:
                        // Historial de Reagendamientos
                            System.out.println("\nHistorial de Reagendamientos:");
                            break;
                        case 9:
                            System.out.println("Volviendo al menú principal.");
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                            break;
                    }
                    break;
                case 8:
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
}