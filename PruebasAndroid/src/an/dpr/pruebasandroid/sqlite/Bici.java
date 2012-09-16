package an.dpr.pruebasandroid.sqlite;

public class Bici {
	
	private Integer biciId;
	private String marca;
	private String modelo;
	private String grupo;
	
	public Integer getBiciId() {
		return biciId;
	}
	public void setBiciId(Integer biciId) {
		this.biciId = biciId;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	
	@Override
	public String toString(){
		return biciId+" - "+marca+" - "+modelo+" - "+grupo;
	}
	

}
