import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Fenetre extends JFrame implements RequeteApiTermineListener, ActionListener{
	
	private static final Color rougeYoutube=new Color(230,33,23);
	
	private JPanel headerPanel=new JPanel(),
			       recherchePanel=new JPanel();
	
	private JLabel titre=new JLabel("Rechercher sur YouTube"),
			       teamName=new JLabel("Team SoComman"),
				   youtubeLogo=new JLabel();
	
	private JTextField rechercheTextField=new JTextField("Norman fait des videos");
	private JButton go=new JButton("Go");
	private JScrollPane scrollPane=new JScrollPane();
	
	
	private VideoView videoView;
	private ListVideoView listVideoView;

	private APIYoutube apiyoutube=new APIYoutube(this);
	
	public Fenetre(){
		
		setTitle("Petit Test de l' API Youtube");
		setSize(800,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		//initialisation header
		go.setVerticalAlignment(JButton.CENTER);
		go.addActionListener(this);
		
		titre.setForeground(new Color(240,245,245));
		titre.setFont(new Font("Arial",Font.BOLD,15));
		
		rechercheTextField.setPreferredSize(new Dimension(200,30));
		rechercheTextField.addActionListener(this);
		
		recherchePanel.add(titre);
		recherchePanel.add(rechercheTextField);
		recherchePanel.add(go);
		recherchePanel.setBackground(rougeYoutube);
		recherchePanel.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));
		
	    youtubeLogo.setIcon(new ImageIcon("youtube-logo.png"));
		
		teamName.setForeground(new Color(255,200,0));
		teamName.setFont(new Font("Kalinga",Font.BOLD,13));

		headerPanel.setLayout(new BorderLayout(5,5));
		headerPanel.add(youtubeLogo,BorderLayout.WEST);
		headerPanel.add(recherchePanel,BorderLayout.CENTER);
		headerPanel.add(teamName,BorderLayout.EAST);
		headerPanel.setBackground(rougeYoutube);
		headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

		add(headerPanel,BorderLayout.NORTH);
		add(scrollPane,BorderLayout.CENTER);

		setVisible(true);
		
		
	}
	//Implementation de l'interface ActionListener, qui previent lorsque qu'un controller est actionné
		//bouton ou autre.

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==go || e.getSource()==rechercheTextField){
				//appuie sur entree ou sur go, on lance une recherche
				apiyoutube.rechercher(rechercheTextField.getText());
			}
		}
	
	// Implementation de l'interface RequeteApiTermineListener qui nous previent lorsqu'une requete de APIYoutube est terminé
	@Override
	public void onRequeteFoire(String resultat,int code) {
		System.out.println("Erreur "+ code+": "+resultat);
	}


	@Override
	public void onRequeteReussi(String type, JSONObject jsonObject) {
		System.out.println("resultat requete: "+jsonObject.toString());
		
		try {
			if(type==APIYoutube.SEARCH){
				//Si c'est une recherche, alors on fait.
				listVideoView=new ListVideoView();
				listVideoView.parseJSON(jsonObject);
				scrollPane.setViewportView(listVideoView);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	


	


}
