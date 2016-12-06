
public class Commentaire {

	private String description;
	private String pseudo;
	public Commentaire(String description) {
		this.description=description;
	}
	public void setPseudo(String pseudo) {
		this.pseudo=pseudo;
	}
	public String getDescription() {
		return description;
	}
	public String getPseudo() {
		return pseudo;
	}

}
