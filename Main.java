// Jimena Vásquez
// Carné: 25092
// Programación Orientada a Objetos
// Laboratorio 3 - Herencia

// Imports
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Entrada al sistema
        System.out.println("Bienvenido al sistema de gestión de médicos.");
        Controlador controlador = new Controlador(); // controlador central
        Manager manager = new Manager(controlador); // capa de manager
        Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        // Lista de departamentos disponibles (fijos)
        ArrayList<String> departamentos = new ArrayList<>();
        departamentos.add("Cardiologia");
        departamentos.add("Neurologia");
        departamentos.add("Pediatria");
        departamentos.add("Urgencias");
        departamentos.add("Farmacia");
        departamentos.add("Radiologia");
        departamentos.add("Enfermeria");
        departamentos.add("Medicina General");
        departamentos.add("Cirugia");
        departamentos.add("Dermatologia");

        // Asignar departamentos al controlador
        controlador.setDepartamentos(departamentos);

        while (opcion != 7) { // bucle principal del menú
            System.out.println("\n--- Menú Principal ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Agregar Médico");
            System.out.println("2. Mostrar Personal Médico");
            System.out.println("3. Calcular Salario");
            System.out.println("4. Agendar Cita");
            System.out.println("5. Cambiar Cita");
            System.out.println("6. Operaciones de manager");
            System.out.println("7. Salir");
            System.out.print("Opción: ");
            opcion = teclado.nextInt();

            switch (opcion) {
                case 1:
                    // Agregar nuevos médicos en un ciclo
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
                        System.out.println("Ingrese el departamento del médico: ");
                        System.out.println("\nDepartamentos en el hospital:");
                        for (String depto : departamentos) {
                            System.out.println("- " + depto);
                        }
                        System.out.println("Seleccione un departamento de la lista anterior.");
                        String d = teclado.next();
                        while (!departamentos.contains(d.trim())) {
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
                        // Crear la subclase correspondiente según el tipo
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
                    // Mostrar lista completa de médicos
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
                    // Calcular salario por médico, según su tipo
                    System.out.println("\nCalcular salario por médico");
                    for (Medico m : controlador.getMedicos()) {
                        System.out.println("[ID: #" + m.getId() + "]: " + m.getNombre());
                    }
                    System.out.print("Ingrese el ID del médico para calcular su salario: ");
                    int identificador = teclado.nextInt();
                    for (Medico m : controlador.getMedicos()) {
                        if (m.getId() == identificador) {
                            if (m.getClass() == DoctorGeneral.class) {
                                DoctorGeneral doc = (DoctorGeneral) m;
                                System.out.print("Ingrese la cantidad de consultas realizadas este mes para " + m.getNombre() + ": ");
                                int consultas = teclado.nextInt();
                                double salario = doc.calcularSalario(consultas);
                                System.out.println("El salario del médico " + m.getNombre() + " es: " + salario);
                                break;
                            }
                            else if (m.getClass() == Enfermero.class) {
                                Enfermero enf = (Enfermero) m;
                                double bono = 0;
                                if (enf.getHorario().equalsIgnoreCase("nocturno")) {
                                    System.out.print("Ingrese el bono nocturno para " + m.getNombre() + ": ");
                                    bono = teclado.nextDouble();
                                }
                                double salario = m.calcularSalario(bono);
                                System.out.println("El salario del médico " + m.getNombre() + " es: " + salario);
                                break;
                            }
                            else if (m.getClass() == Radiologo.class) {
                                Radiologo rad = (Radiologo) m;
                                System.out.println("Ingrese la cantidad de estudios realizados por " + m.getNombre() + ": ");
                                int estudios = teclado.nextInt();
                                double salario = rad.calcularSalario(estudios);
                                System.out.println("El salario del médico " + m.getNombre() + " es: " + salario);
                                break;
                            }
                            else if (m.getClass() == Farmaceutico.class) {
                                Farmaceutico farm = (Farmaceutico) m;
                                System.out.print("Ingrese el bono mensual por prescripciones para " + m.getNombre() + ": ");
                                double bono = teclado.nextDouble();
                                double salario = farm.calcularSalario(bono);
                                System.out.println("El salario del médico " + m.getNombre() + " es: " + salario);
                                break;
                            }
                        }
                    } 
                    break;
                case 4:
                    // Agendar una nueva cita (con validaciones)
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
                        // parseo seguro de fecha
                        fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    } catch (Exception e) {
                        System.out.println("Formato de fecha no válido.");
                        break;
                    }
                    System.out.print("Hora (HH:mm): ");
                    String horaStr = teclado.next();
                    LocalTime hora;
                    try {
                        // parseo seguro de hora
                        hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));
                    } catch (Exception e) {
                        System.out.println("Formato de hora no válido.");
                        break;
                    }
                    System.out.print("Tipo de cita: ");
                    String tipoCita = teclado.next();

                    // Validación de capacidad diaria para ciertos tipos
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

                    // Determinar estado inicial según fecha/hora relativa
                    String estado;
                    if (fecha.isBefore(LocalDate.now()) && hora.isBefore(LocalTime.now())) {
                        estado = "COMPLETADA";
                    } else if (hora.equals(LocalTime.now()) && fecha.isEqual(LocalDate.now())) {
                        estado = "EN PROGRESO";
                    } else if (fecha.isBefore(LocalDate.now()) && hora.isAfter(LocalTime.now())) {
                        estado = "COMPLETADA";
                    } else {
                        estado = "PROGRAMADA";
                    }

                    Cita cita = new Cita(idCita, paciente, medicoElegido, fecha, hora, tipoCita, estado);
                    controlador.agregarCita(cita);
                    System.out.println("¡Cita agendada!");

                    break;

                case 5: // Reagendar Citas
                    System.out.print("Ingrese el ID de la cita a reagendar: ");
                    int idCitaCambiar = teclado.nextInt();
                    System.out.println("¿El médico original está de acuerdo? (si/no): ");
                    String confirmacionMedico = teclado.next();
                    if (confirmacionMedico.equalsIgnoreCase("no")) {
                        // Si el médico no está de acuerdo, se puede cancelar
                        System.out.println("Reagendamiento cancelado. El médico no está de acuerdo.");
                        System.out.print("¿Desea cancelar la cita? (si/no): ");
                        String cancelarCita = teclado.next();
                        if (cancelarCita.equalsIgnoreCase("si")) {
                            Cita citaACancelar = null;
                            for (Cita c : controlador.getCitas()) {
                                if (c.getId() == idCitaCambiar) {
                                    citaACancelar = c;
                                    break;
                                }
                            }
                            if (citaACancelar != null) {
                                controlador.citaCancelada(citaACancelar);
                                System.out.println("Cita cancelada exitosamente.");
                            } else {
                                System.out.println("No se pudo cancelar la cita (no existe).");
                            }
                        }
                        break;
                    }
                    boolean resultado = controlador.reagendarCita(idCitaCambiar);
                    if (resultado) {
                        System.out.println("Cita reagendada exitosamente.");
                        System.out.println(controlador.obtenerHistorialReagendamientos(idCitaCambiar)); // mostrar historial
                    } else {
                        System.out.println("No se pudo reagendar la cita (no existe o sin disponibilidad).");
                    }
                    break;
                case 6: // Operaciones de manager
                    System.out.println("\nOperaciones de manager:");
                    System.out.println("Seleccione una opción:");
                    System.out.println("1. Encontrar personal disponible");
                    System.out.println("2. Reportes de Nómina por departamento");
                    System.out.println("3. Gestión de Conflictos de Horarios");
                    System.out.println("4. Reportes de personal");
                    System.out.println("5. Reporte de Citas");
                    System.out.println("6. Historial de Reagendamientos");
                    System.out.println("7. Volver al menú principal");
                    System.out.println("Seleccione una opción:");
                    int opcionManager = teclado.nextInt();

                    switch (opcionManager) {
                        case 1:
                        // Encontrar personal Disponible por tipo
                            System.out.println("\nEncontrar personal médico disponible:");
                            System.out.print("Ingrese el tipo de médico (DoctorGeneral, Enfermero, Radiologo, Farmaceutico): ");
                            String tipoMedicoDisponible = teclado.next();
                            while (tipoMedicoDisponible.equalsIgnoreCase("DoctorGeneral") == false && tipoMedicoDisponible.equalsIgnoreCase("Enfermero") == false && tipoMedicoDisponible.equalsIgnoreCase("Radiologo") == false && tipoMedicoDisponible.equalsIgnoreCase("Farmaceutico") == false) {
                                System.out.println("Tipo de médico no válido. Ingrese un tipo válido: ");
                                tipoMedicoDisponible = teclado.next();
                            }
                            System.out.print("Ingrese la fecha (YYYY-MM-DD): ");
                            String fechaDisponible = teclado.next();
                            LocalDate fechaDisp;
                            try {
                                fechaDisp = LocalDate.parse(fechaDisponible, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            } catch (Exception e) {
                                System.out.println("Formato de fecha no válido.");
                                break;
                            }
                            System.out.print("Ingrese la hora (HH:mm): ");
                            String horaDisponible = teclado.next();
                            LocalTime horaDisp;
                            try {
                                horaDisp = LocalTime.parse(horaDisponible, DateTimeFormatter.ofPattern("HH:mm"));
                            } catch (Exception e) {
                                System.out.println("Formato de hora no válido.");
                                break;
                            }
                            
                            // Revisar disponibilidad contra las citas existentes
                            for (Cita c : controlador.getCitas()) {
                                if (controlador.getMedicos().isEmpty()) {
                                    System.out.println("No hay médicos registrados.");
                                    break;
                                }
                                for (Medico m : controlador.getMedicos()) {
                                    m = c.getMedico(); // revisar por cita
                                    if (m.getClass().getSimpleName().equals(tipoMedicoDisponible)) {
                                        if (c.getHora().equals(horaDisp) && c.getFecha().equals(fechaDisp)) {
                                            System.out.println("Doctor/a " + m.getNombre() + " no está disponible en ese momento.");
                                            break;
                                        } else {
                                            System.out.println("Doctor/a " + m.getNombre() + " está disponible en ese momento.");
                                            break;
                                        }
                                    } 
                                }
                            }
                            break;
                        case 2:
                            // Reportes de Nómina por departamento
                            System.out.println("\nNómina por departamento:");
                            ArrayList<String> nomina = manager.calcularNominaPorDepartamento();
                            for (String n : nomina) {
                                System.out.println(n);
                            }
                            break;
                        case 3:
                            // Gestión de Conflictos de Horarios
                            System.out.println("\nGestión de conflictos de horarios:");
                            boolean huboConflictos = false;
                            for (int i = 0; i < controlador.getCitas().size(); i++) {
                                Cita citaA = controlador.getCitas().get(i);
                                for (int j = i + 1; j < controlador.getCitas().size(); j++) {
                                    Cita citaB = controlador.getCitas().get(j);
                                    if (citaA.getMedico().equals(citaB.getMedico()) && citaA.getFecha().equals(citaB.getFecha()) && citaA.getHora().equals(citaB.getHora()) && !citaA.getEstado().equals("CANCELADA") && !citaB.getEstado().equals("CANCELADA")) {
                                        System.out.println("Conflicto detectado entre las citas ID: " + citaA.getId() + " y ID: " + citaB.getId() + ". Se recomienda reagendar una de las citas.");
                                        huboConflictos = true;
                                    }
                                }
                            }
                            if (!huboConflictos) {
                                System.out.println("No se detectaron conflictos de horarios.");
                            }
                            break;
                        case 4:
                            // Reportes de personal: conteo de citas por médico
                            System.out.println("\nReporte de personal médico:");
                            for (Medico m : controlador.getMedicos()) {
                                System.out.println("Cantidad de citas para " + m.getNombre() + ": " + controlador.getCitas().stream().filter(c -> c.getMedico().equals(m)).count());
                            }
                            break;
                        case 5:
                            // Reporte de Citas con opción de filtrado
                            System.out.println("\nReporte de citas:");
                            System.out.println("¿Desea filtar por estado? (si/no): ");
                            String resp = teclado.next();
                            for (Medico m : controlador.getMedicos()) {
                                for (Cita c : controlador.getCitas()) {
                                    if (controlador.getMedicos().isEmpty()) {
                                        System.out.println("No hay médicos registrados.");
                                        break;
                                    }
                                    if (resp.equalsIgnoreCase("si")) {
                                        System.out.print("Ingrese el estado a filtrar (PROGRAMADA, EN PROGRESO, COMPLETADA, CANCELADA, REAGENDADA): ");
                                        String estadoFiltro = teclado.next().toUpperCase();
                                        while (estadoFiltro.equals("PROGRAMADA") == false && estadoFiltro.equals("EN PROGRESO") == false && estadoFiltro.equals("COMPLETADA") == false && estadoFiltro.equals("CANCELADA") == false && estadoFiltro.equals("REAGENDADA") == false) {
                                            System.out.println("Estado no válido. Ingrese un estado válido: ");
                                            estadoFiltro = teclado.next().toUpperCase();
                                        }
                                        if (estadoFiltro.equals("PROGRAMADA")) {
                                            System.out.println("\nCitas PROGRAMADAS:");
                                            manager.mostrarCitasPorEstadoYMedico("PROGRAMADA", m);
                                            break;
                                        } else if (estadoFiltro.equals("EN PROGRESO")) {
                                            System.out.println("\nCitas EN PROGRESO:");
                                            manager.mostrarCitasPorEstadoYMedico("EN PROGRESO", m);
                                            break;
                                        } else if (estadoFiltro.equals("COMPLETADA")) {
                                            System.out.println("\nCitas COMPLETADAS:");
                                            manager.mostrarCitasPorEstadoYMedico("COMPLETADA", m);
                                            break;
                                        } else if (estadoFiltro.equals("CANCELADA")) {
                                            System.out.println("\nCitas CANCELADAS:");
                                            manager.mostrarCitasPorEstadoYMedico("CANCELADA", m);
                                            break;
                                        } else if (estadoFiltro.equals("REAGENDADA")) {
                                            System.out.println("\nCitas REAGENDADAS:");
                                            manager.mostrarCitasPorEstadoYMedico("REAGENDADA", m);
                                            break;
                                        }
                                    } else {
                                        if (c.getMedico().equals(m)) {
                                            System.out.println(c);
                                        }
                                    }
                                }
                            }
                            break;
                        case 6:
                        // Historial de Reagendamientos
                            System.out.println("\nHistorial de Reagendamientos:");
                            for (Cita c : controlador.getCitas()) {
                                        if (c.getHistorial().size() > 1) {
                                            System.out.println("Cita ID: " + c.getId());
                                            for (String evento : c.getHistorial()) {
                                                System.out.println("  " + evento);
                                            }
                                        }
                                    }
                                    break;
                        case 7: // Opción para volver al menú principal
                            System.out.println("Volviendo al menú principal.");
                            break;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                            break;
                    }
                    break;
                case 7: // Opción para salir del sistema
                    System.out.println("Saliendo del sistema. ¡Hasta luego!");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
}