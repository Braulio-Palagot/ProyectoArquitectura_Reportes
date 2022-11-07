package model;

public class Usuario{
    private int ID_Usuario;
    private String nombreUsuario;
    private String correo;
    private String apellidoPaternoUsuario;
    private String apellidoMaternoUsuario;
    private String contrasenia;

    public Usuario(int ID_Usuario, String nombreUsuario, String correo, String apellidoPaternoUsuario, String apellidoMaternoUsuario, String contrasenia){
        this.ID_Usuario = ID_Usuario;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
        this.contrasenia = contrasenia;
    }

    public int getIdUsuario(){
        return ID_Usuario;
    }

    public void setIdUsuario(int ID_Usuario){
        this.ID_Usuario = ID_Usuario;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario){
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo(){
        return correo;
    }

    public void setCorreo(String correo){
        this.correo = correo;
    }

    public String getApellidoPaternoUsuario(){
        return apellidoPaternoUsuario;
    }

    public void setApellidoPaternoUsuario(String apellidoPaternoUsuario){
        this.apellidoPaternoUsuario = apellidoPaternoUsuario;
    }

    public String getApellidoMaternoUsuario(){
        return apellidoMaternoUsuario;
    }

    public void setApellidoMaternoUsuario(String apellidoMaternoUsuario){
        this.apellidoMaternoUsuario = apellidoMaternoUsuario;
    }

    public String getContrasenia(){
        return contrasenia;
    }

    public void setContrasenia(String contrasenia){
        this.contrasenia = contrasenia;
    }
}