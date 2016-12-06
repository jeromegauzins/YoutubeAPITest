import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.ListCellRenderer;

public class CommentaireRenderer extends JPanel implements ListCellRenderer<Commentaire> {
 
	private JLabel descrLabel=new JLabel(),
            pseudoLabel=new JLabel();
	
	public CommentaireRenderer(){
        
        pseudoLabel.setForeground(new Color(160,160,160));
        pseudoLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        descrLabel.setVerticalAlignment(JLabel.NORTH);
        descrLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		setLayout(new GridLayout(3,1));
        add(descrLabel);
        add(pseudoLabel);
        add(new JSeparator());
        
        setBackground(Color.WHITE);

	}
	@Override
	public Component getListCellRendererComponent(JList<? extends Commentaire> arg0, Commentaire commentaire, int arg2,
			boolean arg3, boolean arg4) {
    	descrLabel.setText("<html>"+commentaire.getDescription()+"</html>");
    	pseudoLabel.setText(commentaire.getPseudo());

		return this;
	}
	
     
}