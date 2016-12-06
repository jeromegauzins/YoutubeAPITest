public class APIYoutube {

	public static final String SEARCH="search?";
	public static final String VIDEO ="videos?";
	public static final String COMMENTAIRE ="commentThreads?";
	
	private HttpApi httpApi;
	
	public APIYoutube(RequeteApiTermineListener requeteApiListener){
		httpApi=new HttpApi(requeteApiListener);
	}
	
	
	/* Il va y avoir ici une liste de methode pour appeler l'api Youtube.
	   J'ai mis que la recherche pour l'instant
	   Mais a terme on peut imaginer pas mal de truc
	   uploadCommentaire, uploadVideo...*/
	
	
	public void getCommentaires(String idVideo){
		httpApi.executerRequete(APIYoutube.COMMENTAIRE, "part=snippet&videoId="+idVideo);
	}
	
	//recuperer les metadata d'une video
	public void getVideoMetadata(String idVideo){
		httpApi.executerRequete(APIYoutube.VIDEO, "part=snippet,statistics&id="+idVideo);
	}
	
	//recherche une video (q le parametre de recherche)
	public void rechercher(String q) {
		httpApi.executerRequete(APIYoutube.SEARCH,"part=snippet&maxResults=10&q="+q);
	}
	
	//recherche une video avec nb_resultat resultat retournes (q le parametre de recherche)
	public void rechercher(String q, int nb_requltat) {
		httpApi.executerRequete(APIYoutube.SEARCH,"part=snippet&maxResults="+nb_requltat+"&q="+q);
	}
	
	

}
