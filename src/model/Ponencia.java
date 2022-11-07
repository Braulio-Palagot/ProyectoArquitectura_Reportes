package model;

public class Ponencia {
    private int ID_Ponencia;
    private String tema;
    private String documentacion;
    private String materialApoyo;

    public Ponencia (int ID_Ponencia, String tema, String documentacion, String materialApoyo){
        this.ID_Ponencia= ID_Ponencia;
        this.tema = tema;
        this.documentacion = documentacion;
        this.materialApoyo = materialApoyo;

    }

    public int getID_Ponencia(){
        return ID_Ponencia;
    }
    public String getTema(){
        return tema;
    }
    public String getDocumentacion(){
        return documentacion;
    }
    public String getMaterialApoyo(){
        return materialApoyo;
    }

    public void setID_Ponecia(int ID_Ponencia){
        this.ID_Ponencia=ID_Ponencia;
    }
    public void setTema (String tema){
        this.tema=tema;
    }
    public void setDocumentacion(String documentacion){
        this.documentacion=documentacion;
    }
    public void setMaterialApoyo(String materialApoyo){
        this.materialApoyo= materialApoyo;
    }

}
