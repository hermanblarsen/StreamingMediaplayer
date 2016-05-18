package client;

import javax.swing.JFrame;

import server.UserAccount;
import server.VideoFile;

import javax.swing.ButtonModel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class CommentWindow extends JFrame {
	private JTextField commentInputPane;
	private JTextArea submittedCommentsPane;
	private JButton submitButton;
	private String selectedVideo;
	private Client connectedClient;
	private UserAccount connectedUser;
	private List<String> listOfComments = new ArrayList<String>();
	private JScrollPane scrollPaneForCommentsPanel;
	private JRadioButton rateRadioButton2;
	private JRadioButton rateRadioButton3;
	private JRadioButton rateRadioButton4;
	private JRadioButton rateRadioButton5;
	private JButton rateButton;
	private int userRating = 0;

	public CommentWindow(String aSelectedVideoTitle, String aSelectedVideoID, Client aConnectedClient,
			UserAccount aConnectedUser) {
		this.setLocation(new Point(100, 100));
		this.setPreferredSize(new Dimension(600, 430));
		this.setResizable(false);
		this.setMinimumSize(new Dimension(600, 430));
		this.setMaximumSize(new Dimension(600, 430));
		this.setAlwaysOnTop(true);
		this.setTitle("Comments Field: " + aSelectedVideoTitle);

		this.connectedClient = aConnectedClient;
		this.selectedVideo = aSelectedVideoID;
		this.connectedUser = aConnectedUser;

		getContentPane().setLayout(null);
		submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// test that comment box is not empty
				if (commentInputPane.getText().length() >= 1) {
					connectedClient.sendToServer("COMMENT");
					connectedClient.sendToServer(selectedVideo);
					connectedClient
							.sendToServer("[" + connectedUser.getUserNameID() + "]: " + commentInputPane.getText());
					// resetting the comment pane
					commentInputPane.setText("");
					// obtaining the new updated comment list
					getVideoComments();
				}
			}
		});
		submitButton.setBounds(475, 296, 105, 20);
		getContentPane().add(submitButton);

		commentInputPane = new JTextField();
		commentInputPane.setBounds(15, 296, 450, 20);
		getContentPane().add(commentInputPane);

		scrollPaneForCommentsPanel = new JScrollPane();
		scrollPaneForCommentsPanel.setBounds(15, 15, 565, 270);
		getContentPane().add(scrollPaneForCommentsPanel);

		submittedCommentsPane = new JTextArea();
		submittedCommentsPane.setText("");
		submittedCommentsPane.setEditable(false);
		submittedCommentsPane.setLineWrap(true);
		scrollPaneForCommentsPanel.setViewportView(submittedCommentsPane);

		JLabel ratingLabel = new JLabel("Rating (1-5):");
		ratingLabel.setBounds(176, 325, 75, 31);
		getContentPane().add(ratingLabel);

		ButtonGroup ratingButtonGroup = new ButtonGroup();

		JRadioButton rateRadioButton1 = new JRadioButton("1");
		ratingButtonGroup.add(rateRadioButton1);

		rateRadioButton1.setBounds(257, 323, 40, 35);
		rateRadioButton1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				userRating = 1;
			}
		});
		getContentPane().add(rateRadioButton1);
		rateRadioButton1.setVerticalTextPosition(SwingConstants.TOP);
		rateRadioButton1.setHorizontalTextPosition(SwingConstants.CENTER);

		rateRadioButton2 = new JRadioButton("2");
		ratingButtonGroup.add(rateRadioButton2);
		rateRadioButton2.setBounds(299, 323, 40, 35);
		rateRadioButton2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				userRating = 2;
			}
		});
		getContentPane().add(rateRadioButton2);
		rateRadioButton2.setVerticalTextPosition(SwingConstants.TOP);
		rateRadioButton2.setHorizontalTextPosition(SwingConstants.CENTER);

		rateRadioButton3 = new JRadioButton("3");
		ratingButtonGroup.add(rateRadioButton3);
		rateRadioButton3.setBounds(341, 323, 40, 35);
		rateRadioButton3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				userRating = 3;
			}
		});
		getContentPane().add(rateRadioButton3);
		rateRadioButton3.setVerticalTextPosition(SwingConstants.TOP);
		rateRadioButton3.setHorizontalTextPosition(SwingConstants.CENTER);

		rateRadioButton4 = new JRadioButton("4");
		ratingButtonGroup.add(rateRadioButton4);
		rateRadioButton4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				userRating = 4;
			}
		});
		rateRadioButton4.setBounds(383, 323, 40, 35);
		getContentPane().add(rateRadioButton4);
		rateRadioButton4.setVerticalTextPosition(SwingConstants.TOP);
		rateRadioButton4.setHorizontalTextPosition(SwingConstants.CENTER);

		rateRadioButton5 = new JRadioButton("5");
		ratingButtonGroup.add(rateRadioButton5);
		rateRadioButton5.setBounds(425, 323, 40, 35);
		rateRadioButton5.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				userRating = 5;
			}
		});
		getContentPane().add(rateRadioButton5);
		rateRadioButton5.setVerticalTextPosition(SwingConstants.TOP);
		rateRadioButton5.setHorizontalTextPosition(SwingConstants.CENTER);

		rateButton = new JButton("Rate It!");
		rateButton.setBounds(475, 332, 105, 20);
		getContentPane().add(rateButton);
		rateButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (userRating > 0) {
					connectedClient.sendToServer("RATE");
					// send the videoID to the server
					connectedClient.sendToServer(selectedVideo);
					connectedClient.sendToServer(userRating);
					// update the local user rating
					ArrayList<VideoFile> modifiedVideoList = (ArrayList<VideoFile>) connectedClient.currentUser
							.getVideos();
					for (Iterator iterator = modifiedVideoList.iterator(); iterator.hasNext();) {
						VideoFile video = (VideoFile) iterator.next();
						if (video.getID().equals(selectedVideo)) {
							video.setUserRating(userRating);
						}
					}
					connectedClient.currentUser.setVideos(modifiedVideoList);
					// rating should now be updated, client now has to refresh
					// the table.
					connectedClient.getVideoListFromServer();
				}
			}
		});
		// get the user rating
		for (Iterator iterator = connectedClient.currentUser.getVideos().iterator(); iterator.hasNext();) {
			VideoFile video = (VideoFile) iterator.next();
			int Rating = 0;
			if (video.getID().equals(selectedVideo)) {
				Rating = video.getUserRating();
			}
			switch (Rating) {
			case 1:
				rateRadioButton1.setSelected(true);
				break;
			case 2:
				rateRadioButton2.setSelected(true);
				break;
			case 3:
				rateRadioButton3.setSelected(true);
				break;
			case 4:
				rateRadioButton4.setSelected(true);
				break;
			case 5:
				rateRadioButton5.setSelected(true);
				break;
			default:
				break;
			}
		}
		this.getVideoComments();
	}

	private void getVideoComments() {
		connectedClient.sendToServer("GET VIDEO COMMENTS");
		connectedClient.sendToServer(selectedVideo);

		// Make sure the object in the stream is the arraylist.
		Object clientInput;
		do {
			clientInput = connectedClient.readFromServer();
		} while (!(clientInput instanceof ArrayList<?>));

		listOfComments = (ArrayList<String>) clientInput;
		String commentsAsTextString = "";
		// if there are no comments comments will be null, hence the check.
		if (listOfComments != null) {
			for (String comment : listOfComments) {
				commentsAsTextString += comment + "\n";
			}
		}
		submittedCommentsPane.setText(commentsAsTextString);
	}
}