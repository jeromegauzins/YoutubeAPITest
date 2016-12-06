import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;

public class VideoItemRenderer extends JPanel implements ListCellRenderer<VideoItem> {
 
	private JPanel panelDescription=new JPanel();
	private JPanel panelImage=new JPanel();
	private JLabel titreLabel=new JLabel(),
			descrLabel=new JLabel(),
            channelLabel=new JLabel(),
			iconLabel=new JLabel();
	
	public VideoItemRenderer(){
		setLayout(new BorderLayout());
        
        iconLabel=new JLabel();
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        
        titreLabel.setFont(new Font("Arial",Font.PLAIN,20));
        titreLabel.setForeground(new Color(105,158,205));
        titreLabel.setPreferredSize(new Dimension(400,80));
        
        channelLabel.setPreferredSize(new Dimension(400,30));
        channelLabel.setVerticalAlignment(JLabel.NORTH);
        channelLabel.setForeground(new Color(125,181,223));
        channelLabel.setFont(new Font("Arial",Font.PLAIN,13));

        descrLabel.setPreferredSize(new Dimension(400,50));
        descrLabel.setVerticalAlignment(JLabel.NORTH);
        
        panelImage.add(iconLabel);
        panelImage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelImage.setPreferredSize(new Dimension(350,200));
        panelImage.setBackground(Color.WHITE);
        
        panelDescription.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelDescription.add(titreLabel,BorderLayout.NORTH);
        panelDescription.add(channelLabel,BorderLayout.NORTH);
        panelDescription.add(descrLabel,BorderLayout.CENTER);
        panelDescription.setBackground(Color.WHITE);
        panelDescription.setPreferredSize(new Dimension(400,220));

        
        add(new JSeparator(),BorderLayout.NORTH);
        add(panelImage,BorderLayout.WEST);
        add(panelDescription,BorderLayout.CENTER);
        
        setBackground(Color.WHITE);

	}
    @Override
    public Component getListCellRendererComponent(JList<? extends VideoItem> list, VideoItem videoItem, int index,
        boolean isSelected, boolean cellHasFocus) {
    	
        ImageIcon imageIcon = new ImageIcon(chargerImageFromURL(videoItem.getImageURL()));

        iconLabel.setIcon(imageIcon);
    	titreLabel.setText(videoItem.getTitre());
    	channelLabel.setText(videoItem.getChannelTitle());
    	descrLabel.setText("<html>"+videoItem.getDescription()+"</html>"); //les balises html servent à traiter le multiline dans un jlabel

        return this;
    }

	private Image chargerImageFromURL(String imageURL) {
		
		try {
			return ImageIO.read(new URL(imageURL));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
     
}