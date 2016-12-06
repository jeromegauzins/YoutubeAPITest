import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListVideoView extends JList<VideoItem> implements ListSelectionListener{

	private DefaultListModel<VideoItem> listModel;
	
	public ListVideoView(){
		 setBorder(BorderFactory.createBevelBorder(0));
		 setCellRenderer(new VideoItemRenderer());
		 addListSelectionListener(this);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			VideoView videoView=new VideoView(listModel.getElementAt(getSelectedIndex()));
			videoView.chargerMetaData();
			JScrollPane scrollPane=(JScrollPane) (getParent().getParent());
			
			scrollPane.setViewportView(videoView);
        }
	}
	
	public void parseJSON(JSONObject jsonObject) throws JSONException {
		listModel=new DefaultListModel<VideoItem>();
		
		JSONArray itemsVideo=jsonObject.getJSONArray("items");
		for(int i=0;i<itemsVideo.length();i++){
			JSONObject jsonVideo=itemsVideo.getJSONObject(i);
			JSONObject snippet=jsonVideo.getJSONObject("snippet");
			
			VideoItem videoItem=new VideoItem(snippet.getString("title"));
			videoItem.setDescription(snippet.getString("description"));
			videoItem.setImageURL(snippet.getJSONObject("thumbnails").getJSONObject("medium").getString("url"));
			videoItem.setChannelTitle(snippet.getString("channelTitle"));
			
			if(jsonVideo.getJSONObject("id").get("kind").equals("youtube#video"))videoItem.setVideoId(jsonVideo.getJSONObject("id").getString("videoId"));
			
			listModel.addElement(videoItem);
		}
		
		setModel(listModel);		
	}


}
