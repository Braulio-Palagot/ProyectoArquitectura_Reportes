package model;

public class Evento {

    private int ID_Evento;
    private String nombre;
    private String direccion;
    private String fecha;

    public Evento(int ID_Evento, String nombre, String direccion, String fecha) {
        this.ID_Evento = ID_Evento;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fecha = fecha;
    }


    public int getID_Evento() {
        return ID_Evento;
    }

    public void setID_Evento(int ID_Evento) {
        this.ID_Evento = ID_Evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}