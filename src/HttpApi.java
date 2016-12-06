import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpApi {
	

	//cette clé a été généré depuis la Console developer google, elle permet de gérer les quotas
	private static final String KEY="AIzaSyB1MuSmWH5Lfaj-5FQoYuW6gLPjhS0yigw";
	
	//L'url de base de l'API
	private static final String URL_BASE="https://www.googleapis.com/youtube/v3/";
	
	private RequeteApiTermineListener requeteApiListener;

	private static final int HTTP_SUCCESS = 200;
	
	public HttpApi(RequeteApiTermineListener requeteApiListener){
		this.requeteApiListener=requeteApiListener;
	}

	//le type represente le type d'action, que l'on souhaite recuperer via l'API.
	public void executerRequete(String type, String params) {
		final String URL_STR=(URL_BASE+type+params+"&key="+KEY).replace(" ", "%20");
		System.out.println("Executer la requete="+URL_STR);
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					
					URL url=new URL(URL_STR);
					HttpURLConnection connexion=((HttpURLConnection)url.openConnection());
					connexion.setRequestMethod("GET");
					connexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
					connexion.setRequestProperty("charset", "UTF-8");
					connexion.connect();
					
					if(connexion.getResponseCode()==HTTP_SUCCESS){
						requeteApiListener.onRequeteReussi(type,getJSONObjectFromInputStream(connexion.getInputStream()));
					}else{
						requeteApiListener.onRequeteFoire("Erreur de requète "+connexion.getResponseMessage(),connexion.getResponseCode());
					}
					
				} catch (MalformedURLException e) {
					requeteApiListener.onRequeteFoire("L'URL est mal formé",-1);
					e.printStackTrace();
				} catch (IOException e) {
					requeteApiListener.onRequeteFoire(e.getLocalizedMessage(),-1);
					e.printStackTrace();
				}	
			}}).start();
		
		
	}
	
	private JSONObject getJSONObjectFromInputStream(InputStream inputStream) {
		try {
			BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		    String ligne,chaine="";
		    
		    while((ligne=reader.readLine())!=null){
		    	chaine+=ligne+"\n";
		    }
		    reader.close();
		    return new JSONObject(chaine);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
