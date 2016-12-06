import org.json.JSONObject;

public interface RequeteApiTermineListener {

	void onRequeteFoire(String resultat,int code);
	void onRequeteReussi(String type, JSONObject jsonObject);
}
