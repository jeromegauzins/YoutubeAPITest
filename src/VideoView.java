import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Page pour afficher la fiche d'une video.
 * les commentaires, la description,...
 * @author jerome
 *
 */
public class VideoView extends JPanel implements RequeteApiTermineListener{

	private VideoItem video;
	
	private JPanel mainPanel=new JPanel();
	private JPanel videoPanel=new JPanel();
	private JPanel infosPanel=new JPanel();
	private JPanel descriptionPanel=new JPanel();

	
	private JLabel titreLabel=new JLabel();
	private JLabel chaineLabel=new JLabel();
	private JLabel nbVueLabel=new JLabel();
	private JLabel descriptionLabel=new JLabel();


	private APIYoutube apiyoutube=new APIYoutube(this);

	private DefaultListModel<Commentaire> commentairesListModel;
	private JList<Commentaire> listCommentaires=new  JList<Commentaire>();

	
	public VideoView(VideoItem video) {
		this.video=video;
		
		JLabel lab=new JLabel("<html>Hey hey quand est ce qui marche ce putain de lecteur vidéo à la con</html>");
		lab.setForeground(Color.WHITE);
		lab.setHorizontalAlignment(JLabel.CENTER);

		videoPanel.setBackground(Color.black);
		videoPanel.setPreferredSize(new Dimension(550,300));
		videoPanel.setLayout(new BorderLayout());
		videoPanel.add(lab,BorderLayout.CENTER);
		videoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		titreLabel.setText("<html>"+video.getTitre()+"</html>");
		titreLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		titreLabel.setFont(new Font(titreLabel.getFont().getName(),Font.PLAIN,16));
		titreLabel.setPreferredSize(new Dimension(540,20));

		chaineLabel.setText(video.getChannelTitle());
		chaineLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
		chaineLabel.setFont(new Font(titreLabel.getFont().getName(),Font.BOLD,11));

		nbVueLabel.setText(video.getNbVues()+" vues");
		nbVueLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		nbVueLabel.setForeground(new Color(95,95,95));
		nbVueLabel.setFont(new Font(titreLabel.getFont().getName(),Font.BOLD,17));
		nbVueLabel.setPreferredSize(new Dimension(540,20));
		nbVueLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		descriptionLabel.setText(video.getChannelTitle());
		descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		descriptionLabel.setFont(new Font(titreLabel.getFont().getName(),Font.PLAIN,11));
		
		infosPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		infosPanel.setBackground(Color.white);
		infosPanel.setPreferredSize(new Dimension(550,110));
		infosPanel.add(titreLabel);
		infosPanel.add(chaineLabel);
		infosPanel.add(nbVueLabel);
		infosPanel.add(new JSeparator());
		infosPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		
		descriptionPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		descriptionPanel.setBackground(Color.white);
		descriptionPanel.add(descriptionLabel);
		descriptionPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


		listCommentaires.setCellRenderer(new CommentaireRenderer());
		listCommentaires.setFixedCellWidth(550);
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(videoPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.add(infosPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.add(descriptionPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0,5)));
		mainPanel.add(listCommentaires);

		setLayout(new FlowLayout(FlowLayout.LEFT));
		add(mainPanel);

	}


	public void chargerMetaData() {
		apiyoutube.getVideoMetadata(video.getVideoId());
		apiyoutube.getCommentaires(video.getVideoId());
	}


	


	public void updateView(){
		descriptionLabel.setText("<html>"+video.getDescription().replace("\n", "<br/>")+"</html>");
		descriptionPanel.setPreferredSize(new Dimension(540,(int) descriptionLabel.getPreferredSize().getHeight()+15));
		
		titreLabel.setText("<html>"+video.getTitre()+"</html>");
		titreLabel.setPreferredSize(new Dimension(540,(int) titreLabel.getPreferredSize().getHeight()+15));

		nbVueLabel.setText(video.getNbVues()+" vues");
		
	}
	
	@Override
	public void onRequeteReussi(String type, JSONObject jsonObject) {
		System.out.println("Resultat requete="+jsonObject.toString());
		
		try{
			if(type==APIYoutube.VIDEO){
				parseMetadataJSON(jsonObject);
			}else if(type==APIYoutube.COMMENTAIRE){
				parseCommentairesJSON(jsonObject);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		

		updateView();
		
	}
	
	@Override
	public void onRequeteFoire(String resultat, int code) {
		System.out.println("Erreur "+ code+": "+resultat);
		
	}


	private void parseCommentairesJSON(JSONObject jsonObject) throws JSONException {
		commentairesListModel=new DefaultListModel<Commentaire>();
		
		JSONArray itemsVideo=jsonObject.getJSONArray("items");
		for(int i=0;i<itemsVideo.length();i++){
			JSONObject jsonVideo=itemsVideo.getJSONObject(i);
			JSONObject snippet=jsonVideo.getJSONObject("snippet").getJSONObject("topLevelComment").getJSONObject("snippet");
			
			Commentaire commentaire=new Commentaire(snippet.getString("textDisplay"));
			commentaire.setPseudo(snippet.getString("authorDisplayName"));
			video.ajouterCommentaire(commentaire);
			commentairesListModel.addElement(commentaire);
		}

		listCommentaires.setModel(commentairesListModel);
		
	}


	private void parseMetadataJSON(JSONObject jsonObject) throws JSONException {
		String des=jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("snippet").getString("description");
		
		video.setNbLike(jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("statistics").getLong("likeCount"));
		video.setNbDislike(jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("statistics").getLong("dislikeCount"));
		video.setNbVues(jsonObject.getJSONArray("items").getJSONObject(0).getJSONObject("statistics").getLong("viewCount"));
		video.setDescription(des);
	}

}
