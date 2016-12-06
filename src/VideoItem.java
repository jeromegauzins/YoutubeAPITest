import java.util.ArrayList;

public class VideoItem {

	private String titre;
	private String description;
	private String imageURL;
	private String videoId;
	private String channelTitle;
	
	private long nbVues;
	private long nbLike;
	private long nbDislike;
	private ArrayList<Commentaire> commentaires=new ArrayList<Commentaire>();
	
	public VideoItem(String titre) {
		this.titre=titre;
	}
	public void setDescription(String description) {
		this.description=description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getTitre() {
		return titre;
	}
	public String getDescription() {
		return description;
	}
	public void setVideoId(String id) {
		videoId=id;
	}
	public void setChannelTitle(String channelTitle) {
		this.channelTitle=channelTitle;
	}
	public String getChannelTitle() {
		return channelTitle;
	}
	public String getVideoId() {
		// TODO Auto-generated method stub
		return videoId;
	}
	public long getNbVues() {
		return nbVues;
	}
	public void setNbVues(long nbVues) {
		this.nbVues = nbVues;
	}
	public long getNbLike() {
		return nbLike;
	}
	public void setNbLike(long nbLike) {
		this.nbLike = nbLike;
	}
	public long getNbDislike() {
		return nbDislike;
	}
	public void setNbDislike(long nbDislike) {
		this.nbDislike = nbDislike;
	}
	public void ajouterCommentaire(Commentaire commentaire) {
		commentaires.add(commentaire);
	}
	

}
